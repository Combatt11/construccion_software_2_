package com.nexora.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long id; private Long userId; private String status; private LocalDateTime creationDate;
    private final List<CartItem> items = new ArrayList<>();
    public Cart() { }
    public Cart(Long id,Long userId,String status,LocalDateTime creationDate){this.id=id;this.userId=userId;this.status=status;this.creationDate=creationDate;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public LocalDateTime getCreationDate(){return creationDate;} public void setCreationDate(LocalDateTime v){creationDate=v;}
    public List<CartItem> getItems(){return items;}
}
