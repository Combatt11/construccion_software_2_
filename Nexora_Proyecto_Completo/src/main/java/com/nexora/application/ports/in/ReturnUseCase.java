package com.nexora.application.ports.in;

import com.nexora.domain.models.Order;
import com.nexora.domain.models.Refund;
import com.nexora.domain.models.Return;
import java.math.BigDecimal;

public interface ReturnUseCase {
    Return request(Order order, Long returnId, String reason);
    Refund refund(Return request, Long refundId, BigDecimal amount);
}
