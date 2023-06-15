#!/bin/bash

# THIS SCRIPT PRODUCE REQUESTS TO TEST ALL THE API DOMAIN INSERTION AND REMOVAL REQUESTS
# First run the create-schema.sql to clear the database

echo "[INIT] ------------------------------------------------------"

echo
echo "[INIT] - Seting up files to use"
export JQ_EXEC="C:\Program Files\jq\jq-win64.exe"
export OBJECTS="objects.json"

echo "[INIT] - Seting up the Uris"
export CATEGORY_ID_CREATED="4"
export WALLET_ID_CREATED="1"
export TRANSACTION_ID_CREATED="1"

export HOST=http://localhost:8080
export CREATE_USER=$HOST"/auth/register"
export LOGIN_USER=$HOST"/auth/login"
export GET_USERS=$HOST"/users"
export UPDATE_USER=$HOST"/users"

export CREATE_CATEGORY=$HOST"/categories"
export GET_CATEGORIES=$HOST"/categories"
export GET_CATEGORY_BY_ID=$HOST"/categories/"
export UPDATE_CATEGORY=$HOST"/categories/"
export DELETE_CATEGORY_BY_ID=$HOST"/categories"

export CREATE_WALLET=$HOST"/wallets"
export GET_WALLETS_OF_USER=$HOST"/wallets"
export GET_WALLET_BY_ID=$HOST"/wallets/"
export UPDATE_WALLET=$HOST"/wallets/"
export DELETE_WALLET_BY_ID=$HOST"/wallets/"

export CREATE_TRANSACTION=$HOST"/transactions"
export GET_TRANSACTION_BY_ID=$HOST"/transactions"
export UPDATE_TRANSACTION=$HOST"/transactions/"
export DELETE_TRANSACTION_BY_ID=$HOST"/transactions"


echo "[INIT] - Seting up json Objects"
CREATE_USER_OBJ=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_user')
UPDATE_USER_OBJ=$(cat "$OBJECTS" | "$JQ_EXEC" '.update_user')
CREATE_CATEGORY_OBJ=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_category')
UPDATE_CATEGORY_OBJ=$(cat "$OBJECTS" | "$JQ_EXEC" '.update_category')
CREATE_WALLET_OBJ=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_wallet')
UPDATE_WALLET_OBJ=$(cat "$OBJECTS" | "$JQ_EXEC" '.update_wallet')
CREATE_TRANSACTION_OBJ_1=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_transaction_1')
CREATE_TRANSACTION_OBJ_2=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_transaction_2')
UPDATE_TRANSACTION_OBJ=$(cat "$OBJECTS" | "$JQ_EXEC" '.update_transaction')

