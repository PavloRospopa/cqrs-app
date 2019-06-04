CREATE SEQUENCE products_id_seq START WITH 1;
CREATE TABLE products
(
    id                 BIGINT PRIMARY KEY     DEFAULT NEXTVAL('products_id_seq'),
    name               VARCHAR(256)  NOT NULL,
    description        VARCHAR(1024) NOT NULL,
    available_quantity INT           NOT NULL DEFAULT 0,
    price              FLOAT         NOT NULL DEFAULT 0
);

CREATE SEQUENCE orders_id_seq START WITH 1;
CREATE TABLE orders
(
    id         BIGINT PRIMARY KEY   DEFAULT NEXTVAL('orders_id_seq'),
    uuid       VARCHAR(36) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status     VARCHAR(32) NOT NULL
);
CREATE UNIQUE INDEX uidx_orders ON orders (uuid);

CREATE TABLE order_items
(
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES products (id)
);
