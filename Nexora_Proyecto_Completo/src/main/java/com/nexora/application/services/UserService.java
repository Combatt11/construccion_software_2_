package com.nexora.application.services;

import com.nexora.application.ports.in.UserUseCase;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;

public class UserService implements UserUseCase {
    private final UserRepositoryPort users;

    public UserService(UserRepositoryPort users) {
        this.users = users;
    }

    @Override
    public User registerBuyer(Long id, String name, String email) {
        return register(id, name, email, Role.BUYER);
    }

    @Override
    public User register(Long id, String name, String email, Role role) {
        validateText(name, "El nombre es obligatorio.");
        validateText(email, "El correo es obligatorio.");
        if (id == null || users.existsById(id)) {
            throw new IllegalArgumentException("El identificador ya existe o es inválido.");
        }
        if (users.existsByEmail(email)) {
            throw new IllegalArgumentException("El correo ya existe.");
        }
        if (role == null) throw new IllegalArgumentException("El rol es obligatorio.");
        User user = new User(id, name, email, role, UserStatus.ACTIVE);
        users.save(user);
        return user;
    }

    @Override
    public User onboardSeller(User administrator, Long id, String name, String email) {
        if (administrator == null || administrator.getRole() != Role.ADMINISTRATOR) {
            throw new IllegalArgumentException("Solo un administrador puede incorporar vendedores.");
        }
        return register(id, name, email, Role.SELLER);
    }

    private void validateText(String value, String message) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
    }
}
