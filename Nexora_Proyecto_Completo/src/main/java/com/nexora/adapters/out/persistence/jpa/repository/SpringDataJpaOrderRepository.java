package com.nexora.adapters.out.persistence.jpa.repository;

import com.nexora.adapters.out.persistence.jpa.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaOrderRepository extends JpaRepository<OrderJpaEntity, Long> {
}
