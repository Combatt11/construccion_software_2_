package com.nexora.application.ports.out;

import com.nexora.domain.models.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryRepositoryPort {
    Inventory save(Inventory inventory);
    Optional<Inventory> findById(Long id);
    List<Inventory> findAll();
}
