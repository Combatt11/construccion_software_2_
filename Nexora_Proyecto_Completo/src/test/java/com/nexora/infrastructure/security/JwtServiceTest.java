package com.nexora.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import com.nexora.adapters.out.persistence.InMemoryUserRepository;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import org.junit.jupiter.api.Test;

class JwtServiceTest {
    private final UserRepositoryPort users = new InMemoryUserRepository();
    private final JwtService jwtService = new JwtService("TDEATDEATDEATDEATDEATDEATDEATDEA", 3600000L, users);

    @Test
    void generateAndValidateToken() {
        User u = new User(1L, "U", "u@x.com", Role.BUYER, UserStatus.ACTIVE);
        u.setAuthTokenVersion(0);
        users.save(u);
        String token = jwtService.generateToken(u);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token));
        assertEquals(1L, jwtService.extractUserId(token));
        assertEquals(0, jwtService.extractVersion(token));
    }

    @Test
    void invalidTokenReturnsFalse() {
        assertFalse(jwtService.isTokenValid("not-a-token"));
    }

    @Test
    void versionMismatchDetected() {
        User u = new User(1L, "U", "u@x.com", Role.BUYER, UserStatus.ACTIVE);
        u.setAuthTokenVersion(0);
        users.save(u);
        String token = jwtService.generateToken(u);
        u.setAuthTokenVersion(1);
        users.save(u);
        // token version still 0, user version 1
        assertEquals(0, jwtService.extractVersion(token));
        assertEquals(1, users.findById(1L).get().getAuthTokenVersion());
    }
}
