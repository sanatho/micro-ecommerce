package com.thomas.cart.controller;

import com.thomas.cart.entity.Cart;
import com.thomas.cart.entity.CartRequest;
import com.thomas.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Add a new product in the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added the product in the cart",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping
    public void saveProduct(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "The new product to insert into the cart", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartRequest.class),
                    examples = @ExampleObject(value = "{ \"productId\": \"1\", \"quantity\": \"5\" }")))
            @RequestBody CartRequest cartRequest, @AuthenticationPrincipal Jwt jwt){
        cartService.addItemToCart(cartRequest, jwt.getClaim("sub"));
    }

    @Operation(summary = "Delete user's cart by cart's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted an element from user's cart",
                    content = {@Content(mediaType = "application/json"
                    )})
    })
    @DeleteMapping("/{id}")
    public void removeItemFromCart(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product ID that will be removed from the user's cart", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class),
                    examples = @ExampleObject(value = "{ \"productId\": \"1\"}")))
            @PathVariable("id") Integer id, @AuthenticationPrincipal Jwt jwt){
        cartService.removeItem(id, jwt.getClaim("sub"));
    }

    @Operation(summary = "Edit the quantity of a product in the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited correctly the quantity",
                    content = {@Content(mediaType = "application/json")})
    })
    @PatchMapping
    public void editCartQuantity(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product with the quantity to edit", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartRequest.class),
                    examples = @ExampleObject(value = "{ \"productId\": \"1\", \"quantity\": \"5\" }")))
                                @RequestBody CartRequest cartRequest, @AuthenticationPrincipal Jwt jwt){
        cartService.editCart(cartRequest, jwt.getClaim("sub"));
    }

    @Operation(summary = "Get the cart's content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All the products inside the cart",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartRequest.class))})
    })
    @GetMapping
    public List<Cart> getCart(@AuthenticationPrincipal Jwt jwt){
        return cartService.getCart(jwt.getClaim("sub"));
    }

}
