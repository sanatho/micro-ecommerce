package com.thomas.cart.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Data
@Getter @Setter
@ToString @Entity
public class Cart {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "cart_id_seq"
    )
    private Integer id;
    private Integer product_id;
    private String user_id;
    private Integer quantity;

    public Cart(Integer product_id, String user_id, Integer quantity) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.quantity = quantity;
    }
}
