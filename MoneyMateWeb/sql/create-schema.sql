-- Script to create the database and tables

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS wallet CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS user_shared_wallet CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP SCHEMA IF EXISTS MoneyMate CASCADE;

create schema MoneyMate;

CREATE TABLE MoneyMate.users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,

    CONSTRAINT email_is_valid CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);


CREATE TABLE MoneyMate.wallet
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(50) NOT NULL,
    user_id          INT         NOT NULL REFERENCES MoneyMate.users (id),
    date_of_creation DATE        NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE MoneyMate.category
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(250) NOT NULL,
    user_id INT         NOT NULL DEFAULT 0 REFERENCES MoneyMate.users,
    FOREIGN KEY (user_id) REFERENCES MoneyMate.users (id)

);

CREATE TABLE MoneyMate.user_shared_wallet
(
    id          SERIAL PRIMARY KEY,
    wallet_id   INT         NOT NULL REFERENCES MoneyMate.wallet (id),
    user_id     INT         NOT NULL REFERENCES MoneyMate.users (id),
    wallet_name VARCHAR(50) NOT NULL
);

CREATE TABLE MoneyMate.transactions
(
    id               SERIAL PRIMARY KEY,
    user_id          INT            NOT NULL REFERENCES MoneyMate.users (id),
    wallet_id        INT            NOT NULL REFERENCES MoneyMate.wallet (id),
    category_id      INT            NOT NULL REFERENCES MoneyMate.category (id),
    amount           DECIMAL(10, 2) NOT NULL,
    date_of_creation DATE           NOT NULL DEFAULT CURRENT_DATE,
    title            VARCHAR(50)    NOT NULL,
    transaction_type VARCHAR(15)    NOT NULL,
    periodical       SMALLINT,

    CONSTRAINT transaction_is_valid CHECK (transaction_type IN ('income', 'expense')),
    CONSTRAINT period_is_valid CHECK (periodical IN (1, 3, 6, 9, 12))

);