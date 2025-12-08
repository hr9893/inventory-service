package com.oms.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "AVAILABLE_INVENTORY")
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @Column(name = "ITEM_ID")
    private Integer itemId;
    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;
    @Column(name = "AVAILABLE_QUANTITY")
    private Integer availableQuantity;
    @Column(name = "ITEM_PRICE")
    private double price;
    @Version
    private Integer version;
}
