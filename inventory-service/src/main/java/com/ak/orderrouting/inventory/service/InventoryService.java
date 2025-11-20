package com.ak.orderrouting.inventory.service;

import com.ak.orderrouting.inventory.entity.Inventory;
import com.ak.orderrouting.inventory.entity.Product;
import com.ak.orderrouting.inventory.repository.InventoryRepository;
import com.ak.orderrouting.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing inventory and stock availability.
 */
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository,
                            ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public int getAvailableQty(Long warehouseId, String sku) {
        return inventoryRepository.findByWarehouseIdAndProduct_Sku(warehouseId, sku)
                .stream()
                .mapToInt(Inventory::getAvailableQty)
                .sum();
    }

    @Transactional
    public Inventory addInventory(Long warehouseId, String sku, int qty) {
        Product product = productRepository.findBySku(sku)
                .orElseGet(() -> {
                    Product p = new Product();
                    p.setSku(sku);
                    p.setName(sku);
                    return productRepository.save(p);
                });

        Inventory inv = new Inventory();
        inv.setWarehouseId(warehouseId);
        inv.setProduct(product);
        inv.setAvailableQty(qty);
        inv.setReservedQty(0);
        return inventoryRepository.save(inv);
    }
}