package com.nexora.adapters.in.rest.dto.responses;

import java.math.BigDecimal;

public record OrderItemResponse(Long id, Long productId, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) { }
