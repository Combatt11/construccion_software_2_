package com.nexora.adapters.out.persistence.jpa.repository;

import com.nexora.adapters.out.persistence.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaProductRepository extends JpaRepository<ProductJpaEntity, Long> {
}
