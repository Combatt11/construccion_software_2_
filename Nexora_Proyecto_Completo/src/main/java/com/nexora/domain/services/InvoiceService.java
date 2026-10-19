package com.nexora.domain.services;

import java.time.LocalDateTime;
import com.nexora.domain.models.Invoice;
import com.nexora.domain.models.Order;
import com.nexora.enums.OrderStatus;

public class InvoiceService {
    public Invoice generate(Order order, Long invoiceId, String number) {
        if (order.getStatus() != OrderStatus.PAID && order.getStatus() != OrderStatus.DISPATCHED
                && order.getStatus() != OrderStatus.DELIVERED && order.getStatus() != OrderStatus.FINALIZED) {
            throw new IllegalStateException("La factura requiere un pedido pagado.");
        }
        return new Invoice(invoiceId, order.getId(), number, LocalDateTime.now(), order.getTotal(), order.getTotal(), "ISSUED");
    }
}
