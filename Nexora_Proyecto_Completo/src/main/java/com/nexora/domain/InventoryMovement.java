package com.nexora.domain;

import java.time.LocalDateTime;
import com.nexora.enums.InventoryMovementType;

public class InventoryMovement {
    private Long id; private Long inventoryId; private InventoryMovementType type; private Integer quantity; private LocalDateTime date;
    public InventoryMovement() { }
    public InventoryMovement(Long id,Long inventoryId,InventoryMovementType type,Integer quantity,LocalDateTime date){this.id=id;this.inventoryId=inventoryId;this.type=type;this.quantity=quantity;this.date=date;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getInventoryId(){return inventoryId;} public void setInventoryId(Long v){inventoryId=v;}
    public InventoryMovementType getType(){return type;} public void setType(InventoryMovementType v){type=v;}
    public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
    public LocalDateTime getDate(){return date;} public void setDate(LocalDateTime v){date=v;}
}
