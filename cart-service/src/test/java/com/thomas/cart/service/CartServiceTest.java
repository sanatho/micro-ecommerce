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

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    // delete element test
    @Test
    @DisplayName("delete existing cart item")
    void deleteElement() {
        Integer cartId = 3;
        String userId = "user-123";
        Integer productId = 10;

        // cart presente nel DB
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(5);

        // mock del repository
        given(cartRepository.findById(cartId)).willReturn(Optional.of(cart));

        // eseguo il metodo
        cartService.removeItem(cartId, userId);

        // verifiche
        verify(cartRepository).deleteById(cartId);
    }

    @Test
    @DisplayName("delete cart with not valid userid")
    void deleteElementNotValidID(){
        Integer cartId = 3;
        String userId = "user-123";
        String userId2 = "user-124";
        Integer productId = 10;

        // cart presente nel DB
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId2);
        cart.setProductId(productId);
        cart.setQuantity(5);

        // mock del repository
        given(cartRepository.findById(cartId)).willReturn(Optional.of(cart));

        assertThatThrownBy(() -> cartService.removeItem(cartId, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cart id not associate to you");
    }

    // patch element test
    // not existing element or valid quantity or valid id
    @Test
    @DisplayName("edit cart exist")
    void editElement(){
        Integer cartId = 3;
        String userId = "user-123";
        int productId = 10;
        int productQuantity = 20;
        int newProductQuantity = 10;
        int stock = 50;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(productQuantity);

        CartRequest cartRequestTest = new CartRequest(productId, newProductQuantity);
        given(cartRepository.findByUserIdAndProductIdAndQuantity(userId, productId, newProductQuantity)).willReturn(List.of(cart));
        given(productClient.getStock(productId)).willReturn(stock);
        cartService.editCart(cartRequestTest, userId);

        verify(cartRepository).save(cart);
        assertThat(cart.getQuantity()).isEqualTo(newProductQuantity);
    }

    @Test
    @DisplayName("edit cart with no valid quantity")
    void editElementNoValidQuantity(){
        Integer cartId = 3;
        String userId = "user-123";
        int productId = 10;
        int productQuantity = 20;
        int newProductQuantity = 10;
        int stock = 5;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(productQuantity);

        CartRequest cartRequestTest = new CartRequest(productId, newProductQuantity);
        given(cartRepository.findByUserIdAndProductIdAndQuantity(userId, productId, newProductQuantity)).willReturn(List.of(cart));
        given(productClient.getStock(productId)).willReturn(stock);

        assertThatThrownBy(() -> cartService.editCart(cartRequestTest, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product out of stock");
    }

    @Test
    @DisplayName("edit cart that not exist")
    void editElementThatNotExist(){
        Integer cartId = 3;
        String userId = "user-123";
        int productId = 10;
        int productQuantity = 20;
        int newProductQuantity = 10;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(productQuantity);

        CartRequest cartRequestTest = new CartRequest(productId, newProductQuantity);
        given(cartRepository.findByUserIdAndProductIdAndQuantity(userId, productId, newProductQuantity)).willReturn(Collections.emptyList());

        assertThatThrownBy(() -> cartService.editCart(cartRequestTest, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cart not found");
    }

    @Test
    @DisplayName("One more than cart found")
    void oneMoreThanOneCartFound(){
        Integer cartId = 3;
        String userId = "user-123";
        int productId = 10;
        int productQuantity = 20;
        int newProductQuantity = 10;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(productQuantity);

        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        cartList.add(cart);

        CartRequest cartRequestTest = new CartRequest(productId, newProductQuantity);
        given(cartRepository.findByUserIdAndProductIdAndQuantity(userId, productId, newProductQuantity)).willReturn(cartList);

        assertThatThrownBy(() -> cartService.editCart(cartRequestTest, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("More than one cart found");
    }

    // get element test
    @Test
    @DisplayName("get cart")
    void getCart(){
        Integer cartId = 3;
        Integer productId = 3;
        String userId = "user-123";
        int productQuantity = 20;
        Cart cart = new Cart(cartId, productId, userId, productQuantity);

        given(cartRepository.findByUserId(userId)).willReturn(List.of(cart));

        assertThat(cartService.getCart(userId)).isEqualTo(List.of(cart));
    }
}