package com.thoms.products.service;

import com.thoms.products.entity.Product;
import com.thoms.products.repository.BrandRepository;
import com.thoms.products.repository.CategoryRepository;
import com.thoms.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer productId) {

        boolean existsById = productRepository.existsById(productId);

        if(existsById){
            log.info("Product with id {} exist", productId);

            Product productById = productRepository.getById(productId);

            return productById;
        }else{
            log.info("Product with id {} not exist", productId);
            throw new IllegalArgumentException("Product with id " + productId + "not exist");
        }
    }

    public Product saveProduct(Product product) {
        //Controllare immissione
        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {

        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return;
        }else{
            throw new IllegalArgumentException("Product with id " + productId + " not exist");
        }

    }


    public Product editProduct(Product product, Integer productId) {

        Optional<Product> originalProduct = productRepository.findById(productId);

        if(originalProduct.isPresent()){
            Product newProduct = Product.builder()
                    .product_id(productId)
                    .model(product.getModel())
                    .category_name(product.getCategory_name())
                    .brand_name(product.getBrand_name())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .build();

            productRepository.save(newProduct);
            log.info("Product {} updated with new info {}", productId, newProduct);
        }else{
            log.warn("Product with id {} not exist", productId);
            throw new IllegalArgumentException("Product with id " + productId + " not exist");
        }

        return null;
    }

    public List<Product> findByCategory(String category) {

        if(categoryRepository.existsById(category)){
            log.info("Categoria trovata {}", category);
            return productRepository.findProductByCategoryName(category);
        }else{
            throw new IllegalArgumentException("This category not exist!");
        }

    }

    public List<Product> findByBrand(String brand) {

        if(brandRepository.existsById(brand)){
            log.info("Brand trovata {}", brand);
            return productRepository.findProductByBrandName(brand);
        }else{
            throw new IllegalArgumentException("This brand not exist!");
        }

    }

    public Integer getStock(Integer productId) {

        if(productRepository.existsById(productId)){
            return productRepository.findById(productId).get().getStock();
        }else{
            log.info("Product with id {} not exist", productId);
            throw new IllegalArgumentException("Product with id " + productId + " not exist");
        }
    }
}
