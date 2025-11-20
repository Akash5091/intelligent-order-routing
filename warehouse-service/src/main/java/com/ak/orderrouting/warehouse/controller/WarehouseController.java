package com.ak.orderrouting.warehouse.controller;

import com.ak.orderrouting.warehouse.entity.Warehouse;
import com.ak.orderrouting.warehouse.repository.WarehouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for warehouse management.
 */
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @PostMapping
    public ResponseEntity<Warehouse> create(@RequestBody Warehouse warehouse) {
        warehouse.getRegions().forEach(r -> r.setWarehouse(warehouse));
        return ResponseEntity.ok(warehouseRepository.save(warehouse));
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> list() {
        return ResponseEntity.ok(warehouseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> get(@PathVariable Long id) {
        return warehouseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}