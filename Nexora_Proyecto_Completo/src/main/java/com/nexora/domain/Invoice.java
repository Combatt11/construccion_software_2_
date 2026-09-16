package com.nexora.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {
    private Long id; private Long orderId; private String number; private LocalDateTime issueDate; private BigDecimal subtotal; private BigDecimal total; private String status;
    public Invoice() { }
    public Invoice(Long id,Long orderId,String number,LocalDateTime issueDate,BigDecimal subtotal,BigDecimal total,String status){this.id=id;this.orderId=orderId;this.number=number;this.issueDate=issueDate;this.subtotal=subtotal;this.total=total;this.status=status;}
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getOrderId(){return orderId;} public void setOrderId(Long v){orderId=v;}
    public String getNumber(){return number;} public void setNumber(String v){number=v;} public LocalDateTime getIssueDate(){return issueDate;} public void setIssueDate(LocalDateTime v){issueDate=v;}
    public BigDecimal getSubtotal(){return subtotal;} public void setSubtotal(BigDecimal v){subtotal=v;} public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
