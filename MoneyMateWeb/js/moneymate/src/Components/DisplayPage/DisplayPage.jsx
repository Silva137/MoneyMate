import React, {useState} from 'react';
import './DisplayPage.css'
import TransactionItem from "../TransactionItem/TransactionItem.jsx";
import {CgClose} from "react-icons/cg";

const DisplayPage = ({transactions, balance, categoryName, close, onClickElement}) => {

    return (
        <div className="modal-overlay-display-page">
            <div className="modal-display-page">
                <button className="close-button" onClick={close}><CgClose/></button>
                <h2 className="modal-title-display-page">Transactions: {categoryName} {balance} € </h2>
                <div className={`transaction-list ${transactions.length > 4 ? 'scrollable' : ''}`}>
                    {transactions.map((transaction) => (
                        <TransactionItem key={transaction.id} transaction={transaction} />
                    ))}
                </div>
            </div>
        </div>

    );
};

export default DisplayPage;
