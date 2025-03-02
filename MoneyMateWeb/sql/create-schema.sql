-- Script to create the database and tables

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS tokens CASCADE;
DROP TABLE IF EXISTS wallet CASCADE;
DROP TABLE IF EXISTS invite CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS user_shared_wallet CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS regular_transactions CASCADE;
DROP SCHEMA IF EXISTS MoneyMate CASCADE;

create schema MoneyMate;

CREATE TABLE MoneyMate.users
(
    user_id            SERIAL PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(100) NOT NULL,

    CONSTRAINT email_is_valid CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE TABLE MoneyMate.tokens (
    token_id          SERIAL PRIMARY KEY,
    token       VARCHAR(300) NOT NULL,
    revoked     BOOLEAN NOT NULL DEFAULT FALSE,
    expired     BOOLEAN NOT NULL DEFAULT FALSE,
    userId     INT NOT NULL REFERENCES MoneyMate.users(user_id)
);

CREATE TABLE MoneyMate.wallet
(
    wallet_id               SERIAL PRIMARY KEY,
    wallet_name             VARCHAR(50) NOT NULL,
    user_id          INT         NOT NULL REFERENCES MoneyMate.users (user_id),
    date_of_creation DATE        NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE MoneyMate.invite
(
    invite_id   SERIAL PRIMARY KEY,
    sender_id   INT NOT NULL REFERENCES MoneyMate.users(user_id),
    receiver_id INT NOT NULL REFERENCES MoneyMate.users(user_id),
    sw_id       INT NOT NULL REFERENCES MoneyMate.wallet (wallet_id),
    state       VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    on_finished BOOLEAN NOT NULL DEFAULT FALSE,
    date_of_creation DATE        NOT NULL DEFAULT CURRENT_DATE
    CONSTRAINT state_values CHECK (state IN ('ACCEPTED', 'PENDING', 'REJECTED'))

);


CREATE TABLE MoneyMate.category
(
    category_id      SERIAL PRIMARY KEY,
    category_name    VARCHAR(250) NOT NULL,
    user_id INT         NOT NULL DEFAULT 0 REFERENCES MoneyMate.users,
    FOREIGN KEY (user_id) REFERENCES MoneyMate.users (user_id)

);

CREATE TABLE MoneyMate.user_shared_wallet
(
    sh_id                   SERIAL PRIMARY KEY,
    wallet_id   INT         NOT NULL REFERENCES MoneyMate.wallet (wallet_id),
    user_id     INT         NOT NULL REFERENCES MoneyMate.users (user_id),
    sh_name VARCHAR(50) NOT NULL
);

CREATE TABLE MoneyMate.transactions
(
    transaction_id                  SERIAL PRIMARY KEY,
    title            VARCHAR(50)    NOT NULL,
    amount           DECIMAL(10, 2) NOT NULL,
    user_id          INT            NOT NULL REFERENCES MoneyMate.users (user_id),
    wallet_id        INT            NOT NULL REFERENCES MoneyMate.wallet (wallet_id),
    category_id      INT            NOT NULL REFERENCES MoneyMate.category (category_id),
    date_of_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    periodical       SMALLINT
);

CREATE TABLE MoneyMate.regular_transactions
(
    regular_transaction_id SERIAL PRIMARY KEY,
    frequency           VARCHAR(50) NOT NULL,
    transaction_info_id INT NOT NULL REFERENCES MoneyMate.transactions (transaction_id)
    CONSTRAINT frequency_values CHECK (frequency IN ('daily', 'weekly', 'monthly', 'yearly'))
);
