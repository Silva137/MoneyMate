
# PARAMS
METHOD=$1
URL=$2
TOKEN=$3
LOG_MSG=$4
DATA=$5

echo
echo "$LOG_MSG"
echo "[REQUEST] - $METHOD Request"
echo "- Making a $METHOD Request To:"
echo "$URL"

if [ "$DATA" == "null" ]; then
  HTTP_RESPONSE=$(curl -X "$METHOD" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" "$URL")
else
  if [ "${DATA:0:1}" = '.' ]; then
      OBJECT=$(cat "$OBJECTS" | "$JQ_EXEC" "$DATA")
      echo "- Body Of Request: $OBJECT"
      HTTP_RESPONSE=$(curl -X "$METHOD" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$OBJECT" "$URL")
  else
      echo "- Body Of Request: $DATA"
      HTTP_RESPONSE=$(curl -X "$METHOD" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$DATA" "$URL")
  fi
fi

echo "- Got Response:"
echo "$HTTP_RESPONSE" | "$JQ_EXEC"

export HTTP_RESPONSE
