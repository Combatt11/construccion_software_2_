package com.nexora.application.ports.in;

import com.nexora.domain.models.Invoice;
import com.nexora.domain.models.Order;

public interface InvoiceUseCase {
    Invoice generate(Order order, Long invoiceId, String number);
}
