package com.nexora.domain;

import java.math.BigDecimal;

public class OrderItem {
    private Long id; private Long orderId; private Long productId; private Integer quantity; private BigDecimal unitPrice; private BigDecimal subtotal;
    public OrderItem() { }
    public OrderItem(Long id,Long orderId,Long productId,Integer quantity,BigDecimal unitPrice,BigDecimal subtotal){this.id=id;this.orderId=orderId;this.productId=productId;this.quantity=quantity;this.unitPrice=unitPrice;this.subtotal=subtotal;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getOrderId(){return orderId;} public void setOrderId(Long v){orderId=v;}
    public Long getProductId(){return productId;} public void setProductId(Long v){productId=v;}
    public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal v){unitPrice=v;}
    public BigDecimal getSubtotal(){return subtotal;} public void setSubtotal(BigDecimal v){subtotal=v;}
}
