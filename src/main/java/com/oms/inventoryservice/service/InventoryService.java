package com.oms.inventoryservice.service;

import com.oms.inventoryservice.dto.InventoryRequestDTO;
import com.oms.inventoryservice.dto.InventoryResponseDTO;
import com.oms.inventoryservice.entity.Inventory;
import com.oms.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    @Autowired
    InventoryRepository inventoryRepository;

    public InventoryResponseDTO checkInventory(InventoryRequestDTO requestDTO) {
        final String methodName = "checkInventory";
        log.info("Entry", methodName);

        Inventory invResponse = getInventoryByItemId(requestDTO.getItemId());
        return updateInventory(requestDTO.getItemId(), requestDTO.getItemQuantity(), invResponse);
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

    private InventoryResponseDTO updateInventory(Integer itemId, Integer requestedQuantity, Inventory invResponse) {
        final String methodName = "updateInventory";
        logger.info("Entry", methodName);

        InventoryResponseDTO inventoryResponseDTO = new InventoryResponseDTO();

        if(invResponse.getAvailableQuantity() >= requestedQuantity) {
            Inventory updatedInventory = new Inventory();
            updatedInventory.setItemId(itemId);
            updatedInventory.setAvailableQuantity(invResponse.getAvailableQuantity() - requestedQuantity);
            updatedInventory.setPrice(invResponse.getPrice());

            inventoryRepository.save(updatedInventory);

            inventoryResponseDTO.setItemInStock(true);
        } else {
            inventoryResponseDTO.setItemInStock(false);
        }
        inventoryResponseDTO.setItemId(itemId);
        inventoryResponseDTO.setUnitPrice(invResponse.getPrice());
        inventoryResponseDTO.setItemQuantity(requestedQuantity);

        logger.info("Exit", methodName);

        return inventoryResponseDTO;
    }

    public List<Inventory> getInventoryById(Integer itemId){
        return inventoryRepository.findByItemId(itemId);
    }
}
