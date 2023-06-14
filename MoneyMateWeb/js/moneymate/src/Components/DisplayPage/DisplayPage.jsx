import React, {useState} from 'react';
import './DisplayPage.css'
import TransactionItem from "../TransactionItem/TransactionItem.jsx";

const DisplayPage = ({transactions, balance, categoryName, close, onClickElement}) => {



    return (
        <div className="modal-overlay-display-page">
            <div className="modal-display-page">
                <button className="close-button" onClick={close}>
                    X
                </button>
                <h2 className="modal-title-display-page">Transactions: {categoryName} {balance} € </h2>
                <ul className="category-list-display-page">
                    {transactions.map((transaction) => (
                        <TransactionItem key={transaction.id} transaction={transaction} />
                    ))}
                </ul>
            </div>
        </div>

    );
};

export default DisplayPage;
