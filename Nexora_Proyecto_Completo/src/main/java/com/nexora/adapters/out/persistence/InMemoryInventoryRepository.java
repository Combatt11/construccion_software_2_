package com.nexora.adapters.out.persistence;

import com.nexora.application.ports.out.InventoryRepositoryPort;
import com.nexora.domain.models.Inventory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryInventoryRepository implements InventoryRepositoryPort {
    private final List<Inventory> data = new ArrayList<>();

    @Override
    public Inventory save(Inventory inventory) { data.add(inventory); return inventory; }

    @Override
    public Optional<Inventory> findById(Long id) {
        return data.stream().filter(i -> id != null && id.equals(i.getId())).findFirst();
    }

    @Override
    public List<Inventory> findAll() { return new ArrayList<>(data); }
}
