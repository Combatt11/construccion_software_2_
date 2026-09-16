package com.nexora.domain;

public class Inventory {
    private Long id; private Long productId; private Long warehouseId;
    private Integer availableQuantity; private Integer reservedQuantity;
    public Inventory() { }
    public Inventory(Long id,Long productId,Long warehouseId,Integer availableQuantity,Integer reservedQuantity){
        this.id=id;this.productId=productId;this.warehouseId=warehouseId;this.availableQuantity=availableQuantity;this.reservedQuantity=reservedQuantity;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getProductId(){return productId;} public void setProductId(Long v){productId=v;}
    public Long getWarehouseId(){return warehouseId;} public void setWarehouseId(Long v){warehouseId=v;}
    public Integer getAvailableQuantity(){return availableQuantity;} public void setAvailableQuantity(Integer v){availableQuantity=v;}
    public Integer getReservedQuantity(){return reservedQuantity;} public void setReservedQuantity(Integer v){reservedQuantity=v;}
}
