-- PRODUCTS CREATION
INSERT INTO products(product_description, product_price, product_quantity)
VALUES ('Product Test 1', 100.99, 100);

INSERT INTO products(product_description, product_price, product_quantity)
VALUES ('Product Test 2', 55.99, 10);

-- CUSTOMERS CREATION
INSERT INTO customers(customer_name, customer_address, customer_phone1,
                      customer_phone2, customer_credit_limit, customer_current_credit)
VALUES ('Customer Test 1', '123 Customer Street', '123456789',
        '987456321', 1000, 0);

-- CUSTOMERS CREATION
INSERT INTO customers(customer_name, customer_address, customer_phone1,
                      customer_phone2, customer_credit_limit, customer_current_credit)
VALUES ('Customer Test 2', '123 Customer Street', '123456789',
        '987456321', 5000, 0);


