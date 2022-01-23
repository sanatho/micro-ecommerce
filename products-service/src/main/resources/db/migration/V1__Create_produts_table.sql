-- TODO fare versione 2 con tabella per la marca in modo che possa filtrare per marca

CREATE TABLE IF NOT EXISTS Category(
    category_name VARCHAR PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS Product(
    product_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    stock INTEGER NOT NULL,
    category_name VARCHAR NOT NULL,
    FOREIGN KEY (category_name) REFERENCES Category(category_name)
);

INSERT INTO Category(category_name) VALUES
    ('Libri'),
    ('Musica, Film e TV'),
    ('Videgiochi e Console'),
    ('Elettronica e Informatica'),
    ('Casa, Giardino, Fai da te e Animali'),
    ('Alimentari e Cura della casa'),
    ('Bellezza e Salute'),
    ('Giochi e Prima infanzia'),
    ('Vestiti, Scarpe, Gioielli e Accessori'),
    ('Sport e Tempo libero'),
    ('Auto e Moto'),
    ('Commercio, Industria e Scienza');

INSERT INTO Product (name, price, stock, category_name) VALUES
    ('Acer Nitro 5', 1000, 10, 'Elettronica e Informatica'),
    ('Razer Blade 15', 3000, 1, 'Elettronica e Informatica'),
    ('MacBook Pro 14', 1300, 5, 'Elettronica e Informatica'),
    ('MSI GF63', 800, 0, 'Elettronica e Informatica'),
    ('Lenovo Legion 5', 1700, 10, 'Elettronica e Informatica');