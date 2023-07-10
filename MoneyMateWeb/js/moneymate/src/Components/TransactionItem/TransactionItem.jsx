import React, {useState} from 'react';
import './TransactionItem.css';
import dayjs from 'dayjs';
import TransactionEditModal from "./TransactionEditModal.jsx";
import WalletService from "../../Services/WalletService.jsx";
import TransactionService from "../../Services/TransactionService.jsx";

function TransactionItem({ transaction, getTransactions, showSuccess, showError }) {
    const formattedDate = dayjs(transaction.createdAt).format('MMMM D YYYY, HH:mm');
    const dayOfWeek = dayjs(transaction.createdAt).format('dddd');
    const [modal, setModal] = useState(false);
    const [transactionTitle, setTransactionTitle] = useState(transaction.title);
    const [transactionAmount, setTransactionAmount] = useState(transaction.amount);


    async function handleUpdateTransaction(event) {
        event.preventDefault()
        try {
            const response = await TransactionService.editTransaction(transaction.id,transaction.category.id, transactionAmount, transactionTitle);
            console.log(response);
            showSuccess('Transaction name updated successfully!')
            await getTransactions();
        } catch (error) {
            console.error('Error updating transaction name:', error);
            showError('Failed to update transaction name. Please try again.')
        }
        handleModalClose()
    }

    async function handleDeleteTransaction(event) {
        event.preventDefault()
        try {
            const response = await TransactionService.deleteTransaction(transaction.id);
            console.log(response);
            showSuccess('Transaction deleted successfully!')
            await getTransactions()
        } catch (error) {
            console.error('Error deleting transaction:', error);
            showError('Failed to delete transaction. Please try again.')
        }
        handleModalClose()
    }

    function handleTransactionItemClick() {
        setModal(true);
    }

    function handleModalClose(){
        setModal(null)
    }

    return (
        <div>
            <div className="transaction-item" onClick={handleTransactionItemClick}>
                <div className="column left-column">
                    <div className="transaction-title">{transaction.title}</div>
                    <div className="transaction-date">{formattedDate}</div>
                </div>
                <div className="column right-column">
                    <div className="transaction-amount">{transaction.amount}€</div>
                    <div className="day-of-week">{dayOfWeek}</div>
                </div>
            </div>
            {modal && (
                <TransactionEditModal
                    title={transactionTitle}
                    amount={transactionAmount}
                    onModalClose={handleModalClose}
                    onUpdateClick={handleUpdateTransaction}
                    onChangeTitle={e => setTransactionTitle(e.target.value)}
                    onChangeAmount={e => setTransactionAmount(e.target.value)}
                    onDeleteClick={handleDeleteTransaction}
                />
            )}
        </div>
    );
}

export default TransactionItem;
