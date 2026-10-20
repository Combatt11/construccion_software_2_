package com.nexora.application.services;

import static org.junit.jupiter.api.Assertions.*;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.Refund;
import com.nexora.domain.models.Return;
import com.nexora.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class ReturnServiceTest {
    private final ReturnService service = new ReturnService();

    @Test
    void requestReturnForDelivered() {
        Order o = new Order(1L, 2L, OrderStatus.DELIVERED, LocalDateTime.now(), BigDecimal.TEN);
        Return r = service.request(o, 1L, "defecto");
        assertEquals("REQUESTED", r.getStatus());
    }

    @Test
    void requestReturnForPendingThrows() {
        Order o = new Order(1L, 2L, OrderStatus.PENDING_PAYMENT, LocalDateTime.now(), BigDecimal.TEN);
        assertThrows(IllegalStateException.class, () -> service.request(o, 1L, "defecto"));
    }

    @Test
    void refundSuccess() {
        Order o = new Order(1L, 2L, OrderStatus.DELIVERED, LocalDateTime.now(), BigDecimal.TEN);
        Return r = service.request(o, 1L, "defecto");
        Refund rf = service.refund(r, 1L, new BigDecimal("5"));
        assertEquals("PROCESSED", rf.getStatus());
        assertEquals("APPROVED", r.getStatus());
    }

    @Test
    void refundZeroThrows() {
        Order o = new Order(1L, 2L, OrderStatus.DELIVERED, LocalDateTime.now(), BigDecimal.TEN);
        Return r = service.request(o, 1L, "defecto");
        assertThrows(IllegalArgumentException.class, () -> service.refund(r, 1L, BigDecimal.ZERO));
    }
}
