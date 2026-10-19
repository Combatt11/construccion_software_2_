package com.nexora.application.services;

import com.nexora.application.ports.in.UserUseCase;
import com.nexora.application.ports.out.AuditPort;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import java.util.Map;
import org.slf4j.MDC;

public class UserService implements UserUseCase {
    private final UserRepositoryPort users;
    private final AuditPort audit;

    public UserService(UserRepositoryPort users, AuditPort audit) {
        this.users = users;
        this.audit = audit;
    }

    @Override
    public User registerBuyer(Long id, String name, String email) {
        return register(id, name, email, null, Role.BUYER);
    }

    @Override
    public User register(Long id, String name, String email, Role role) {
        return register(id, name, email, null, role);
    }

    public User register(Long id, String name, String email, String passwordHash, Role role) {
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
        user.setPassword(passwordHash);
        user.setAuthTokenVersion(0);
        users.save(user);
        audit.record(MDC.get("requestId"), user.getId(), "REGISTER_USER", "User", user.getId(),
                Map.of("email", email, "role", role.name()), "SUCCESS");
        return user;
    }

    @Override
    public User onboardSeller(User administrator, Long id, String name, String email) {
        if (administrator == null || administrator.getRole() != Role.ADMINISTRATOR) {
            throw new IllegalArgumentException("Solo un administrador puede incorporar vendedores.");
        }
        User seller = register(id, name, email, null, Role.SELLER);
        audit.record(MDC.get("requestId"), administrator.getId(), "ONBOARD_SELLER", "User", seller.getId(),
                Map.of("sellerEmail", email), "SUCCESS");
        return seller;
    }

    private void validateText(String value, String message) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
    }
}
