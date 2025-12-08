package com.oms.inventoryservice.controller;

import com.oms.inventoryservice.dto.InventoryRequestDTO;
import com.oms.inventoryservice.dto.InventoryResponseDTO;
import com.oms.inventoryservice.entity.Inventory;
import com.oms.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @RequestMapping("check/availableInventory")
    public InventoryResponseDTO checkAvailableInventory(@RequestBody InventoryRequestDTO inventoryRequest){
        log.info("Calling CheckAvailableInventoryByItemId for ItemId = {}", inventoryRequest.getItemId());
        InventoryResponseDTO inventoryResponse = inventoryService.checkInventory(inventoryRequest);
        return inventoryResponse;
    }

    @GetMapping("/itemId")
    public List<Inventory> getInventoryByItemId(@RequestParam Integer itemId){
        List<Inventory> response = inventoryService.getInventoryById(itemId);
        return response;
    }

    @PostMapping("/save")
    public Inventory saveInventory(@RequestParam Integer itemId,
                                   @RequestParam Integer availableQuantity,
                                   @RequestParam Integer itemPrice){
        return inventoryService.saveInventory(itemId, availableQuantity, itemPrice);
    }
}
