package com.thomas.clients.product.entity;

import lombok.*;

@Data @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Product {
    private Integer product_id;
    private String model;
    private Integer price;
    private Integer stock;
    private String category_name;
    private String brand_name;
}
