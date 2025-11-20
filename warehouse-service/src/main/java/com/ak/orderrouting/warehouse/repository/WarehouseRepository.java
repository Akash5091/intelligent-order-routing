package com.ak.orderrouting.warehouse.repository;

import com.ak.orderrouting.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
