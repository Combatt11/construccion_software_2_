package com.nexora.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.nexora.application.ports.out.InventoryMovementRepositoryPort;
import com.nexora.application.ports.out.InventoryRepositoryPort;
import com.nexora.domain.exceptions.InsufficientStockException;
import com.nexora.domain.models.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InventoryServiceTest {
    private InventoryRepositoryPort inventories;
    private InventoryMovementRepositoryPort movements;
    private InventoryService service;

    @BeforeEach
    void setup() {
        inventories = Mockito.mock(InventoryRepositoryPort.class);
        movements = Mockito.mock(InventoryMovementRepositoryPort.class);
        service = new InventoryService(inventories, movements);
    }

    @Test
    void createInventory() {
        when(inventories.findById(1L)).thenReturn(java.util.Optional.empty());
        Inventory inv = service.createInventory(1L, 2L, 3L, 5);
        assertEquals(5, inv.getAvailableQuantity());
        verify(inventories).save(inv);
        verify(movements).save(any());
    }

    @Test
    void createInventoryNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.createInventory(1L, 2L, 3L, -1));
    }

    @Test
    void reserveSuccess() {
        Inventory inv = new Inventory(1L, 2L, 3L, 5, 0);
        when(inventories.findById(1L)).thenReturn(java.util.Optional.of(inv));
        service.reserve(1L, 2, false);
        assertEquals(3, inv.getAvailableQuantity());
        assertEquals(2, inv.getReservedQuantity());
    }

    @Test
    void reserveTooMuchThrows() {
        Inventory inv = new Inventory(1L, 2L, 3L, 5, 0);
        when(inventories.findById(1L)).thenReturn(java.util.Optional.of(inv));
        assertThrows(InsufficientStockException.class, () -> service.reserve(1L, 6, false));
    }

    @Test
    void reserveDamagedThrows() {
        Inventory inv = new Inventory(1L, 2L, 3L, 5, 0);
        when(inventories.findById(1L)).thenReturn(java.util.Optional.of(inv));
        assertThrows(IllegalArgumentException.class, () -> service.reserve(1L, 1, true));
    }

    @Test
    void reserveZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.reserve(1L, 0, false));
    }

    @Test
    void getNotFoundThrows() {
        when(inventories.findById(99L)).thenReturn(java.util.Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.get(99L));
    }
}
