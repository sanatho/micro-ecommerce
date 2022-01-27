DELETE FROM product;

CREATE TABLE IF NOT EXISTS Brand(
    brand_name VARCHAR PRIMARY KEY
);

ALTER TABLE Product
    RENAME name TO model;
ALTER TABLE Product
    ADD brand_name VARCHAR;
ALTER TABLE Product
    ADD FOREIGN KEY (brand_name) REFERENCES Brand(brand_name);

INSERT INTO Brand(brand_name) VALUES
    ('Acer'),
    ('Razer'),
    ('Apple'),
    ('MSI'),
    ('Lenovo');

INSERT INTO Product (brand_name, model, price, stock, category_name) VALUES
    ('Acer', 'Nitro 5', 1000, 10, 'Elettronica e Informatica'),
    ('Razer', 'Blade 15', 3000, 1, 'Elettronica e Informatica'),
    ('Apple', 'MacBook Pro 14', 1300, 5, 'Elettronica e Informatica'),
    ('MSI', 'GF63', 800, 0, 'Elettronica e Informatica'),
    ('Lenovo', 'Legion 5', 1700, 10, 'Elettronica e Informatica');