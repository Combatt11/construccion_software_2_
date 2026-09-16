package com.nexora.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.nexora.enums.OrderStatus;

public class Order {
    private Long id; private Long buyerId; private OrderStatus status; private LocalDateTime creationDate; private BigDecimal total;
    private final List<OrderItem> items = new ArrayList<>();
    public Order() { }
    public Order(Long id,Long buyerId,OrderStatus status,LocalDateTime creationDate,BigDecimal total){this.id=id;this.buyerId=buyerId;this.status=status;this.creationDate=creationDate;this.total=total;}
    public Long getId(){return id;} public void setId(Long v){ensureEditable();id=v;}
    public Long getBuyerId(){return buyerId;} public void setBuyerId(Long v){ensureEditable();buyerId=v;}
    public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus v){status=v;}
    public LocalDateTime getCreationDate(){return creationDate;} public void setCreationDate(LocalDateTime v){ensureEditable();creationDate=v;}
    public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){ensureEditable();total=v;}
    public List<OrderItem> getItems(){return items;}
    private void ensureEditable(){if(status==OrderStatus.FINALIZED) throw new IllegalStateException("El pedido finalizado no se puede modificar.");}
}
