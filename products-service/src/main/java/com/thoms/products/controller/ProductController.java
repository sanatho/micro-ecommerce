package com.thoms.products.controller;

import com.thoms.products.entity.Product;
import com.thoms.products.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
//    @RolesAllowed("user")
    public Product getProduct(@PathVariable("productId") Integer productId){
        return productService.getProduct(productId);
    }

    @GetMapping("/")
    @RolesAllowed("admin")
    public List<Product> getAllProducts(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        log.info("Received header with jwt is {}", authorization);
        return productService.getAllProducts();
    }

    @PostMapping
//    @RolesAllowed("admin")
    public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{productId}")
//    @RolesAllowed("admin")
    public void deleteProduct(@PathVariable("productId") Integer productId){
        productService.deleteProduct(productId);
    }

    @PatchMapping("/{productId}")
//    @RolesAllowed("admin")
    public Product editProduct(@RequestBody Product product,
                               @PathVariable("productId") Integer productId){
        return productService.editProduct(product, productId);
    }

    @GetMapping("/category/{category}")
//    @RolesAllowed("user")
    public List<Product> findByCategory(@PathVariable("category") String category){
        return productService.findByCategory(category);
    }

    @GetMapping("/brand/{brand}")
//    @RolesAllowed("user")
    public List<Product> findByBrand(@PathVariable("brand") String brand){
        return productService.findByBrand(brand);
    }

    @GetMapping("/stock/{product_id}")
//    @RolesAllowed("user")
    public Integer getStock(@PathVariable("product_id") Integer product_id){
        return productService.getStock(product_id);
    }

    @GetMapping("/admin")
//    @RolesAllowed("admin")
    public boolean isAdminUser(){
        return true;
    }

}
