package com.thomas.cart.controller;


import com.thomas.cart.entity.Cart;
import com.thomas.cart.entity.CartRequest;
import com.thomas.cart.service.CartService;
import com.thomas.clients.product.ProductClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
@Slf4j
public class CartController {

    private final ProductClient productClient;
    private final CartService cartService;

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

    @GetMapping
    public List<Cart> getCart(@AuthenticationPrincipal Jwt jwt){
        return cartService.getCart(jwt.getClaim("sub"));
    }

}
