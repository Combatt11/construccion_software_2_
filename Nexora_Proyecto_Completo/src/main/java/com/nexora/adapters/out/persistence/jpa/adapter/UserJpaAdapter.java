package com.nexora.adapters.out.persistence.jpa.adapter;

import com.nexora.adapters.out.persistence.jpa.entity.UserJpaEntity;
import com.nexora.adapters.out.persistence.jpa.mapper.UserJpaMapper;
import com.nexora.adapters.out.persistence.jpa.repository.SpringDataJpaUserRepository;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpaAdapter implements UserRepositoryPort {
    private final SpringDataJpaUserRepository repo;

    public UserJpaAdapter(SpringDataJpaUserRepository repo) { this.repo = repo; }

    @Override
    public User save(User user) {
        UserJpaEntity saved = repo.save(UserJpaMapper.toEntity(user));
        return UserJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) { return repo.findById(id).map(UserJpaMapper::toDomain); }

    @Override
    public Optional<User> findByEmail(String email) { return repo.findByEmail(email).map(UserJpaMapper::toDomain); }

    @Override
    public boolean existsById(Long id) { return repo.existsById(id); }

    @Override
    public boolean existsByEmail(String email) { return repo.findByEmail(email).isPresent(); }

    @Override
    public List<User> findAll() { return repo.findAll().stream().map(UserJpaMapper::toDomain).toList(); }
}
