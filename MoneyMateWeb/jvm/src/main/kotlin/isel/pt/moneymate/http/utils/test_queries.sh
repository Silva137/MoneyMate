#!/bin/bash

# THIS SCRIPT PRODUCE REQUESTS TO TEST ALL THE API LOGICAL REQUESTS
# First run the insertDataScript.sql to introduce data in the database

echo "[INIT] ------------------------------------------------------"

echo
echo "[INIT] - Seting up files to use"
JQ_EXEC="C:\Program Files\jq\jq-win64.exe"

echo "[INIT] - Seting up the Uris"
HOST=http://localhost:8080

# Test for 3 users (3 diferent tokens)
TOKEN="A"

GET_WALLET_BALANCE=$HOST"/transactions/wallets/{walletId}/balance"

GET_ALL_TRANSACTIONS=$HOST"/transactions/wallets/{walletId}"
GET_INCOMES_TRANSACTIONS=$HOST"/transactions/wallets/{walletId}/incomes"
GET_EXPENSES_TRANSACTIONS=$HOST"/transactions/wallets/{walletId}/expenses"

GET_TRANSACTIONS_BY_CATEGORY=$HOST"/transactions/wallets/{walletId}/categories/{categoryId}"
GET_TRANSACTIONS_BALANCE_BY_CATEGORY=$HOST"/transactions/wallets/{walletId}/categories/balance"

GET_TRANSACTIONS_ALL_BY_CATEGORY=$HOST"/transactions/categories/{categoryId}"
GET_TRANSACTIONS_ALL_BALANCE_BY_CATEGORY=$HOST"/transactions/categoryAmounts"

GET_TRANSACTIONS_BY_USER=$HOST"/transactions/wallets/{shId}/users/{userId}"
GET_TRANSACTIONS_BALANCE_BY_USER=$HOST"/transactions/wallets/{shId}/users/userAmounts"

GET_PERIODICAL_TRANSACTIONS=$HOST"/transactions/periodical"
UPDATE_TRANSACTION_FREQUENCY=$HOST"/transactions/{transactionId}/frquency"
UPDATE_TRANSACTION_AMOUNT=$HOST"/transactions/{transactionId}/amount"

echo
echo "[STARTING_REQUESTS]------------------------------------------"
echo


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
echo "[REQUEST][4] - Create Category"
echo "- Making a POST Request To: "$CREATE_CATEGORY
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$CREATE_CATEGORY_OBJ" $CREATE_CATEGORY)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][5] - Get Category By Id"
echo "- Making a GET Request To: "$GET_CATEGORY_BY_ID
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_CATEGORY_BY_ID$CATEGORY_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][6] - Get All Categories"
echo "- Making a GET Request To: "$GET_CATEGORIES
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_CATEGORIES)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][7] - Update Cateogey name"
echo "- Making a PUT Request To: "$UPDATE_CATEGORY
RESPONSE=$(curl -X PATCH -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$UPDATE_CATEGORY_OBJ" $UPDATE_CATEGORY$CATEGORY_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

#echo
#echo "[REQUEST][8] - Delete Category"
#echo "- Making a DELETE Request To: "$DELETE_CATEGORY_BY_ID
#RESPONSE=$(curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $DELETE_CATEGORY_BY_ID$CATEGORY_ID_CREATED)
#echo "- Got Response:"
#echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][9] - Get All Categories"
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

echo
echo "[REQUEST][2] - Get Wallet By Id"
echo "- Making a GET Request To: "$GET_WALLET_BY_ID
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_WALLET_BY_ID$WALLET_ID_CREATED)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

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

#echo
#echo "[REQUEST][8] - Delete Wallet"
#echo "- Making a DELETE Request To: "$DELETE_WALLET_BY_ID
#RESPONSE=$(curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $DELETE_WALLET_BY_ID$WALLET_ID_CREATED)
#echo "- Got Response:"
#echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[TRANSACTION][REQUESTS]"

echo
echo "[REQUEST][1] - Create a TRANSACTION"
echo "- Making a POST Request To: "$CREATE_TRANSACTION
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$CREATE_TRANSACTION_OBJ""wallets/$WALLET_ID_CREATED/categories/$CATEGORY_ID_CREATED")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"

echo
echo "[REQUEST][2] - Get TRANSACTION By Id"
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

# shellcheck disable=SC2162
read
