#!/bin/bash

# THIS SCRIPT PRODUCE REQUESTS TO TEST ALL THE API DOMAIN INSERTION AND REMOVAL REQUESTS
# THIS SCRIPT PRODUCE REQUESTS TO TEST ALL THE API LOGICAL REQUESTS
# First run the insertDataScript.sql to introduce data in the database

echo "[INIT] ------------------------------------------------------"

JQ_EXEC="C:\Program Files\jq\jq-win64.exe"
OBJECTS="objects.json"
OUT_FILE="acessTokens.txt"
rm -f "$OUT_FILE" # Remove o arquivo de saída se já existir

HOST=http://localhost:8080
CREATE_USER=$HOST"/auth/register"
GET_USERS=$HOST"/users"

CREATE_USER_OBJ_1=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_user_1')
CREATE_USER_OBJ_2=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_user_2')
CREATE_USER_OBJ_3=$(cat "$OBJECTS" | "$JQ_EXEC" '.create_user_3')

echo
echo "[STARTING_REQUESTS]------------------------------------------" #>> $OUT_FILE
echo

echo "[USER][REQUESTS]"

echo
echo "[REQUEST][1] - Create User 1"
echo "- Making a POST Request To: "$CREATE_USER
echo "$CREATE_USER_OBJ_1"
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -d "$CREATE_USER_OBJ_1" $CREATE_USER)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"
TOKEN=$(echo "$RESPONSE" | "$JQ_EXEC" -r '.access_token')
if [ "$TOKEN" == "null" ]; then
  echo "[ERROR] - Token is NULL, Change userDetails and try again"
  echo "[EXITING...]"
  sleep 10
  exit
fi
echo $TOKEN >> $OUT_FILE

echo
echo "[REQUEST][1] - Create User 2"
echo "- Making a POST Request To: "$CREATE_USER
echo "$CREATE_USER_OBJ_2"
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -d "$CREATE_USER_OBJ_2" $CREATE_USER)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"
TOKEN=$(echo "$RESPONSE" | "$JQ_EXEC" -r '.access_token')
if [ "$TOKEN" == "null" ]; then
  echo "[ERROR] - Token is NULL, Change userDetails and try again"
  echo "[EXITING...]"
  sleep 10
  exit
fi
echo $TOKEN >> $OUT_FILE

echo
echo "[REQUEST][1] - Create User 3"
echo "- Making a POST Request To: "$CREATE_USER
echo "$CREATE_USER_OBJ_3"
RESPONSE=$(curl -X POST -H "Content-Type: application/json" -d "$CREATE_USER_OBJ_3" $CREATE_USER)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"
TOKEN=$(echo "$RESPONSE" | "$JQ_EXEC" -r '.access_token')
if [ "$TOKEN" == "null" ]; then
  echo "[ERROR] - Token is NULL, Change userDetails and try again"
  echo "[EXITING...]"
  sleep 10
  exit
fi
echo $TOKEN >> $OUT_FILE

echo
echo "[REQUEST][2] - Get All Users"
echo "- Making a GET Request To: "$GET_USERS
RESPONSE=$(curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" $GET_USERS)
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"


# shellcheck disable=SC2162
read
