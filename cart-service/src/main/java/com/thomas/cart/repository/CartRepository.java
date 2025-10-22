package com.thomas.cart.repository;

import com.thomas.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserIdAndProductIdAndQuantity(String user_id, int product, int quantity);
    List<Cart> findByUserId(String user_id);
}
