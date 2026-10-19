package com.nexora.application.ports.out;

import com.nexora.domain.models.User;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
    List<User> findAll();
}
