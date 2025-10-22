package com.thomas.cart.service;

import com.thomas.cart.entity.Cart;
import com.thomas.cart.entity.CartRequest;
import com.thomas.cart.repository.CartRepository;
import com.thomas.clients.product.ProductClient;
import com.thomas.clients.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("testing")
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductClient productClient;

    private CartService cartService;

    @BeforeEach
    void setUp(){
        cartService = new CartService(cartRepository, productClient);
    }

    @Test
    @DisplayName("Save item successfully")
    void addItemToCart() {
        CartRequest cartRequestTest = new CartRequest(3, 1);
        String userID = UUID.randomUUID().toString();

        Cart finalCart = new Cart(cartRequestTest.getProductId(), userID, cartRequestTest.getQuantity());

        given(productClient.getStock(cartRequestTest.getProductId())).willReturn(5);
        given(productClient.getProduct(cartRequestTest.getProductId())).willReturn(new Product());

        cartService.addItemToCart(cartRequestTest, userID);
        verify(cartRepository).save(finalCart);
    }

	@Test
	@DisplayName("UserID null")
	void userIDNull() {
		CartRequest cartRequestTest = new CartRequest(3, 1);
		String notValidUserId = "";

		Cart finalCart = new Cart(cartRequestTest.getProductId(), notValidUserId, cartRequestTest.getQuantity());

		assertThatThrownBy(() -> cartService.addItemToCart(cartRequestTest, notValidUserId))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("User id not valid");

		verify(cartRepository, never()).save(finalCart);
	}

	@Test
	@DisplayName("quantity not valid")
	void quantityNotValid() {
		CartRequest cartRequestTest = new CartRequest(3, 0);
		String userID = UUID.randomUUID().toString();

		Cart finalCart = new Cart(cartRequestTest.getProductId(), userID, cartRequestTest.getQuantity());

		assertThatThrownBy(() -> cartService.addItemToCart(cartRequestTest, userID))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Quantity not valid");

		verify(cartRepository, never()).save(finalCart);
	}

	@Test
	@DisplayName("product out of stock")
	void outOfStock() {
		CartRequest cartRequestTest = new CartRequest(3, 5);
		String userID = UUID.randomUUID().toString();

		Cart finalCart = new Cart(cartRequestTest.getProductId(), userID, cartRequestTest.getQuantity());

		given(productClient.getStock(cartRequestTest.getProductId())).willReturn(0);
		given(productClient.getProduct(cartRequestTest.getProductId())).willReturn(new Product());

		assertThatThrownBy(() -> cartService.addItemToCart(cartRequestTest, userID))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Product out of stock");

		verify(cartRepository, never()).save(finalCart);
	}

	@Test
	@DisplayName("product id not exist")
	void productIdNotExist() {
		CartRequest cartRequestTest = new CartRequest(3, 1);
		String userID = UUID.randomUUID().toString();

		Cart finalCart = new Cart(cartRequestTest.getProductId(), userID, cartRequestTest.getQuantity());

		given(productClient.getProduct(cartRequestTest.getProductId())).willReturn(null);

		assertThatThrownBy(() -> cartService.addItemToCart(cartRequestTest, userID))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Product id not valid");

		verify(cartRepository, never()).save(finalCart);
	}
}