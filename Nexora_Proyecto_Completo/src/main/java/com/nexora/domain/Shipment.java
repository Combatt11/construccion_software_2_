package com.nexora.domain;

import java.time.LocalDateTime;
import com.nexora.enums.ShipmentStatus;

public class Shipment {
    private Long id; private Long orderId; private String deliveryAddress; private ShipmentStatus status; private LocalDateTime dispatchDate; private LocalDateTime deliveryDate;
    public Shipment() { }
    public Shipment(Long id,Long orderId,String deliveryAddress,ShipmentStatus status,LocalDateTime dispatchDate,LocalDateTime deliveryDate){this.id=id;this.orderId=orderId;this.deliveryAddress=deliveryAddress;this.status=status;this.dispatchDate=dispatchDate;this.deliveryDate=deliveryDate;}
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getOrderId(){return orderId;} public void setOrderId(Long v){orderId=v;}
    public String getDeliveryAddress(){return deliveryAddress;} public void setDeliveryAddress(String v){deliveryAddress=v;} public ShipmentStatus getStatus(){return status;} public void setStatus(ShipmentStatus v){status=v;}
    public LocalDateTime getDispatchDate(){return dispatchDate;} public void setDispatchDate(LocalDateTime v){dispatchDate=v;} public LocalDateTime getDeliveryDate(){return deliveryDate;} public void setDeliveryDate(LocalDateTime v){deliveryDate=v;}
}
