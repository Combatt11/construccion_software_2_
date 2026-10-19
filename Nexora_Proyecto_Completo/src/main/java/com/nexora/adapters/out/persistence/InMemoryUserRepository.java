package com.nexora.adapters.out.persistence;

import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepositoryPort {
    private final List<User> data = new ArrayList<>();

    @Override
    public User save(User user) { data.add(user); return user; }

    @Override
    public Optional<User> findById(Long id) {
        return data.stream().filter(u -> id != null && id.equals(u.getId())).findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return data.stream().filter(u -> email != null && email.equalsIgnoreCase(u.getEmail())).findFirst();
    }

    @Override
    public boolean existsById(Long id) { return findById(id).isPresent(); }

    @Override
    public boolean existsByEmail(String email) { return findByEmail(email).isPresent(); }

    @Override
    public List<User> findAll() { return new ArrayList<>(data); }
}
