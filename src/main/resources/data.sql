-- PRODUCTS CREATION
INSERT INTO products(product_description, product_price, product_available_quantity)
VALUES ('Product Test 1', 100.99, 100);

INSERT INTO products(product_description, product_price, product_available_quantity)
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

-- SALESORDER CREATION
INSERT INTO sales_orders(sales_order_number, sales_order_total_price, sales_order_customer_id)
VALUES (100, 1000, (SELECT customer_id FROM customers WHERE customer_name = 'Customer Test 1'));

INSERT INTO sales_orders(sales_order_number, sales_order_total_price, sales_order_customer_id)
VALUES (101, 5000, (SELECT customer_id FROM customers WHERE customer_name = 'Customer Test 1'));

INSERT INTO sales_orders_products(sales_order_product_quantity, product_id)
VALUES (10, (SELECT product_id FROM products WHERE product_description = 'Product Test 1'));

INSERT INTO sales_orders_products(sales_order_product_quantity, product_id)
VALUES (5, (SELECT product_id FROM products WHERE product_description = 'Product Test 2'));

INSERT INTO order_products(order_id, order_product_id)
VALUES ((SELECT sales_order_id FROM sales_orders WHERE sales_order_number = 100),
        (SELECT sales_order_product_id FROM sales_orders_products WHERE sales_order_product_quantity = 10));

INSERT INTO order_products(order_id, order_product_id)
VALUES ((SELECT sales_order_id FROM sales_orders WHERE sales_order_number = 101),
        (SELECT sales_order_product_id FROM sales_orders_products WHERE sales_order_product_quantity = 5));
