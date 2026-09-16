package com.nexora.domain;

public class CartItem {
    private Long id; private Long cartId; private Long productId; private Integer quantity;
    public CartItem() { }
    public CartItem(Long id,Long cartId,Long productId,Integer quantity){this.id=id;this.cartId=cartId;this.productId=productId;this.quantity=quantity;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getCartId(){return cartId;} public void setCartId(Long v){cartId=v;}
    public Long getProductId(){return productId;} public void setProductId(Long v){productId=v;}
    public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
}
