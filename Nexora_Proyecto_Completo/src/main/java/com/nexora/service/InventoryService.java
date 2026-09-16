package com.nexora.service;

import java.time.LocalDateTime;
import com.nexora.domain.Inventory;
import com.nexora.domain.InventoryMovement;
import com.nexora.enums.InventoryMovementType;
import com.nexora.repository.InMemoryRepository;

public class InventoryService {
    private final InMemoryRepository<Inventory> inventories = new InMemoryRepository<>();
    private final InMemoryRepository<InventoryMovement> movements = new InMemoryRepository<>();

    public Inventory createInventory(Long id, Long productId, Long warehouseId, int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("El inventario no puede ser negativo.");
        Inventory inventory = new Inventory(id, productId, warehouseId, quantity, 0);
        inventories.save(inventory);
        recordMovement(id + 1000, id, InventoryMovementType.INCOMING, quantity);
        return inventory;
    }

    public void reserve(Long inventoryId, int quantity, boolean damaged) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        Inventory inventory = get(inventoryId);
        if (damaged) throw new IllegalArgumentException("El inventario dañado no se puede reservar.");
        if (inventory.getAvailableQuantity() < quantity) throw new IllegalArgumentException("No hay cantidad disponible para reservar.");
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        recordMovement(inventoryId + 2000, inventoryId, InventoryMovementType.RESERVATION, quantity);
    }

    public Inventory get(Long id) {
        return inventories.findFirst(i -> id.equals(i.getId())).orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado."));
    }

    public InMemoryRepository<InventoryMovement> getMovements() { return movements; }

    private void recordMovement(Long id, Long inventoryId, InventoryMovementType type, int quantity) {
        movements.save(new InventoryMovement(id, inventoryId, type, quantity, LocalDateTime.now()));
    }
}
