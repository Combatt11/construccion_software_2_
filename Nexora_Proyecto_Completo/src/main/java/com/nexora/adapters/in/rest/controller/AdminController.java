package com.nexora.adapters.in.rest.controller;

import com.nexora.adapters.in.rest.dto.requests.RegisterUserRequest;
import com.nexora.adapters.in.rest.dto.responses.UserResponse;
import com.nexora.adapters.in.rest.mapper.UserRestMapper;
import com.nexora.application.ports.in.UserUseCase;
import com.nexora.enums.Role;
import com.nexora.infrastructure.security.SecurityConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class AdminController {
    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserUseCase userUseCase, PasswordEncoder passwordEncoder) {
        this.userUseCase = userUseCase;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterUserRequest req) {
        Role role = Role.BUYER;
        if (req.role() != null && !req.role().isBlank()) {
            try { role = Role.valueOf(req.role()); } catch (IllegalArgumentException ignored) { }
        }
        String hash = passwordEncoder.encode(req.password());
        var user = userUseCase.register(System.currentTimeMillis(), req.name(), req.email(), hash, role);
        return ResponseEntity.ok(UserRestMapper.toResponse(user));
    }
}
