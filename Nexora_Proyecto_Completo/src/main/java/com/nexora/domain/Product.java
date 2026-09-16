package com.nexora.domain;

import java.math.BigDecimal;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;

public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductType type;
    private ProductStatus status;
    private Long sellerId;
    private Long categoryId;

    public Product() { }
    public Product(Long id, String name, String description, BigDecimal price, ProductType type,
                   ProductStatus status, Long sellerId, Long categoryId) {
        this.id=id; this.name=name; this.description=description; this.price=price; this.type=type;
        this.status=status; this.sellerId=sellerId; this.categoryId=categoryId;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public ProductType getType(){return type;} public void setType(ProductType v){type=v;}
    public ProductStatus getStatus(){return status;} public void setStatus(ProductStatus v){status=v;}
    public Long getSellerId(){return sellerId;} public void setSellerId(Long v){sellerId=v;}
    public Long getCategoryId(){return categoryId;} public void setCategoryId(Long v){categoryId=v;}
}
