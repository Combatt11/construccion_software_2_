package com.nexora.application.ports.in;

import com.nexora.domain.models.Inventory;

public interface InventoryUseCase {
    Inventory createInventory(Long id, Long productId, Long warehouseId, int quantity);
    void reserve(Long inventoryId, int quantity, boolean damaged);
    Inventory get(Long id);
}
