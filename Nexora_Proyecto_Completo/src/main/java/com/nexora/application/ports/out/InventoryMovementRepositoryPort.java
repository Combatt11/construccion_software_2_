package com.nexora.application.ports.out;

import com.nexora.domain.models.InventoryMovement;
import java.util.List;

public interface InventoryMovementRepositoryPort {
    InventoryMovement save(InventoryMovement movement);
    List<InventoryMovement> findAll();
}
