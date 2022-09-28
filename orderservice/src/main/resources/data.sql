INSERT INTO items (item_id,product_name, quantity) VALUES (1, 'freely', 505 );
INSERT INTO items (item_id,product_name, quantity) VALUES (2, 'cole', 12 );
INSERT INTO items (item_id,product_name, quantity) VALUES (3, 'sample', 304 );
INSERT INTO items (item_id,product_name, quantity) VALUES (4, 'appropriations', 87 );
INSERT INTO items (item_id,product_name, quantity) VALUES (5, 'nebraska', 35 );

INSERT INTO orders (customer_id, referral, pharmacy_npi, shipped_date, item_quantity) VALUES (1, false, 'SPEC10', '2022-10-26',2);
INSERT INTO orders (customer_id, referral, pharmacy_npi, shipped_date, item_quantity) VALUES (1, false, 'SPEC20', '2022-10-26',2);
INSERT INTO orders (customer_id, referral, pharmacy_npi, shipped_date, item_quantity) VALUES (2, true, 'SPEC20', '2022-10-26',2);
INSERT INTO orders (customer_id, referral, pharmacy_npi, shipped_date, item_quantity) VALUES (3, false, 'SPEC20', '2022-10-26',2);
INSERT INTO orders (customer_id, referral, pharmacy_npi, shipped_date, item_quantity) VALUES (3, true, 'SPEC20', '2022-10-26',2);
INSERT INTO orders (customer_id, referral, pharmacy_npi, shipped_date, item_quantity) VALUES (3, false, 'SPEC20', '2022-10-26',2);

INSERT INTO orders_items(order_id, item_id) VALUES (1,1);
INSERT INTO orders_items(order_id, item_id) VALUES (1,3);
INSERT INTO orders_items(order_id, item_id) VALUES (2,2);
INSERT INTO orders_items(order_id, item_id) VALUES (2,5);
INSERT INTO orders_items(order_id, item_id) VALUES (3,2);
INSERT INTO orders_items(order_id, item_id) VALUES (3,3);
INSERT INTO orders_items(order_id, item_id) VALUES (4,4);
INSERT INTO orders_items(order_id, item_id) VALUES (4,1);
INSERT INTO orders_items(order_id, item_id) VALUES (5,1);
INSERT INTO orders_items(order_id, item_id) VALUES (5,5);
INSERT INTO orders_items(order_id, item_id) VALUES (6,3);
INSERT INTO orders_items(order_id, item_id) VALUES (6,5);
