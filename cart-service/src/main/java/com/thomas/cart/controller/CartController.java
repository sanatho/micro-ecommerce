package com.thomas.cart.controller;

import com.thomas.clients.product.entity.Product;
import com.thomas.clients.product.ProductClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart/")
@AllArgsConstructor
@Slf4j
public class CartController {

    private final ProductClient productClient;

    @GetMapping
    public List<Product> testingFeignClient(HttpServletRequest request){
        //TODO sistemare jwt attraverso feign client
        return productClient.getAllProducts();
    }

}