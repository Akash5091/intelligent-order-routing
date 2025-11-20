package com.ak.orderrouting.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long warehouseId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int availableQty;
    private int reservedQty;
}