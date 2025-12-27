package com.oms.inventoryservice.service;

import com.oms.inventoryservice.dto.InventoryRequestDTO;
import com.oms.inventoryservice.dto.InventoryResponseDTO;
import com.oms.inventoryservice.entity.Inventory;
import com.oms.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public InventoryResponseDTO checkInventory(InventoryRequestDTO requestDTO) {
        final String methodName = "checkInventory";
        log.info("Entry {} ", methodName);
        InventoryResponseDTO inventoryResponseDTO = new InventoryResponseDTO();
        Inventory invResponse = inventoryRepository.findById(requestDTO.getItemId())
                .orElseThrow(() ->
                        new RuntimeException("Inventory not found for itemId " + requestDTO.getItemId())
                );
        log.info("Inventory response {}", invResponse);

        if(invResponse.getAvailableQuantity() >= requestDTO.getItemQuantity()) {
            invResponse.setAvailableQuantity(invResponse.getAvailableQuantity() - requestDTO.getItemQuantity());
            log.info("Updating Inventory Request {}", invResponse);

            inventoryResponseDTO.setItemInStock(true);
        } else {
            inventoryResponseDTO.setItemInStock(false);
        }
        inventoryResponseDTO.setItemId(requestDTO.getItemId());
        inventoryResponseDTO.setItemDescription(invResponse.getItemDescription());
        inventoryResponseDTO.setUnitPrice(invResponse.getPrice());
        inventoryResponseDTO.setItemQuantity(requestDTO.getItemQuantity());

        return inventoryResponseDTO;
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
