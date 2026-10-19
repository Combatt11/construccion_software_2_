package com.nexora.application.services;

import com.nexora.application.ports.in.InventoryUseCase;
import com.nexora.application.ports.out.InventoryRepositoryPort;
import com.nexora.application.ports.out.InventoryMovementRepositoryPort;
import com.nexora.domain.exceptions.InsufficientStockException;
import com.nexora.domain.models.Inventory;
import com.nexora.domain.models.InventoryMovement;
import com.nexora.enums.InventoryMovementType;
import java.time.LocalDateTime;

public class InventoryService implements InventoryUseCase {
    private final InventoryRepositoryPort inventories;
    private final InventoryMovementRepositoryPort movements;

    public InventoryService(InventoryRepositoryPort inventories, InventoryMovementRepositoryPort movements) {
        this.inventories = inventories;
        this.movements = movements;
    }

    @Override
    public Inventory createInventory(Long id, Long productId, Long warehouseId, int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("El inventario no puede ser negativo.");
        Inventory inventory = new Inventory(id, productId, warehouseId, quantity, 0);
        inventories.save(inventory);
        recordMovement(id + 1000, id, InventoryMovementType.INCOMING, quantity);
        return inventory;
    }

    @Override
    public void reserve(Long inventoryId, int quantity, boolean damaged) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        Inventory inventory = get(inventoryId);
        if (damaged) throw new IllegalArgumentException("El inventario dañado no se puede reservar.");
        if (inventory.getAvailableQuantity() < quantity) throw new InsufficientStockException("No hay cantidad disponible para reservar.");
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        recordMovement(inventoryId + 2000, inventoryId, InventoryMovementType.RESERVATION, quantity);
    }

    @Override
    public Inventory get(Long id) {
        return inventories.findById(id).orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado."));
    }

    private void recordMovement(Long id, Long inventoryId, InventoryMovementType type, int quantity) {
        movements.save(new InventoryMovement(id, inventoryId, type, quantity, LocalDateTime.now()));
    }
}
