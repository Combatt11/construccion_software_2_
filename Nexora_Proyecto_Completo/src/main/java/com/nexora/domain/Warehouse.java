package com.nexora.domain;

import com.nexora.enums.WarehouseStatus;
import com.nexora.enums.WarehouseType;

public class Warehouse {
    private Long id; private String name; private String location; private WarehouseType type;
    private WarehouseStatus status; private Long sellerId;
    public Warehouse() { }
    public Warehouse(Long id,String name,String location,WarehouseType type,WarehouseStatus status,Long sellerId){
        this.id=id;this.name=name;this.location=location;this.type=type;this.status=status;this.sellerId=sellerId;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public WarehouseType getType(){return type;} public void setType(WarehouseType v){type=v;}
    public WarehouseStatus getStatus(){return status;} public void setStatus(WarehouseStatus v){status=v;}
    public Long getSellerId(){return sellerId;} public void setSellerId(Long v){sellerId=v;}
}
