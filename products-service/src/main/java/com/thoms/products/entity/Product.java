package com.thoms.products.entity;

import lombok.*;

import javax.persistence.*;

@Data @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity @Builder
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "product_product_id_seq"
    )
    private Integer product_id;
    private String model;
    private Integer price;
    private Integer stock;
    private String category_name;
    private String brand_name;
}
