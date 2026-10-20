package com.nexora.application.services;

import static org.junit.jupiter.api.Assertions.*;
import com.nexora.domain.models.Invoice;
import com.nexora.domain.models.Order;
import com.nexora.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class InvoiceServiceTest {
    private final InvoiceService service = new InvoiceService();

    @Test
    void generateInvoiceForPaidOrder() {
        Order o = new Order(1L, 2L, OrderStatus.PAID, LocalDateTime.now(), new BigDecimal("100"));
        Invoice inv = service.generate(o, 1L, "INV-1");
        assertEquals("ISSUED", inv.getStatus());
        assertEquals(new BigDecimal("100"), inv.getTotal());
    }

    @Test
    void generateInvoiceForPendingThrows() {
        Order o = new Order(1L, 2L, OrderStatus.PENDING_PAYMENT, LocalDateTime.now(), new BigDecimal("100"));
        assertThrows(IllegalStateException.class, () -> service.generate(o, 1L, "INV-1"));
    }
}
