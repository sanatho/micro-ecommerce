package com.thoms.products.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Data @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Category {
    @Id
    private String category_name;
}
