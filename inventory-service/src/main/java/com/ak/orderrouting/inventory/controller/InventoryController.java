package com.ak.orderrouting.inventory.controller;

import com.ak.orderrouting.inventory.entity.Inventory;
import com.ak.orderrouting.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST API for inventory management.
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{warehouseId}/product/{sku}")
    public ResponseEntity<Map<String, Object>> getAvailability(
            @PathVariable Long warehouseId,
            @PathVariable String sku) {

        int available = inventoryService.getAvailableQty(warehouseId, sku);
        return ResponseEntity.ok(Map.of(
                "warehouseId", warehouseId,
                "sku", sku,
                "availableQty", available
        ));
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(
            @RequestParam Long warehouseId,
            @RequestParam String sku,
            @RequestParam int qty) {

        Inventory inv = inventoryService.addInventory(warehouseId, sku, qty);
        return ResponseEntity.ok(inv);
    }
}