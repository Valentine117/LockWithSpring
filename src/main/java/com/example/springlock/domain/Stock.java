package com.example.springlock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    private Long id;

    private Integer quantity;

    protected Stock() {
    }

    public Stock(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void decrease(int amount) {
        if (quantity - amount < 0) {
            throw new IllegalStateException("재고 부족");
        }
        this.quantity -= amount;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
