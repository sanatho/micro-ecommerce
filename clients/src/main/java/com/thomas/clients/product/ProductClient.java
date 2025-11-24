package com.thomas.clients.product;

import com.thomas.clients.product.entity.Product;
import com.thomas.clients.product.entity.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "products-service",
        path = "/api/v1/product"
)
public interface ProductClient {

        @GetMapping("/{productId}")
        Product getProduct(@PathVariable("productId") Integer productId);

        @GetMapping("/")
        List<Product> getAllProducts();

        @PostMapping
        Product saveProduct(@RequestBody Product product);

        @DeleteMapping("/{productId}")
         void deleteProduct(@PathVariable("productId") Integer productId);

        @PatchMapping("/{productId}")
        Product editProduct(@RequestBody Product product, @PathVariable("productId") Integer productId);

        @GetMapping("/category/{category}")
        List<Product> findByCategory(@PathVariable("category") String category);

        @GetMapping("/brand/{brand}")
        List<Product> findByBrand(@PathVariable("brand") String brand);

        @GetMapping("/stock/{product_id}")
        Integer getStock(@PathVariable("product_id") Integer product_id);

        @PostMapping("/stock/")
        void setStock(@RequestBody ProductRequest product);

}

