package com.thoms.products.service;

import com.thoms.products.entity.Product;
import com.thoms.products.repository.BrandRepository;
import com.thoms.products.repository.CategoryRepository;
import com.thoms.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("testing")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductService productService;

    Product testProduct =
            new Product(3, "MacBook Air", 800, 50, "Computer", "Apple");

    Product originalProduct =
            new Product(3, "MacBook Air", 800, 50, "Computer", "Apple");

    Product editedProduct =
            new Product(3, "MacBook Pro 13", 800, 50, "Computer", "Apple");


    @BeforeEach
    void setUp() {
        productService =
                new ProductService(productRepository, brandRepository, categoryRepository);
    }

    @Test
    @DisplayName("Get all product")
    void getAllProducts() {
        productService.getAllProducts();

        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Get product that not exist")
    void getProductNotExist() {

        Integer id = 3;

        assertThatThrownBy(() -> productService.getProduct(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product with id " + id + "not exist");

        verify(productRepository, never()).getById(id);
    }

    @Test
    @DisplayName("Get product that exist")
    void getProductExist() {

        Integer id = 3;

        given(productRepository.existsById(id)).willReturn(true);

        productService.getProduct(id);

        verify(productRepository).getById(id);
    }

    @Test
    @DisplayName("Save new product")
    void saveProduct() {

        productService.saveProduct(testProduct);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(argumentCaptor.capture());

        Product productCaptured = argumentCaptor.getValue();

        assertThat(productCaptured).isEqualTo(testProduct);

    }

    @Test
    @DisplayName("Delete product that exist")
    void deleteProductThatExist() {

        Integer id = 3;

        given(productRepository.existsById(id)).willReturn(true);

        productService.deleteProduct(id);

        verify(productRepository).deleteById(id);

    }

    @Test
    @DisplayName("Delete product that not exist")
    void deleteProductThatNotExist() {

        Integer id = 3;

        given(productRepository.existsById(id)).willReturn(false);


        assertThatThrownBy(() -> productService.deleteProduct(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product with id " + id + " not exist");

    }

    @Test
    @DisplayName("Edit product that exist")
    void editProductThatExist() {

        Integer id = 3;

        given(productRepository.findById(3)).willReturn(Optional.of(originalProduct));

        productService.editProduct(editedProduct, id);

        ArgumentCaptor<Product> editedObject = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(editedObject.capture());

        Product savedProductAfterEdit = editedObject.getValue();

        assertThat(editedProduct).isEqualTo(savedProductAfterEdit);
    }

    @Test
    @DisplayName("Edit product that not exist")
    void editProductThatNotExist() {

        Integer id = 3;

        given(productRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.editProduct(editedProduct, id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product with id " + id + " not exist");

    }

    @Test
    @DisplayName("Search with category that exist")
    void findByCategoryThatExist() {

        String category = "Libri";

        given(categoryRepository.existsById(category)).willReturn(true);

        productService.findByCategory(category);

        verify(productRepository).findProductByCategoryName(category);

    }

    @Test
    @DisplayName("Search with category that not exist")
    void findByCategoryThatNotExist() {

        String category = "Libri";

        given(categoryRepository.existsById(category)).willReturn(false);

        assertThatThrownBy(() -> productService.findByCategory(category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("This category not exist!");
    }

    @Test
    @DisplayName("Search with brand that exist")
    void findByBrandThatExist() {

        String brand = "Acer";

        given(brandRepository.existsById(brand)).willReturn(true);

        productService.findByBrand(brand);

        verify(productRepository).findProductByBrandName(brand);

    }

    @Test
    @DisplayName("Search with brand that not exist")
    void findByBrandThatNotExist() {
        String brand = "Acer";

        given(brandRepository.existsById(brand)).willReturn(false);

        assertThatThrownBy(() -> productService.findByBrand(brand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("This brand not exist!");
    }

    @Test
    @DisplayName("Get stock of product that exist")
    void getStockForProductThatExist() {

        Integer id = 3;

        given(productRepository.existsById(id)).willReturn(true);
        given(productRepository.findById(id)).willReturn(Optional.of(testProduct));

        productService.getStock(id);

        verify(productRepository).findById(id);

    }

    @Test
    @DisplayName("Get stock of product that not exist")
    void getStockForProductThatNotExist() {

        Integer id = 3;

        given(productRepository.existsById(id)).willReturn(false);

        assertThatThrownBy(() -> productService.getStock(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product with id " + id + " not exist");

    }
}