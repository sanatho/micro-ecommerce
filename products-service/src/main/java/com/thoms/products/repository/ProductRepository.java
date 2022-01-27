package com.thoms.products.repository;

import com.thoms.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM Product AS P WHERE P.category_name= ?1", nativeQuery = true)
    List<Product> findProductByCategoryName(String categoryName);

    @Query(value = "SELECT * FROM Product AS P WHERE P.brand_name= ?1", nativeQuery = true)
    List<Product> findProductByBrandName(String categoryName);
}
