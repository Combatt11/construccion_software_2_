package com.nexora.domain;

import java.time.LocalDateTime;

public class Return {
    private Long id; private Long orderId; private String reason; private LocalDateTime requestDate; private String status;
    public Return() { }
    public Return(Long id,Long orderId,String reason,LocalDateTime requestDate,String status){this.id=id;this.orderId=orderId;this.reason=reason;this.requestDate=requestDate;this.status=status;}
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getOrderId(){return orderId;} public void setOrderId(Long v){orderId=v;}
    public String getReason(){return reason;} public void setReason(String v){reason=v;} public LocalDateTime getRequestDate(){return requestDate;} public void setRequestDate(LocalDateTime v){requestDate=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
