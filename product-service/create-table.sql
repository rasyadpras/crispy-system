\c ad_product;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    product VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    stock INT NOT NULL
)