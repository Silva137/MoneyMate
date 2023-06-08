
# PARAMS
METHOD=$1
URL=$2
LOG_MSG=$3
DATA=$4

# VARS
JQ_EXEC="C:\Program Files\jq\jq-win64.exe"

echo
echo "$LOG_MSG"
echo "[REQUEST] - $METHOD Request"
echo "- Making a $METHOD Request To: $URL"

OBJECT=$(cat "$OBJECTS" | "$JQ_EXEC" "$DATA")

HTTP_RESPONSE=$(curl -X "$METHOD" -H "Content-Type: application/json" -d "$OBJECT" "$URL")

echo "- Got Response:"
echo "$HTTP_RESPONSE" | "$JQ_EXEC"

TOKEN=$(echo "$HTTP_RESPONSE" | "$JQ_EXEC" -r '.access_token')
if [ "$TOKEN" == "null" ]; then
  echo "[ERROR] - Token is NULL, Change userDetails and try again"
  echo "[EXITING...]"
  sleep 10
  exit
fi

export TOKEN
export HTTP_RESPONSE
