CREATE SCHEMA IF NOT EXISTS book_store;

CREATE TABLE IF NOT EXISTS book_store.book_language(
    "language_id" SERIAL PRIMARY KEY,
    "language_name" VARCHAR(15) NOT NULL,
    "language_code" VARCHAR(8) NOT NULL
);
CREATE TABLE IF NOT EXISTS book_store.author(
    "author_id" SERIAL PRIMARY KEY,
    "author_first_name" VARCHAR(32) NOT NULL,
    "author_last_name" VARCHAR(32) NOT NULL
);
CREATE TABLE IF NOT EXISTS book_store.publisher(
    "publisher_id" SERIAL PRIMARY KEY,
    "publisher_name" VARCHAR(64) NOT NULL
);
CREATE TABLE IF NOT EXISTS book_store.order_status(
    "status_id" SERIAL PRIMARY KEY,
    "status_value" VARCHAR(32) NOT NULL
);
CREATE TABLE IF NOT EXISTS book_store.country(
    "country_id" SERIAL PRIMARY KEY,
    "country_name" VARCHAR(32) NOT NULL
);
CREATE TABLE IF NOT EXISTS book_store.address_status(
    "status_id" SERIAL PRIMARY KEY,
    "address_status" VARCHAR(64) NOT NULL
);
CREATE TABLE IF NOT EXISTS book_store.shipping_method(
    "method_id" SERIAL PRIMARY KEY,
    "method_name" VARCHAR(32) NOT NULL,
    "cost" FLOAT(2) NOT NULL
);
CREATE TABLE IF NOT EXISTS book_store.book(
    "book_id" SERIAL PRIMARY KEY,
    "title" VARCHAR(128) NOT NULL,
    "genre" VARCHAR(32) NOT NULL,
    "isbn" VARCHAR(17) NOT NULL,
    "num_pages" SMALLINT NOT NULL,
    "language_id" INT NOT NULL,
    "publication_date" DATE NOT NULL,
    "publisher_id" INT NOT NULL,
        FOREIGN KEY (publisher_id)
        REFERENCES book_store.publisher(publisher_id),
        FOREIGN KEY (language_id)
        REFERENCES book_store.book_language(language_id)
);
CREATE TABLE IF NOT EXISTS book_store.book_author(
    "book_id" INT NOT NULL,
    "author_id" INT NOT NULL,
        FOREIGN KEY (book_id)
        REFERENCES  book_store.book(book_id),
        FOREIGN KEY (author_id)
        REFERENCES  book_store.author(author_id)
);
CREATE TABLE IF NOT EXISTS book_store.customer_phone(
    "phone_id" SERIAL PRIMARY KEY,
    "phone_number" VARCHAR(12) NOT NULL,
    "country_id" INT,
        FOREIGN KEY (country_id)
        REFERENCES book_store.country(country_id)
);
CREATE TABLE IF NOT EXISTS book_store.customer(
    "customer_id" SERIAL PRIMARY KEY,
    "first_name" VARCHAR(32) NOT NULL,
    "last_name" VARCHAR(32) NOT NULL,
    "email" VARCHAR(32) NOT NULL,
    "password_hash" VARCHAR(255) NOT NULL,
    "password_salt" VARCHAR(64) NOT NULL,
    "phone_id" INT,
        FOREIGN KEY (phone_id)
        REFERENCES book_store.customer_phone(phone_id)
);
CREATE TABLE IF NOT EXISTS book_store.address(
    "address_id" SERIAL PRIMARY KEY,
    "street_number" SMALLINT NOT NULL,
    "street_name" VARCHAR(32) NOT NULL,
    "city" VARCHAR(32) NOT NULL,
    "country_id" INT NOT NULL,
        FOREIGN KEY (country_id)
        REFERENCES book_store.country(country_id)
);
CREATE TABLE IF NOT EXISTS book_store.customer_order(
    "order_id" SERIAL PRIMARY KEY,
    "ordered" TIMESTAMP NOT NULL,
    "is_ready_for_shipping" BOOLEAN NOT NULL,
    "customer_id" INT NOT NULL,
    "shipping_method_id" INT NOT NULL,
    "destination_address_id" INT NOT NULL,
        FOREIGN KEY (customer_id)
        REFERENCES book_store.customer(customer_id),
        FOREIGN KEY (shipping_method_id)
        REFERENCES book_store.shipping_method(method_id),
        FOREIGN KEY (destination_address_id)
        REFERENCES book_store.address(address_id)
);
CREATE TABLE IF NOT EXISTS book_store.order_line(
    "line_id" SERIAL PRIMARY KEY,
    "order_id" INT NOT NULL,
    "book_id" INT NOT NULL,
    "cost" FLOAT(2) NOT NULL,
        FOREIGN KEY (order_id)
        REFERENCES book_store.customer_order(order_id),
        FOREIGN KEY (book_id)
        REFERENCES book_store.book(book_id)
);
CREATE TABLE IF NOT EXISTS book_store.order_history(
    "history_id" SERIAL PRIMARY KEY,
    "order_id" INT NOT NULL,
    "status_id" INT NOT NULL,
    "status_date" DATE NOT NULL,
        FOREIGN KEY (order_id)
        REFERENCES book_store.customer_order(order_id),
        FOREIGN KEY (status_id)
        REFERENCES book_store.order_status(status_id)
);
CREATE TABLE IF NOT EXISTS book_store.customer_address(
    "customer_id" INT NOT NULL,
    "address_id" INT NOT NULL,
    "status_id" INT NOT NULL,
        FOREIGN KEY (customer_id)
        REFERENCES book_store.customer(customer_id),
        FOREIGN KEY (address_id)
        REFERENCES book_store.address(address_id),
        FOREIGN KEY (status_id)
        REFERENCES book_store.address_status(status_id)
);