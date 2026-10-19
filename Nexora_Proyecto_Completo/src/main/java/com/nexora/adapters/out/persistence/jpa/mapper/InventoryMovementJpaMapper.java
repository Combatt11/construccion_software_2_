package com.nexora.adapters.out.persistence.jpa.mapper;

import com.nexora.adapters.out.persistence.jpa.entity.InventoryMovementJpaEntity;
import com.nexora.domain.models.InventoryMovement;
import com.nexora.enums.InventoryMovementType;

public class InventoryMovementJpaMapper {
    public static InventoryMovement toDomain(InventoryMovementJpaEntity e) {
        if (e == null) return null;
        return new InventoryMovement(e.getId(), e.getInventoryId(), e.getType() == null ? null : InventoryMovementType.valueOf(e.getType()), e.getQuantity(), e.getDate());
    }

    public static InventoryMovementJpaEntity toEntity(InventoryMovement m) {
        if (m == null) return null;
        InventoryMovementJpaEntity e = new InventoryMovementJpaEntity();
        e.setId(m.getId());
        e.setInventoryId(m.getInventoryId());
        e.setType(m.getType() == null ? null : m.getType().name());
        e.setQuantity(m.getQuantity());
        e.setDate(m.getDate());
        return e;
    }
}
