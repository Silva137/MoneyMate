import React from 'react';
import './TransactionItem.css';
import dayjs from "dayjs";

function TransactionItem({ transaction }) {
    const formattedDate = dayjs(transaction.createdAt).format("MMMM D, YYYY");
    const dayOfWeek = dayjs(transaction.createdAt).format('dddd');

    return (
        <div className="transaction-item">
            <div className="column left-column">
                <div className="transaction-title">{transaction.title}</div>
                <div className="transaction-date">{formattedDate}</div>
            </div>
            <div className="column right-column">
                <div className="transaction-amount">{transaction.amount}€</div>
                <div className="day-of-week">{dayOfWeek}</div>
            </div>
        </div>
    );


}

export default TransactionItem;
