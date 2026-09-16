package com.nexora.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.nexora.domain.Order;
import com.nexora.domain.Refund;
import com.nexora.domain.Return;
import com.nexora.enums.OrderStatus;

public class ReturnService {
    public Return request(Order order, Long returnId, String reason) {
        if (order.getStatus() != OrderStatus.DELIVERED && order.getStatus() != OrderStatus.FINALIZED)
            throw new IllegalStateException("La devolución requiere un pedido entregado.");
        return new Return(returnId, order.getId(), reason, LocalDateTime.now(), "REQUESTED");
    }

    public Refund refund(Return request, Long refundId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El reembolso debe ser mayor que cero.");
        request.setStatus("APPROVED");
        return new Refund(refundId, request.getId(), amount, LocalDateTime.now(), "PROCESSED");
    }
}
