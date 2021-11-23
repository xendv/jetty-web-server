CREATE TABLE products
(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL,
    manufacturer_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL
);

INSERT INTO products (id, name, manufacturer_id, quantity)
VALUES (111, 'fork', 111111, 100), (222, 'spoon', 222222, 200),
       (333, 'plate', 333333, 300), (444, 'table', 444444, 400);