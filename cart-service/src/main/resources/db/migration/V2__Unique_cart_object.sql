ALTER TABLE cart
ADD CONSTRAINT unique_user_product UNIQUE (user_id, product_id);