package com.thoms.products.entity;

import lombok.*;

import javax.persistence.Entity;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private Integer productId;
    private int stock;
}
