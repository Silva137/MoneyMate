source ./export_fields.sh

# Vars
NUM_TRANSACTIONS_PER_WALLET=10
USER_OBJ=".create_user_1"
USER_REQUEST="$LOGIN_USER"
USER_REQUEST1="$CREATE_USER"
# Criar Utilizador
source ./register_user.sh "POST" "$USER_REQUEST1" "Register User" "$USER_OBJ"

# Criar Categorias
source ./send_request.sh "POST" "$CREATE_CATEGORY" "$TOKEN" "[REQUEST][2] - Create Category" ".create_category_1"
source ./send_request.sh "POST" "$CREATE_CATEGORY" "$TOKEN" "[REQUEST][2] - Create Category" ".create_category_2"
source ./send_request.sh "POST" "$CREATE_CATEGORY" "$TOKEN" "[REQUEST][2] - Create Category" ".create_category_3"
source ./send_request.sh "POST" "$CREATE_CATEGORY" "$TOKEN" "[REQUEST][2] - Create Category" ".create_category_4"
source ./send_request.sh "POST" "$CREATE_CATEGORY" "$TOKEN" "[REQUEST][2] - Create Category" ".create_category_5"

# Guardar Ids
source ./send_request.sh "GET" "$GET_CATEGORIES" "$TOKEN" "[REQUEST][2] - Get Categories of User"
CATEGORY_IDS_OF_USER=$(echo "$HTTP_RESPONSE" | "$JQ_EXEC" -r '.userCategories.categories[].id')
CATEGORY_IDS_SYSTEM=$(echo "$HTTP_RESPONSE" | "$JQ_EXEC" -r '.systemCategories.categories[].id')
echo "$CATEGORY_IDS_OF_USER"
echo "$CATEGORY_IDS_SYSTEM"

# Criar Carteiras
source ./send_request.sh "POST" "$CREATE_WALLET" "$TOKEN" "[REQUEST][2] - Create Wallet" ".create_wallet_1"
source ./send_request.sh "POST" "$CREATE_WALLET" "$TOKEN" "[REQUEST][2] - Create Wallet" ".create_wallet_2"
source ./send_request.sh "POST" "$CREATE_WALLET" "$TOKEN" "[REQUEST][2] - Create Wallet" ".create_wallet_3"
source ./send_request.sh "POST" "$CREATE_WALLET" "$TOKEN" "[REQUEST][2] - Create Wallet" ".create_wallet_4"

# Guardar Ids
source ./send_request.sh "GET" "$GET_WALLETS_OF_USER" "$TOKEN" "[REQUEST][2] - Get Wallets of User"
WALLET_IDS=$(echo "$HTTP_RESPONSE" | "$JQ_EXEC" -r '.wallets[].id')


# Criar Transações, 10 em cada Carteira
echo "$WALLET_IDS" | while read -r LINE; do
  WALLET_ID=$(echo "$LINE" | tr -cd '[:digit:]')

  for ((n=1; n<="$NUM_TRANSACTIONS_PER_WALLET"; n++)); do
    # Gerar Categoria Aleatoria
    #IFS=$'\n' read -d '' -r -a CATEGORY_ARRAY_USER <<< "$CATEGORY_IDS_OF_USER"
    IFS=$'\n' read -d '' -r -a CATEGORY_ARRAY_SYS <<< "$CATEGORY_IDS_SYSTEM"

    FILTERED_CATEGORY_ARRAY=("${CATEGORY_ARRAY_SYS[@]/#0/}")  # Remove o valor 0 do array
    RANDOM_CATEGORY=${CATEGORY_ARRAY_SYS[RANDOM % ${#FILTERED_CATEGORY_ARRAY[@]}]}
    RANDOM_CATEGORY=$(echo "$RANDOM_CATEGORY" | tr -cd '[:digit:]')

    # Criar objecto com valores aleatorios, 1001 e 500 gera um numero de -500 a 500 , -100 é para aumentar probabalidade de n ser negaitvo
    # TODO Na demonstração criar uma transacao de salario, ex+600
    amount=$((RANDOM % 1001 - 500 -100))
    CREATE_TRANSACTION_OBJ="{\"amount\":\"$amount\",\"title\":\"Transacao_$n \",\"periodical\":\"0\"}"

    # Fazer o pedido
    source ./send_request.sh "POST" "$CREATE_TRANSACTION/wallets/$WALLET_ID/categories/$RANDOM_CATEGORY" "$TOKEN" "[REQUEST][2] - Create Transaction" "$CREATE_TRANSACTION_OBJ"
  done
done

# Printar Wallets
source ./send_request.sh "GET" "$GET_WALLETS_OF_USER" "$TOKEN" "[REQUEST][2] - Get Wallets of User"

