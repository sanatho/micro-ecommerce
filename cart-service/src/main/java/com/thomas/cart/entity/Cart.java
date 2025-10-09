package com.thomas.cart.entity;

import lombok.*;

import javax.persistence.*;

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
