package com.thomas.cart.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.thomas.cart.entity.CartRequest;
import com.thomas.cart.service.CartService;
import com.thomas.cart.utility.ParseJWT;
import com.thomas.clients.product.ProductClient;
import com.thomas.clients.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private String extractToken(HttpServletRequest request){
        String jwtToken = "Bearer ";
        jwtToken += request.getParameter("access_token");
        log.info("Token is Bearer {}", jwtToken);

        return jwtToken;
    }

    @GetMapping
    public List<Product> testingFeignClient(HttpServletRequest request){

        String jwtToken = extractToken(request);

        return productClient.getAllProducts(jwtToken);
    }

    @PostMapping
    public void saveProduct(@RequestBody CartRequest cartRequest, HttpServletRequest request){
        String userID = "3";

        try {
            cartService.addItemToCart(cartRequest, ParseJWT.getUserId(extractToken(request)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("JSON processing error on parsing JWT token for UserID");
        }

    }

}
