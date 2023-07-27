INSERT INTO MoneyMate.users (user_id, username, email, password)
VALUES
    (0, 'system_user', 'systemUser@example.com', 'password');

INSERT INTO MoneyMate.category (category_id, category_name, user_id)
VALUES
    (0, 'Outros', 0);

INSERT INTO MoneyMate.category (category_name, user_id)
VALUES
    ('Refeições', 0),
    ('Lazer', 0),
    ('Transportes', 0),
    ('Apostas', 0),
    ('Saúde', 0),
    ('Roupa', 0),
    ('Habitação', 0),
    ('Educação', 0),
    ('Entretenimento', 0),
    ('Compras', 0),
    ('Viagens', 0),
    ('Impostos', 0),
    ('Seguro', 0),
    ('Dívidas', 0),
    ('Investimentos', 0),
    ('Presentes', 0),
    ('Poupança', 0),
    ('Telemóvel', 0),
    ('Casa', 0),
    ('Desporto', 0),
    ('Salario', 0),
    ('Transferencia', 0),
    ('Automóvel', 0);

