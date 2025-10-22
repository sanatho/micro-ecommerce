package com.thoms.products.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Category {
    @Id
    private String category_name;
}
