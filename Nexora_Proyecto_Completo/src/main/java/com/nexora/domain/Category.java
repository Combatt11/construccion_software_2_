package com.nexora.domain;

import com.nexora.enums.ProductStatus;

public class Category {
    private Long id; private String name; private String description; private ProductStatus status;
    public Category() { }
    public Category(Long id,String name,String description,ProductStatus status){this.id=id;this.name=name;this.description=description;this.status=status;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public ProductStatus getStatus(){return status;} public void setStatus(ProductStatus v){status=v;}
}
