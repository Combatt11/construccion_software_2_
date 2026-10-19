package com.nexora.application.services;

import com.nexora.application.ports.in.ReturnUseCase;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.Refund;
import com.nexora.domain.models.Return;
import com.nexora.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReturnService implements ReturnUseCase {
    @Override
    public Return request(Order order, Long returnId, String reason) {
        if (order.getStatus() != OrderStatus.DELIVERED && order.getStatus() != OrderStatus.FINALIZED)
            throw new IllegalStateException("La devolución requiere un pedido entregado.");
        return new Return(returnId, order.getId(), reason, LocalDateTime.now(), "REQUESTED");
    }

    @Override
    public Refund refund(Return request, Long refundId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El reembolso debe ser mayor que cero.");
        request.setStatus("APPROVED");
        return new Refund(refundId, request.getId(), amount, LocalDateTime.now(), "PROCESSED");
    }
}
