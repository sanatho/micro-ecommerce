package com.thoms.products.service;

import com.thoms.products.entity.Brand;
import com.thoms.products.entity.Category;
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
        return productRepository.findById(productId).get();
    }

    public Product saveProduct(Product product) {
        //Controllare immissione
        return productRepository.save(product);
    }

    public Product deleteProduct(Integer productId) {

        Optional<Product> deleteProduct = productRepository.findById(productId);

        if(deleteProduct.isPresent()){
            productRepository.delete(deleteProduct.get());
            return deleteProduct.get();
        }

        return null;

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
        }

        return null;
    }

    public List<Product> findByCategory(String category) {

        Optional<Category> categorySearched = categoryRepository.findById(category);

        if(categorySearched.isPresent()){
            log.info("Categoria trovata {}", categorySearched.get());
            return productRepository.findProductByCategoryName(category);
        }else{
            throw new IllegalArgumentException("This category not exist!");
        }

    }

    public List<Product> findByBrand(String brand) {

        Optional<Brand> brandSearched = brandRepository.findById(brand);

        if(brandSearched.isPresent()){
            log.info("Brand trovata {}", brandSearched.get());
            return productRepository.findProductByBrandName(brand);
        }else{
            throw new IllegalArgumentException("This brand not exist!");
        }

    }

    public Integer getStock(Integer productId) {

        Optional<Product> productSearched = productRepository.findById(productId);

        if(productSearched.isPresent()){
            return productSearched.get().getStock();
        }else{
            log.info("Product with id {} not exist", productId);
        }

        return -1;

    }
}
