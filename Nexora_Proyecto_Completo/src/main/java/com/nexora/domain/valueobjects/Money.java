package com.nexora.domain.valueobjects;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money {
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("El monto es obligatorio.");
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("El monto no puede ser negativo.");
        this.amount = amount;
    }

    public BigDecimal getAmount() { return amount; }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        return amount.compareTo(((Money) o).amount) == 0;
    }

    @Override
    public int hashCode() { return Objects.hash(amount); }
}
