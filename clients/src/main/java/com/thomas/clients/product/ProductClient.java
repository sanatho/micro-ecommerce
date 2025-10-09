package com.thomas.clients.product;

import com.thomas.clients.product.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(
        name = "products-service"
)
@RequestMapping("/api/v1/product")
public interface ProductClient {

        @GetMapping("/{productId}")
        public Product getProduct(@PathVariable("productId") Integer productId);

        @GetMapping("/")
        List<Product> getAllProducts();

        @PostMapping
        public Product saveProduct(@RequestBody Product product);

        @DeleteMapping("/{productId}")
        public void deleteProduct(@PathVariable("productId") Integer productId);

        @PatchMapping("/{productId}")
        public Product editProduct(@RequestBody Product product, @PathVariable("productId") Integer productId);

        @GetMapping("/category/{category}")
        public List<Product> findByCategory(@PathVariable("category") String category);

        @GetMapping("/brand/{brand}")
        public List<Product> findByBrand(@PathVariable("brand") String brand);

        @GetMapping("/stock/{product_id}")
        public Integer getStock(@PathVariable("product_id") Integer product_id);

}

