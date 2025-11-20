package com.ak.orderrouting.common.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * Shared DTOs for order routing system.
 * Used for inter-service communication.
 */
public class OrderDtos {

    public record AddressDto(
            @NotEmpty String addressLine1,
            @NotEmpty String city,
            @NotEmpty String state,
            @NotEmpty String country,
            @NotEmpty String zipcode
    ) {}

    public record CustomerDto(
            @NotEmpty String name,
            @NotEmpty String email,
            @NotNull AddressDto address
    ) {}

    public record OrderItemRequest(
            @NotEmpty String sku,
            @NotNull Integer qty,
            @NotNull BigDecimal unitPrice
    ) {}

    public record CreateOrderRequest(
            @NotNull CustomerDto customer,
            @NotEmpty List<OrderItemRequest> items,
            @NotNull Integer slaDays
    ) {}

    public record RoutingEvaluateRequest(
            String orderId,
            AddressDto deliveryAddress,
            List<OrderItemShort> items,
            Integer slaDays
    ) {}

    public record OrderItemShort(
            String sku,
            Integer qty
    ) {}

    public record RoutingDecision(
            Long warehouseId,
            String warehouseCode,
            double distanceKm,
            double handlingCost,
            double shippingCostEstimate,
            boolean slaMet,
            double totalScore
    ) {}

    public record RoutingEvaluateResponse(
            String orderId,
            RoutingDecision selectedWarehouse
    ) {}
}