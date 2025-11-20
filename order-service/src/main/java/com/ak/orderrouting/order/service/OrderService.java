package com.ak.orderrouting.order.service;

import com.ak.orderrouting.common.dto.OrderDtos.*;
import com.ak.orderrouting.order.entity.Customer;
import com.ak.orderrouting.order.entity.Order;
import com.ak.orderrouting.order.entity.OrderItem;
import com.ak.orderrouting.order.repository.CustomerRepository;
import com.ak.orderrouting.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service for managing orders and coordinating with routing service.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RoutingClient routingClient;

    public OrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            RoutingClient routingClient
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.routingClient = routingClient;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        // Create and save customer
        Customer customer = new Customer();
        customer.setName(request.customer().name());
        customer.setEmail(request.customer().email());
        customer.setAddressLine1(request.customer().address().addressLine1());
        customer.setCity(request.customer().address().city());
        customer.setState(request.customer().address().state());
        customer.setCountry(request.customer().address().country());
        customer.setZipcode(request.customer().address().zipcode());

        customer = customerRepository.save(customer);

        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus("CREATED");
        order.setExternalOrderId("ORD-" + UUID.randomUUID());

        // Add order items
        for (OrderItemRequest itemReq : request.items()) {
            OrderItem item = new OrderItem();
            item.setSku(itemReq.sku());
            item.setQty(itemReq.qty());
            item.setUnitPrice(itemReq.unitPrice());
            item.setOrder(order);
            order.getItems().add(item);
        }

        order = orderRepository.save(order);

        // Call routing service to determine optimal warehouse
        RoutingEvaluateRequest routingReq = new RoutingEvaluateRequest(
                order.getExternalOrderId(),
                request.customer().address(),
                request.items().stream()
                        .map(i -> new OrderItemShort(i.sku(), i.qty()))
                        .toList(),
                request.slaDays()
        );

        RoutingEvaluateResponse routingResp = routingClient.evaluate(routingReq);
        order.setRoutedWarehouseId(routingResp.selectedWarehouse().warehouseId());
        order.setStatus("ROUTED");

        return orderRepository.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }
}