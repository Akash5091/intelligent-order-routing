package com.ak.orderrouting.inventory.repository;

import com.ak.orderrouting.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByWarehouseIdAndProduct_Sku(Long warehouseId, String sku);
}