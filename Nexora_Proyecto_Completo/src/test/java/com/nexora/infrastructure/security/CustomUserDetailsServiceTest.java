package com.nexora.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsServiceTest {
    @Test
    void loadUserByUsernameSuccess() {
        UserRepositoryPort users = Mockito.mock(UserRepositoryPort.class);
        User u = new User(1L, "U", "u@x.com", Role.BUYER, UserStatus.ACTIVE);
        u.setPassword("hash");
        when(users.findByEmail("u@x.com")).thenReturn(java.util.Optional.of(u));
        CustomUserDetailsService service = new CustomUserDetailsService(users);
        var details = service.loadUserByUsername("u@x.com");
        assertEquals("u@x.com", details.getUsername());
        assertEquals("hash", details.getPassword());
    }

    @Test
    void loadUserByUsernameNotFoundThrows() {
        UserRepositoryPort users = Mockito.mock(UserRepositoryPort.class);
        when(users.findByEmail("x@x.com")).thenReturn(java.util.Optional.empty());
        CustomUserDetailsService service = new CustomUserDetailsService(users);
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("x@x.com"));
    }
}
