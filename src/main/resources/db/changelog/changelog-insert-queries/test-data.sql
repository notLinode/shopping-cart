--liquibase formatted sql

--changeset iskenderov-andrei:insert-test-data-into-customer
INSERT INTO customer (id, first_name, surname, address, email, phone)
VALUES (1, 'Emily', 'Chen', '123 Main St, Anytown, USA 12345', 'emily.chen@example.com', '1 555 123-4567'),
       (2, 'Liam', 'Brown', '456 Elm St, Othertown, USA 67890', 'liam.brown@example.com', '1 555 901-2345'),
       (3, 'Ava', 'Lee', '789 Oak St, Anytown, USA 12345', 'ava.lee@example.com', '1 555 567-8901'),
       (4, 'Ethan', 'Hall', '321 Maple St, Othertown, USA 67890', 'ethan.hall@example.com', '1 555 234-5678'),
       (5, 'Sophia', 'Patel', '901 Pine St, Anytown, USA 12345', 'sophia.patel@example.com', '1 555 890-1234');

--changeset iskenderov-andrei:insert-test-data-into-product
INSERT INTO product (id, name, price, description, in_stock)
VALUES (1, 'Apple Watch', 299.99, 'A sleek smartwatch from Apple.', TRUE),
       (2, 'Samsung TV', 1299.99, 'A 4K UHD smart TV from Samsung.', TRUE),
       (3, 'Nike Running Shoes', 79.99, 'High-quality running shoes from Nike.', FALSE),
       (4, 'Sony Headphones', 99.99, 'Noise-cancelling headphones from Sony.', TRUE),
       (5, 'Canon Camera', 499.99, 'A high-end DSLR camera from Canon.', TRUE);

--changeset iskenderov-andrei:insert-test-data-into-purchase
INSERT INTO purchase (id, purchase_status, payment_method, customer_id)
VALUES (1, 4, 2, 1),
       (2, 2, 2, 1),
       (3, 4, 3, 2),
       (4, 3, 1, 3),
       (5, 1, 3, 4);

--changeset iskenderov-andrei:insert-test-data-into-purchase-detail
INSERT INTO purchase_detail (id, order_id, product_id, price, quantity)
VALUES (1, 1, 1, 299.99, 1),
       (2, 1, 4, 99.99, 1),
       (3, 2, 3, 799900, 10000),
       (4, 3, 1, 299.99, 1),
       (5, 3, 2, 1299.99, 1),
       (6, 4, 1, 299.99, 1),
       (7, 4, 1, 299.99, 1),
       (8, 5, 5, 499.99, 1);
