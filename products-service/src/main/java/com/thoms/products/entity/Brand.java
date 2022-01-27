package com.thoms.products.entity;

import lombok.*;

import javax.persistence.*;

@Data @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Brand {
    @Id
    private String brand_name;
}
