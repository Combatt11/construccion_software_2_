package com.nexora.adapters.in.rest.dto.responses;

import com.nexora.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, Long buyerId, OrderStatus status, LocalDateTime creationDate, BigDecimal total,
                           List<OrderItemResponse> items) { }
