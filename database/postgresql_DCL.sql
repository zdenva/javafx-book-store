CREATE USER "bds-customer" WITH PASSWORD 'homer1234';
GRANT CONNECT ON DATABASE "bds-zdenva" TO "bds-customer";
GRANT USAGE ON SCHEMA "book_store" TO "bds-customer";
ALTER USER "bds-customer" WITH LOGIN;
GRANT SELECT ON TABLE book_store.book TO "bds-customer";
GRANT SELECT ON TABLE book_store.author TO "bds-customer";
GRANT SELECT ON TABLE book_store.book_author TO "bds-customer";
GRANT SELECT ON TABLE book_store.publisher TO "bds-customer";
GRANT SELECT, UPDATE ON TABLE book_store.customer TO "bds-customer";
GRANT SELECT ON TABLE book_store.book_language TO "bds-customer";
GRANT SELECT, INSERT ON TABLE book_store.customer TO "bds-customer";
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE book_store.address TO "bds-customer";
GRANT SELECT ON TABLE book_store.address_status TO "bds-customer";
GRANT SELECT, DELETE, INSERT ON TABLE book_store.customer_address TO "bds-customer";
GRANT SELECT, INSERT, UPDATE ON TABLE book_store.customer_phone TO "bds-customer";
GRANT SELECT, INSERT ON TABLE book_store.country TO "bds-customer";