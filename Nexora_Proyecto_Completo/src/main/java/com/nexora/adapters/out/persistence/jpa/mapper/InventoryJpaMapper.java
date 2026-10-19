package com.nexora.adapters.out.persistence.jpa.mapper;

import com.nexora.adapters.out.persistence.jpa.entity.InventoryJpaEntity;
import com.nexora.domain.models.Inventory;

public class InventoryJpaMapper {
    public static Inventory toDomain(InventoryJpaEntity e) {
        if (e == null) return null;
        return new Inventory(e.getId(), e.getProductId(), e.getWarehouseId(), e.getAvailableQuantity(), e.getReservedQuantity());
    }

    public static InventoryJpaEntity toEntity(Inventory i) {
        if (i == null) return null;
        InventoryJpaEntity e = new InventoryJpaEntity();
        e.setId(i.getId());
        e.setProductId(i.getProductId());
        e.setWarehouseId(i.getWarehouseId());
        e.setAvailableQuantity(i.getAvailableQuantity());
        e.setReservedQuantity(i.getReservedQuantity());
        return e;
    }
}
