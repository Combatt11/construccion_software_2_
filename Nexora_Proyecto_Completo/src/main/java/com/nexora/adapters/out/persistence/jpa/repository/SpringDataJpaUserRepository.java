package com.nexora.adapters.out.persistence.jpa.repository;

import com.nexora.adapters.out.persistence.jpa.entity.UserJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaUserRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByEmail(String email);
}
