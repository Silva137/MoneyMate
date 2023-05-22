INSERT INTO MoneyMate.users (user_id, username, email, password)
VALUES
    (0, 'system_user', 'systemUser@example.com', 'password');

INSERT INTO MoneyMate.category (category_id, category_name, user_id)
VALUES
    (0, 'system_category_outros', 0);

INSERT INTO MoneyMate.category (category_name, user_id)
VALUES
    ('system_category_default_1',0),
    ('system_category_default_2',0),
    ('system_category_default_3',0);

