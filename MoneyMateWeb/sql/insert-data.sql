/*
Valores de teste

INSERT INTO moneymate.users (username, email, password)
VALUES ('Gonçalo', 'goncalosilva17@gmail.com', '12345');

INSERT INTO MoneyMate.users (user_id, username, email, password)
VALUES (0, 'admin', 'admin@example.com', 'password_hash_here');

INSERT INTO moneymate.category(name)
VALUES ('Saúde'),
       ('Educação'),
       ('Imóveis'),
       ('Lares'),
       ('Gerais Familiares'),
       ('Manutenção e reparação de veículos  Automóveis'),
       ('Manutenção e reparação de motociclos, de suas peças e acessórios'),
       ('Alojamento, restauração e similares'),
       ('Atividades de salões de cabeleireiro e institutos de beleza'),
       ('Atividades Veterinárias'),
       ('Ginásios'),
       ('Aquisição de passes mensais');

 /* Nao correr isto, mas sim script para registar 3 nvoos users e retornando acess token*/
INSERT INTO MoneyMate.users (username, email, password)
VALUES
    ('User 1', 'user1@example.com', 'password1'),
    ('User 2', 'user2@example.com', 'password2'),
    ('User 3', 'user3@example.com', 'password3');
 */

INSERT INTO MoneyMate.category (category_name, user_id)
VALUES
    ('Categoria 1 - Compras', 1),
    ('Categoria 2 - Lazer', 1),
    ('Categoria 3 - Desporto', 1),
    ('Categoria 4 - Saude', 2),
    ('Categoria 5 - Carro', 2),
    ('Categoria 6 - Viagens', 2),
    ('Categoria 7 - Prendas', 3),
    ('Categoria 8 - Roupa', 3),
    ('Categoria 9 - Apostas', 3),
    ('Categoria 10 - Conta Agua', 3);

-- Inserção de dados para a tabela wallet
INSERT INTO MoneyMate.wallet (wallet_name, user_id)
VALUES
    ('Wallet 1 - User 1', 1),
    ('Wallet 2 - User 1', 1),
    ('Wallet 3 - User 2', 2),
    ('Wallet 4 - User 2', 2),
    ('Wallet 5 - User 3', 3),
    ('Wallet 6 - User 3', 3);


INSERT INTO MoneyMate.transactions (title, amount, user_id, wallet_id, category_id, date_of_creation)
SELECT
    'Transação ' || transaction.n || ' Wallet ' || wallet.wallet_id || ' User ' || wallet.user_id ,
    CAST(RANDOM() * 1000 - 500 AS DECIMAL(10, 2)),
    wallet.user_id,
    wallet.wallet_id,
    (
        SELECT category_id
        FROM MoneyMate.category c
        WHERE c.user_id = wallet.user_id
        ORDER BY RANDOM()
        LIMIT 1
    ),
    CURRENT_TIMESTAMP - INTERVAL '1 day' * (transaction.n % 30)  -- Data de criação para os últimos 30 dias
FROM
    MoneyMate.wallet wallet
    CROSS JOIN LATERAL (
        SELECT generate_series(1, 10) AS n  -- Inserir 10 transações para cada wallet
    ) transaction;


SELECT SUM(transactions.amount)
FROM MoneyMate.transactions transactions
WHERE transactions.wallet_id = 2

