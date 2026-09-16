package com.nexora.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Refund {
    private Long id; private Long returnId; private BigDecimal amount; private LocalDateTime date; private String status;
    public Refund() { }
    public Refund(Long id,Long returnId,BigDecimal amount,LocalDateTime date,String status){this.id=id;this.returnId=returnId;this.amount=amount;this.date=date;this.status=status;}
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getReturnId(){return returnId;} public void setReturnId(Long v){returnId=v;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;} public LocalDateTime getDate(){return date;} public void setDate(LocalDateTime v){date=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
