# Executar o script send_request.sh para enviar uma solicitação
./send_request.sh "GET" "$GET_ALL_WALLETS" "$TOKEN"

# Extrair os IDs de todas as wallets usando o JQ
WALLET_IDS=$(echo "$RESPONSE" | jq -r '.[].id')

# Executar o script send_request.sh para enviar uma solicitação
./send_request.sh "GET" "$GET_ALL_CATEGORIES" "$TOKEN"

# Extrair os IDs de todas as categorias usando o JQ
CATEGORY_IDS=$(echo "$RESPONSE" | jq -r '.[].id')

# Loop para criar 10 transações em cada wallet
for WALLET_ID in $WALLET_IDS
do
  for ((n=1; n<=10; n++))
  do
    # Selecionar uma categoria aleatória
    RANDOM_CATEGORY=$(shuf -e $CATEGORY_IDS -n 1)

    # Montar o corpo da solicitação JSON com os dados da transação
    CREATE_TRANSACTION_OBJ="{\"title\": \"Transação $n\", \"amount\": $(awk -v min=-500 -v max=500 'BEGIN{srand(); print int(min+rand()*(max-min+1))}'), \"user_id\": 1, \"wallet_id\": $WALLET_ID, \"category_id\": $RANDOM_CATEGORY, \"date_of_creation\": \"$(date -d "-$((n%30)) days" -Iseconds)\"}"

    # Executar o script send_request.sh para enviar uma solicitação POST
    ./send_request.sh "POST" "$CREATE_TRANSACTION" "$TOKEN" -d "$CREATE_TRANSACTION_OBJ"

    # Extrair o ID da transação criada usando o JQ
    TRANSACTION_ID=$(echo "$RESPONSE" | jq -r '.id')

    # Executar o script send_request.sh para enviar uma solicitação GET
    ./send_request.sh "GET" "$CREATE_TRANSACTION/$TRANSACTION_ID" "$TOKEN"
  done

  echo
}