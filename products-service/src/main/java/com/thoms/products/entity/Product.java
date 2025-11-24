package com.thoms.products.entity;

import jakarta.persistence.*;
import lombok.*;

@Data @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq", allocationSize = 1)
    private Integer product_id;
    private String model;
    private Integer price;
    private Integer stock;
    private String category_name;
    private String brand_name;
}
