-- Script to create the database and tables

DROP TABLE IF EXISTS users CASCADE;
DROP SCHEMA IF EXISTS MoneyMate CASCADE;

create schema MoneyMate;

CREATE TABLE MoneyMate.users (
   id SERIAL PRIMARY KEY,
   username VARCHAR(50) NOT NULL UNIQUE,
   email VARCHAR(100) NOT NULL UNIQUE,
   passwordHash VARCHAR(100) NOT NULL,

   CONSTRAINT email_is_valid CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);