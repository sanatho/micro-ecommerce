package com.thomas.cart.entity;

import lombok.Data;

@Data
public class CartRequest {
    private Integer productId;
    private Integer quantity;
}
