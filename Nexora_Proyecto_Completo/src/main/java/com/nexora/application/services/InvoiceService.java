package com.nexora.application.services;

import com.nexora.application.ports.in.InvoiceUseCase;
import com.nexora.domain.models.Invoice;
import com.nexora.domain.models.Order;
import com.nexora.enums.OrderStatus;
import java.time.LocalDateTime;

public class InvoiceService implements InvoiceUseCase {
    @Override
    public Invoice generate(Order order, Long invoiceId, String number) {
        if (order.getStatus() != OrderStatus.PAID && order.getStatus() != OrderStatus.DISPATCHED
                && order.getStatus() != OrderStatus.DELIVERED && order.getStatus() != OrderStatus.FINALIZED) {
            throw new IllegalStateException("La factura requiere un pedido pagado.");
        }
        return new Invoice(invoiceId, order.getId(), number, LocalDateTime.now(), order.getTotal(), order.getTotal(), "ISSUED");
    }
}
