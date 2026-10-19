package com.nexora.adapters.out.persistence.jpa.repository;

import com.nexora.adapters.out.persistence.jpa.entity.InventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaInventoryRepository extends JpaRepository<InventoryJpaEntity, Long> {
}
