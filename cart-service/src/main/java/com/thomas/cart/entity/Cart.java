package com.thomas.cart.entity;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder @Data
@Getter @Setter
@ToString @Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_generator")
    @SequenceGenerator(name = "cart_id_generator", sequenceName = "cart_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "user_id")
    private String userId;
    private Integer quantity;

    public Cart(Integer productId, String userId, Integer quantity) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
    }
}
