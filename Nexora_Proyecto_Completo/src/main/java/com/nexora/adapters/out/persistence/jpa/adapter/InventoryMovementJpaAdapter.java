package com.nexora.adapters.out.persistence.jpa.adapter;

import com.nexora.adapters.out.persistence.jpa.entity.InventoryMovementJpaEntity;
import com.nexora.adapters.out.persistence.jpa.mapper.InventoryMovementJpaMapper;
import com.nexora.adapters.out.persistence.jpa.repository.SpringDataJpaInventoryMovementRepository;
import com.nexora.application.ports.out.InventoryMovementRepositoryPort;
import com.nexora.domain.models.InventoryMovement;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryMovementJpaAdapter implements InventoryMovementRepositoryPort {
    private final SpringDataJpaInventoryMovementRepository repo;

    public InventoryMovementJpaAdapter(SpringDataJpaInventoryMovementRepository repo) { this.repo = repo; }

    @Override
    public InventoryMovement save(InventoryMovement movement) { return InventoryMovementJpaMapper.toDomain(repo.save(InventoryMovementJpaMapper.toEntity(movement))); }

    @Override
    public List<InventoryMovement> findAll() { return repo.findAll().stream().map(InventoryMovementJpaMapper::toDomain).toList(); }
}
