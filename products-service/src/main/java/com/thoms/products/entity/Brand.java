package com.thoms.products.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Data @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Brand {
    @Id
    private String brand_name;
}
