package com.nexora.adapters.out.persistence.jpa.adapter;

import com.nexora.adapters.out.persistence.jpa.entity.InventoryJpaEntity;
import com.nexora.adapters.out.persistence.jpa.mapper.InventoryJpaMapper;
import com.nexora.adapters.out.persistence.jpa.repository.SpringDataJpaInventoryRepository;
import com.nexora.application.ports.out.InventoryRepositoryPort;
import com.nexora.domain.models.Inventory;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryJpaAdapter implements InventoryRepositoryPort {
    private final SpringDataJpaInventoryRepository repo;

    public InventoryJpaAdapter(SpringDataJpaInventoryRepository repo) { this.repo = repo; }

    @Override
    public Inventory save(Inventory inventory) { return InventoryJpaMapper.toDomain(repo.save(InventoryJpaMapper.toEntity(inventory))); }

    @Override
    public Optional<Inventory> findById(Long id) { return repo.findById(id).map(InventoryJpaMapper::toDomain); }

    @Override
    public List<Inventory> findAll() { return repo.findAll().stream().map(InventoryJpaMapper::toDomain).toList(); }
}
