-- Para criar os users é necessario dar login para gerar o token



-- Criar 2 users demo

-- Criar 3 users Para apresentar carteira partilhada~

-- Criar carteira partilhada e partilhar com 2 utilizadores

-- 3 utilizador criar na apresetnação apra mostrar os convites



INSERT INTO MoneyMate.wallet (wallet_name, user_id)
VALUES
    -- Carteiras User1
    ('Santander',1),
    ('Caixa Geral',1),
    ('Poupança',1),
    -- Carteiras User2
    ('Principal',2);

INSERT INTO MoneyMate.transactions (title, amount, user_id, wallet_id, category_id, date_of_creation, periodical)
VALUES

    --------------------------------------

    -- VALORES PARA USER1

    --------------------------------------

    -- Valores basicos para user1 para carteira Santander para Julho
    ('Salario Julho', 1400, 1, 1, 21, '2023-07-05', 0),
    ('Netflix Julho', -10, 1, 1, 19, '2023-07-15', 0),
    ('Conta Água Julho', -30, 1, 1, 19, '2023-07-15', 0),
    ('Conta Luz Julho', -30, 1, 1, 19, '2023-07-15', 0),
    ('Renda Casa Julho', -300, 1, 1, 19, '2023-07-15', 0),
    ('Internet e TV Julho', -50, 1, 1, 19, '2023-07-15', 0),
    ('Pacote Telemovel Julho', -15, 1, 1, 19, '2023-07-05', 0),
    ('Transferencia Carteira poupanca', -200, 1, 1, 22, '2023-07-16', 0),


    -- Valores basicos para user1 para carteira Santander para Agosto
    ('Salario Agosto', 1400, 1, 1, 21, '2023-08-05', 0),
    ('Netflix Agosto', -10, 1, 1, 19, '2023-08-15', 0),
    ('Conta Água Agosto', -30, 1, 1, 19, '2023-08-15', 0),
    ('Conta Luz Agosto', -30, 1, 1, 19, '2023-08-15', 0),
    ('Renda Casa Agosto', -300, 1, 1, 19, '2023-08-15', 0),
    ('Internet e TV Agosto', -50, 1, 1, 19, '2023-08-15', 0),
    ('Pacote Telemovel Agosto', -15, 1, 1, 19, '2023-08-15', 0),
    ('Transferencia Carteira poupanca', -120, 1, 1, 22, '2023-08-16', 0),



-- Valores diversos para user1 para carteira Santander para Julho

('Uber Lisboa', -10, 1, 1, 3, '2023-07-25', 0),
('Autocarro Porto', -15, 1, 1, 3, '2023-07-07', 0),
('Combustivel', -40, 1, 1, 3, '2023-07-02', 0),
('Passe Carris', -40, 1, 1, 3, '2023-07-12', 0),
('Almoço Trab', -20, 1, 1, 1, '2023-07-09', 0),
('Jantar Familia', -15, 1, 1, 1, '2023-07-19', 0),
('Pequeno Almoço', -5, 1, 1, 1, '2023-07-09', 0),
('Prendas Anos', 40, 1, 1, 16, '2023-07-09', 0),
('Raspadinha', 40, 1, 1, 4, '2023-07-16', 0),
('Livros', -20, 1, 1, 10, '2023-07-16', 0),
('Roupa', -100, 1, 1, 6, '2023-07-16', 0),
('Tenis Desporto', -50, 1, 1, 20, '2023-07-16', 0),
('Equipamento Futebol', -50, 1, 1, 20, '2023-07-16', 0),

-- Valores diversos para user1 para carteira Santander para Agosto
('Uber Casa', -15, 1, 1, 3, '2023-08-25', 0),
('Combustivel', -40, 1, 1, 3, '2023-08-02', 0),
('Combustivel', -50, 1, 1, 3, '2023-08-02', 0),
('Ferias Parque Diversoes', -40, 1, 1, 9, '2023-08-12', 0),
('Almoço', -20, 1, 1, 1, '2023-08-09', 0),
('Jantar Aniversario', -15, 1, 1, 1, '2023-08-19', 0),
('Roupa Casual', -100, 1, 1, 6, '2023-08-16', 0),
('Raquete Tenis', -20, 1, 1, 20, '2023-08-16', 0),

-- Valores diversos para user1 para carteira Caixa Geral para Julho
('Trabalho FreeLancing Julho', 800, 1, 2, 21, '2023-07-05', 0),
('Hospital', -50, 1, 2, 5, '2023-07-16', 0),
('Medicamentos', -50, 1, 2, 5, '2023-07-16', 0),
('Renda Casa Ferias', -300, 1, 2, 19, '2023-07-16', 0),
('Bar Lisboa', -35, 1, 2, 9, '2023-07-16', 0),
('Compras Continente', -100, 1, 2, 10, '2023-07-16', 0),
('Transferencia Carteira poupanca', -80, 1, 2, 22, '2023-07-16', 0),

-- Valores para user1 para carteira Poupança
('Transferencia de Carteira Santander', 200, 1, 3, 22, '2023-07-16', 0),
('Transferencia de Carteira Santander', 120, 1, 3, 22, '2023-08-16', 0),
('Transferencia de Carteira Caixa Geral', 80, 1, 3, 22, '2023-07-16', 0),
('Investimentos Ações Julho', -100, 1, 3, 15, '2023-07-02', 0),
('Retorno Investimentos Julho', 170, 1, 3, 15, '2023-07-26', 0),

--------------------------------------

-- VALORES PARA USER2

--------------------------------------

-- Valores basicos para user1 para carteira Santander para Julho
    ('Salario Julho', 1400, 2, 4, 21, '2023-07-05', 0),
    ('Netflix Julho', -10, 2, 4, 19, '2023-07-15', 0),
    ('Conta Água Julho', -30, 2, 4, 19, '2023-07-15', 0),
    ('Conta Luz Julho', -30, 2, 4, 19, '2023-07-15', 0),
    ('Renda Casa Julho', -300, 2, 4, 19, '2023-07-15', 0),
    ('Internet e TV Julho', -50, 2, 4, 19, '2023-07-15', 0),
    ('Pacote Telemovel Julho', -15, 2, 4, 19, '2023-07-05', 0),

-- Valores diversos para user1 para carteira Santander para Agosto
    ('Uber Casa', -15, 2, 4, 3, '2023-07-25', 0),
    ('Combustivel', -40, 2, 4, 3, '2023-07-02', 0),
    ('Combustivel', -50, 2, 4, 3, '2023-07-02', 0),
    ('Ferias Parque Diversoes', -40, 2, 4, 9, '2023-07-12', 0),
    ('Almoço', -20, 2, 4, 1, '2023-07-09', 0),
    ('Jantar Aniversario', -15, 2, 4, 1, '2023-07-19', 0),
    ('Roupa Casual', -100, 2, 4, 6, '2023-07-16', 0),
    ('Raquete Tenis', -20, 2, 4, 20, '2023-07-16', 0);
