package com.nexora.adapters.out.persistence.jpa.repository;

import com.nexora.adapters.out.persistence.jpa.entity.InventoryMovementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaInventoryMovementRepository extends JpaRepository<InventoryMovementJpaEntity, Long> {
}
