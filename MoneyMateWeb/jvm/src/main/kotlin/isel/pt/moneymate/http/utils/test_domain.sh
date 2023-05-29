#!/bin/bash

# THIS SCRIPT PRODUCE REQUESTS TO TEST ALL THE API DOMAIN INSERTION AND REMOVAL REQUESTS
# First run the create-schema.sql to clear the database

echo "[INIT] ------------------------------------------------------"

source create-schema.sh

echo
echo "[INIT] - Seting up files to use"
JQ_EXEC="C:\Program Files\jq\jq-win64.exe"
OBJECTS="objects.json"

echo "[INIT] - Seting up the Uris"
CATEGORY_ID_CREATED="4"
WALLET_ID_CREATED="1"
TRANSACTION_ID_CREATED="1"

HOST=http://localhost:8080
CREATE_USER=$HOST"/auth/register"
GET_USERS=$HOST"/users"
UPDATE_USER=$HOST"/users"

CREATE_CATEGORY=$HOST"/categories"
GET_CATEGORIES=$HOST"/categories"
GET_CATEGORY_BY_ID=$HOST"/categories/"
UPDATE_CATEGORY=$HOST"/categories/"
DELETE_CATEGORY_BY_ID=$HOST"/categories"

CREATE_WALLET=$HOST"/wallets"
GET_WALLETS_OF_USER=$HOST"/wallets"
GET_WALLET_BY_ID=$HOST"/wallets/"
UPDATE_WALLET=$HOST"/wallets/"
DELETE_WALLET_BY_ID=$HOST"/wallets/"

CREATE_TRANSACTION=$HOST"/transactions/"
GET_TRANSACTION_BY_ID=$HOST"/transactions/"
UPDATE_TRANSACTION=$HOST"/transactions/"
DELETE_TRANSACTION_BY_ID=$HOST"/transactions"


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

echo
echo "[STARTING_REQUESTS]------------------------------------------" #>> $OUT_FILE
echo

echo "[USER][REQUESTS]"

echo
echo "[REQUEST][1] - Create User"
echo "- Making a POST Request To: "$CREATE_USER
echo "$CREATE_USER_OBJ"
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -d "$CREATE_USER_OBJ" $CREATE_USER)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"
TOKEN=$(echo "$RESPONSE" | "$JQ_EXEC" -r '.access_token')
if [ "$TOKEN" == "null" ]; then
  echo "[ERROR] - Token is NULL, Change userDetails and try again"
  echo "[EXITING...]"
  sleep 10
  exit
fi

echo
echo "[REQUEST][2] - Get All Users"
echo "- Making a GET Request To: "$GET_USERS
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_USERS)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][3] - Update User name"
echo "- Making a PUT Request To: "$UPDATE_USER
RESPONSE=$(curl -X PATCH -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$UPDATE_USER_OBJ" $UPDATE_USER)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[CATEGORY][REQUESTS]"

echo
echo "[REQUEST][1] - Create Category"
echo "- Making a POST Request To: "$CREATE_CATEGORY
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$CREATE_CATEGORY_OBJ" $CREATE_CATEGORY)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

# Esta funcao n esta definida no controller
echo
echo "[REQUEST][2] - Get Category By Id"
echo "- Making a GET Request To: "$GET_CATEGORY_BY_ID
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_CATEGORY_BY_ID$CATEGORY_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][3] - Get All Categories"
echo "- Making a GET Request To: "$GET_CATEGORIES
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_CATEGORIES)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][4] - Update Cateogey name"
echo "- Making a PUT Request To: "$UPDATE_CATEGORY
RESPONSE=$(curl -X PATCH -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$UPDATE_CATEGORY_OBJ" $UPDATE_CATEGORY$CATEGORY_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][5] - Get All Categories"
echo "- Making a GET Request To: "$GET_CATEGORIES
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_CATEGORIES)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[WALLET][REQUESTS]"

echo
echo "[REQUEST][1] - Create a Wallet"
echo "- Making a POST Request To: "$CREATE_WALLET
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$CREATE_WALLET_OBJ" $CREATE_WALLET)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

#echo
#echo "[REQUEST][2] - Get Wallet By Id"
#echo "- Making a GET Request To: "$GET_WALLET_BY_ID
#RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_WALLET_BY_ID$WALLET_ID_CREATED)
#echo "- Got Response:"
#echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][3] - Get All Wallets"
echo "- Making a GET Request To: "$GET_WALLETS_OF_USER
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_WALLETS_OF_USER)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][4] - Update Wallet name"
echo "- Making a PUT Request To: "$UPDATE_WALLET
RESPONSE=$(curl -X PATCH -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$UPDATE_WALLET_OBJ" $UPDATE_WALLET$WALLET_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[TRANSACTION][REQUESTS]"

echo
echo "[REQUEST][1] - Create a Transaction_1"
echo "- Making a POST Request To: "$CREATE_TRANSACTION
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$CREATE_TRANSACTION_OBJ_1" $CREATE_TRANSACTION"wallets/$WALLET_ID_CREATED/categories/$CATEGORY_ID_CREATED")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][2] - Get Transaction By Id"
echo "- Making a GET Request To: "$GET_TRANSACTION_BY_ID
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_TRANSACTION_BY_ID$TRANSACTION_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][3] - Update Transaction"
echo "- Making a PUT Request To: "$UPDATE_TRANSACTION
RESPONSE=$(curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$UPDATE_TRANSACTION_OBJ" $UPDATE_TRANSACTION$TRANSACTION_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[DELETING][ENTITIES]"

echo
echo "[REQUEST][1] - Delete Transaction 1"
echo "- Making a DELETE Request To: "$DELETE_TRANSACTION_BY_ID
RESPONSE=$(curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" "$DELETE_TRANSACTION_BY_ID/$TRANSACTION_ID_CREATED")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][2] - Get Transaction 1 By Id"
echo "- Making a GET Request To: "$GET_TRANSACTION_BY_ID
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_TRANSACTION_BY_ID$TRANSACTION_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][3] - Create Transaction_1 Again"
echo "- Making a POST Request To: "$CREATE_TRANSACTION
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$CREATE_TRANSACTION_OBJ_1" $CREATE_TRANSACTION"wallets/$WALLET_ID_CREATED/categories/$CATEGORY_ID_CREATED")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][4] - Create a new Transaction, Transaction_2"
echo "- Making a POST Request To: "$CREATE_TRANSACTION
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$CREATE_TRANSACTION_OBJ_2" $CREATE_TRANSACTION"wallets/$WALLET_ID_CREATED/categories/$CATEGORY_ID_CREATED")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][5] - Delete Category"
echo "- Making a DELETE Request To: "$DELETE_CATEGORY_BY_ID
RESPONSE=$(curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" "$DELETE_CATEGORY_BY_ID/$CATEGORY_ID_CREATED")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][2] - Get Transaction 1 By Id"
echo "- Making a GET Request To: "$GET_TRANSACTION_BY_ID
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_TRANSACTION_BY_ID"2")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][6] - Delete Wallet"
echo "- Making a DELETE Request To: "$DELETE_WALLET_BY_ID
RESPONSE=$(curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $DELETE_WALLET_BY_ID$WALLET_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][2] - Get Transaction 1 By Id"
echo "- Making a GET Request To: "$GET_TRANSACTION_BY_ID
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_TRANSACTION_BY_ID"2")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

# shellcheck disable=SC2162
read
