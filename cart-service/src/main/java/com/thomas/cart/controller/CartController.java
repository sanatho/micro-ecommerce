package com.thomas.cart.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.thomas.cart.entity.CartRequest;
import com.thomas.cart.service.CartService;
import com.thomas.cart.utility.ParseJWT;
import com.thomas.clients.product.ProductClient;
import com.thomas.clients.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
@Slf4j
public class CartController {

    private final ProductClient productClient;
    private final CartService cartService;

//    @GetMapping
//    public List<Product> testingFeignClient(@AuthenticationPrincipal Jwt jwt){
//        log.info("JWT subject: {}", jwt.getSubject());
//        return productClient.getAllProducts();
//    }

    @PostMapping
    public void saveProduct(@RequestBody CartRequest cartRequest, @AuthenticationPrincipal Jwt jwt){
        cartService.addItemToCart(cartRequest, jwt.getClaim("sub"));
    }

    @DeleteMapping("/{id}")
    public void removeItemFromCart(@PathVariable("id") Integer id, @AuthenticationPrincipal Jwt jwt){
        cartService.removeItem(id, jwt.getClaim("sub"));
    }

    @PatchMapping
    public void editCartQuantity(@RequestBody CartRequest cartRequest, @AuthenticationPrincipal Jwt jwt){
        cartService.editCart(cartRequest, jwt.getClaim("sub"));
    }

}
