package com.oms.inventoryservice.service;

import com.oms.inventoryservice.dto.InventoryResponseDTO;
import com.oms.inventoryservice.entity.Inventory;
import com.oms.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    public InventoryResponseDTO checkInventory(Integer itemId, Integer requestedQuantity){
        InventoryResponseDTO inventoryResponseDTO = new InventoryResponseDTO();
        Integer availableQuantity;
        Inventory invResponse = getInventoryByItemId(itemId);

        if (invResponse.getAvailableQuantity() >= requestedQuantity){
            inventoryResponseDTO.setItemInStock(true);
            inventoryResponseDTO.setItemId(itemId);
            inventoryResponseDTO.setUnitPrice(invResponse.getPrice());
            inventoryResponseDTO.setItemQuantity(requestedQuantity);

            availableQuantity = invResponse.getAvailableQuantity() - requestedQuantity;

            Inventory updatedInventory =  new Inventory();
            updatedInventory.setItemId(itemId);
            updatedInventory.setAvailableQuantity(availableQuantity);
            updatedInventory.setPrice(invResponse.getPrice());

            inventoryRepository.save(updatedInventory);
        }
        log.info("Inventory Check Response",inventoryResponseDTO.toString());
        return inventoryResponseDTO;
    }

    public Inventory getInventoryByItemId(Integer itemId)
    {
        return inventoryRepository.findById(itemId)
                .orElse(null);
    }

    public Inventory saveInventory(Integer itemId, Integer availableQuantity, Integer itemPrice){
        Inventory invResponse = new Inventory();
        invResponse.setItemId(itemId);
        invResponse.setAvailableQuantity(availableQuantity);
        invResponse.setPrice(itemPrice);
        log.info("Saved/Updated inventory",invResponse);
        return inventoryRepository.save(invResponse);
    }

    public List<Inventory> getInventoryById(Integer itemId){
        return inventoryRepository.findByItemId(itemId);
    }
}
