-- Atualizar 30% das transações para 1 mês atrás
UPDATE MoneyMate.transactions
SET date_of_creation = CURRENT_DATE - INTERVAL '15 days'
WHERE transaction_id IN (
    SELECT transaction_id
    FROM (
             SELECT transaction_id
             FROM MoneyMate.transactions
             WHERE random() < 0.3
             LIMIT (SELECT COUNT(*) * 0.3 FROM MoneyMate.transactions)
         ) AS subquery
);

-- Atualizar outros 30% das transações para 6 meses atrás
UPDATE MoneyMate.transactions
SET date_of_creation = CURRENT_DATE - INTERVAL '3 months'
WHERE transaction_id IN (
    SELECT transaction_id
    FROM (
             SELECT transaction_id
             FROM MoneyMate.transactions
             WHERE random() < 0.3 AND date_of_creation > CURRENT_DATE - INTERVAL '1 month'
             LIMIT (SELECT COUNT(*) * 0.3 FROM MoneyMate.transactions)
         ) AS subquery
);
