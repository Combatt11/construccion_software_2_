package com.nexora.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.nexora.application.ports.out.AuditPort;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserServiceTest {
    private UserRepositoryPort users;
    private AuditPort audit;
    private UserService service;

    @BeforeEach
    void setup() {
        users = Mockito.mock(UserRepositoryPort.class);
        audit = Mockito.mock(AuditPort.class);
        service = new UserService(users, audit);
    }

    @Test
    void registerBuyerCreatesUser() {
        when(users.existsById(1L)).thenReturn(false);
        when(users.existsByEmail("a@x.com")).thenReturn(false);
        User u = service.registerBuyer(1L, "Ana", "a@x.com");
        assertEquals(Role.BUYER, u.getRole());
        assertEquals(UserStatus.ACTIVE, u.getStatus());
        verify(users).save(u);
        verify(audit).record(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void registerDuplicateIdThrows() {
        when(users.existsById(1L)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.registerBuyer(1L, "Ana", "a@x.com"));
    }

    @Test
    void registerDuplicateEmailThrows() {
        when(users.existsById(1L)).thenReturn(false);
        when(users.existsByEmail("a@x.com")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.registerBuyer(1L, "Ana", "a@x.com"));
    }

    @Test
    void registerNullRoleThrows() {
        when(users.existsById(1L)).thenReturn(false);
        when(users.existsByEmail("a@x.com")).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.register(1L, "Ana", "a@x.com", null));
    }

    @Test
    void onboardSellerByAdmin() {
        User admin = new User(9L, "Admin", "admin@x.com", Role.ADMINISTRATOR, UserStatus.ACTIVE);
        when(users.existsById(2L)).thenReturn(false);
        when(users.existsByEmail("s@x.com")).thenReturn(false);
        User seller = service.onboardSeller(admin, 2L, "Seller", "s@x.com");
        assertEquals(Role.SELLER, seller.getRole());
    }

    @Test
    void onboardSellerByNonAdminThrows() {
        User buyer = new User(9L, "Buyer", "b@x.com", Role.BUYER, UserStatus.ACTIVE);
        assertThrows(IllegalArgumentException.class, () -> service.onboardSeller(buyer, 2L, "Seller", "s@x.com"));
    }

    @Test
    void registerWithPasswordHash() {
        when(users.existsById(3L)).thenReturn(false);
        when(users.existsByEmail("p@x.com")).thenReturn(false);
        User u = service.register(3L, "P", "p@x.com", "hash", Role.BUYER);
        assertEquals("hash", u.getPassword());
    }
}
