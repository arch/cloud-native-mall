CREATE TABLE orders
(
    id               BIGSERIAL PRIMARY KEY NOT NULL,
    book_isbn        varchar(255)          NOT NULL,
    book_name        varchar(255),
    book_price       float8,
    quantity         int                   NOT NULL,
    status           varchar(255)          NOT NULL,
    created_at       timestamp             NOT NULL,
    last_modified_at timestamp             NOT NULL,
    version          integer               NOT NULL
);