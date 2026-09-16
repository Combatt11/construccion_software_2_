package com.nexora.service;

import com.nexora.domain.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import com.nexora.repository.InMemoryRepository;

public class UserService {
    private final InMemoryRepository<User> users = new InMemoryRepository<>();

    public User registerBuyer(Long id, String name, String email) {
        return register(id, name, email, Role.BUYER);
    }

    public User register(Long id, String name, String email, Role role) {
        validateText(name, "El nombre es obligatorio.");
        validateText(email, "El correo es obligatorio.");
        if (id == null || users.findFirst(u -> id.equals(u.getId())).isPresent())
            throw new IllegalArgumentException("El identificador ya existe o es inválido.");
        if (users.findFirst(u -> email.equalsIgnoreCase(u.getEmail())).isPresent())
            throw new IllegalArgumentException("El correo ya existe.");
        if (role == null) throw new IllegalArgumentException("El rol es obligatorio.");
        User user = new User(id, name, email, role, UserStatus.ACTIVE);
        users.save(user);
        return user;
    }

    public User onboardSeller(User administrator, Long id, String name, String email) {
        if (administrator == null || administrator.getRole() != Role.ADMINISTRATOR)
            throw new IllegalArgumentException("Solo un administrador puede incorporar vendedores.");
        return register(id, name, email, Role.SELLER);
    }

    public InMemoryRepository<User> getRepository() { return users; }

    private void validateText(String value, String message) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
    }
}
