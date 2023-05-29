# Parâmetros
METHOD=$1
URL=$2
TOKEN=$3
LOG_MSG=$4

# Vars
JQ_EXEC="C:\Program Files\jq\jq-win64.exe"


echo
echo "$LOG_MSG"
echo "[REQUEST] - $METHOD Request"
echo "- Making a $METHOD Request To: $URL"
RESPONSE=$(curl -X "$METHOD" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" "$URL")
echo "- Got Response:"
echo "$RESPONSE" | "$JQ_EXEC"
