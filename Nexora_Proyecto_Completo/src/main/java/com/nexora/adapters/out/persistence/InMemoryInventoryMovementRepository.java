package com.nexora.adapters.out.persistence;

import com.nexora.application.ports.out.InventoryMovementRepositoryPort;
import com.nexora.domain.models.InventoryMovement;
import java.util.ArrayList;
import java.util.List;

public class InMemoryInventoryMovementRepository implements InventoryMovementRepositoryPort {
    private final List<InventoryMovement> data = new ArrayList<>();

    @Override
    public InventoryMovement save(InventoryMovement movement) { data.add(movement); return movement; }

    @Override
    public List<InventoryMovement> findAll() { return new ArrayList<>(data); }
}
