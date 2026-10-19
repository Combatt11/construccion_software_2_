package com.nexora.adapters.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements")
public class InventoryMovementJpaEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long inventoryId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private LocalDateTime date;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInventoryId() { return inventoryId; }
    public void setInventoryId(Long inventoryId) { this.inventoryId = inventoryId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
