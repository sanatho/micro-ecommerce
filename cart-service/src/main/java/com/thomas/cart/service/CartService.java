package com.thomas.cart.service;

import com.thomas.cart.entity.Cart;
import com.thomas.cart.entity.CartRequest;
import com.thomas.cart.repository.CartRepository;
import com.thomas.clients.product.ProductClient;
import com.thomas.clients.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @Slf4j
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    public void addItemToCart(CartRequest cartRequest, String userID){

        int productId = cartRequest.getProductId();
        int quantity = cartRequest.getQuantity();

        userID = userID.replace("\"", "");

        log.info("add item {} to cart", productId);
        log.info("userID is {}", userID);

        if(userID == null){
            log.error("User id not valid");
            throw new IllegalArgumentException("User id not valid");
        }

        if(quantity < 1){
            log.error("Quantity not valid");
            throw new IllegalArgumentException("Quantity not valid");
        }

        if(!isProductIdValid(productId)){
            log.error("Product id not valid");
            throw new IllegalArgumentException("Product id not valid");
        }

        if(!isProductInStock(productId, quantity)){
            //TODO update stock after buy
            log.warn("Product out of stock");
            throw new IllegalArgumentException("Product out of stock");
        }

        Cart newCart = new Cart(productId, userID, quantity);

        cartRepository.save(newCart);
    }

    private boolean isProductIdValid(Integer productId){
        try{
            log.info("Before fetching product");
            Product product = productClient.getProduct(productId);
            log.info("Product fetched successfully");
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    private boolean isProductInStock(Integer productId, int quantity){

        Integer stock = 0;

        try{
            log.info("Before fetching product");
            stock = productClient.getStock(productId);
            log.info("Product fetched successfully");
            return true;
        }catch (Exception exception){
            log.error("Error fetching stock");
        }

        return stock > quantity;
    }

}
