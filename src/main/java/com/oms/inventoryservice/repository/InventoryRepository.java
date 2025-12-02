package com.oms.inventoryservice.repository;

import com.oms.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    public List<Inventory> findByItemId(Integer itemId);
}
