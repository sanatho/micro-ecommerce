package com.thomas.cart.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Data
@Getter @Setter
@ToString @Entity
public class Cart {
    @Id
    private Integer id;
    private Integer product_id;
    private Integer user_id;
    private Integer quantity;
}
