package com.thoms.products.repository;

import com.thoms.products.entity.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("testing")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void initialize(){
        Product product1 = 
                new Product(1, "Nitro 5", 1000, 10, "Elettronica e Informatica", "Acer");
        Product product2 = 
                new Product(2, "Blade 15", 3000, 1, "Elettronica e Informatica", "Razer");
        Product product3 = 
                new Product(3, "MacBook Pro 14", 1300, 5, "Elettronica e Informatica", "Apple");
        Product product4 =
                new Product(4, "GF63", 800, 0, "Elettronica e Informatica", "MSI");
        Product product5 =
                new Product(5, "Legion 5", 1700, 10, "Elettronica e Informatica", "Lenovo");
        Product product6 =
                new Product(6, "1000 leghe sotto il mare", 17, 10, "Libri", "Mondadori");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
    }

    @AfterEach
    void afterEach(){
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Category that exist")
    void findProductByCategoryNameWhenExist() {

        String categorySearched = "Libri";

        List<Product> productByCategoryName = productRepository.findProductByCategoryName(categorySearched);

        assertFalse(productByCategoryName.isEmpty());

        categorySearched = "Elettronica e Informatica";

        productByCategoryName = productRepository.findProductByCategoryName(categorySearched);

        assertFalse(productByCategoryName.isEmpty());

    }

    @Test
    @DisplayName("Category that not exist")
    void findProductByCategoryNameWhenNotExist() {

        String categorySearched = "Category that not exist";

        List<Product> productByCategoryName = productRepository.findProductByCategoryName(categorySearched);

        assertTrue(productByCategoryName.isEmpty());

    }

    @Test
    @DisplayName("Brand that not exist")
    void findProductByBrandNameWhenNotExist() {

        String brandSearched = "Brand that not exist";

        List<Product> productByBrandName = productRepository.findProductByBrandName(brandSearched);

        assertTrue(productByBrandName.isEmpty());

        brandSearched = "AppleIndia";

        productByBrandName = productRepository.findProductByBrandName(brandSearched);

        assertTrue(productByBrandName.isEmpty());

    }

    @Test
    @DisplayName("Brand that exist")
    void findProductByBrandNameWhenExist() {

        String brandSearched = "MSI";

        List<Product> productByBrandName = productRepository.findProductByBrandName(brandSearched);

        assertFalse(productByBrandName.isEmpty());

        brandSearched = "Apple";

        productByBrandName = productRepository.findProductByBrandName(brandSearched);

        assertFalse(productByBrandName.isEmpty());

    }
}