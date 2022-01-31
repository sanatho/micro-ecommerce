package com.thoms.products.controller;

import com.thoms.products.entity.Product;
import com.thoms.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("testing")
@AutoConfigureMockMvc
class ProductControllerTest {

    // TODO con database per testing

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
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

    @Test
    void getAllProducts() throws Exception {

        ResultActions resultActions =
                mockMvc.perform(get("/api/v1/product/"));

        resultActions.andExpect(status().isUnauthorized());

    }

    @Test
    void getProduct() {
    }

    @Test
    void saveProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void editProduct() {
    }

    @Test
    void findByCategory() {
    }

    @Test
    void findByBrand() {
    }

    @Test
    void getStock() {
    }
}