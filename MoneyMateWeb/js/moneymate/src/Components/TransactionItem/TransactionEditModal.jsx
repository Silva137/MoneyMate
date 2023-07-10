import React from 'react';
import './TransactionItem.css';
import { GoTrashcan } from 'react-icons/go';
import { MdDoneOutline } from 'react-icons/md';
import { CgClose } from 'react-icons/cg';

function TransactionEditModal({ title, amount, onModalClose, onUpdateClick, onChangeTitle, onChangeAmount, onDeleteClick }) {
    function privateHandleModalClose(e) {
        if (e.target.classList.contains('modal-overlay'))
            onModalClose(e)
    }

    return (
        <div className="modal-overlay" onClick={privateHandleModalClose}>
            <div className="modal">
                <button className="close-button" onClick={onModalClose}><CgClose /></button>
                <h2 className="modal-title">Edit Transaction</h2>
                <form onSubmit={onUpdateClick}>
                    <div className="form-group field">
                        <input
                            type="text"
                            className="form-field"
                            placeholder="Transaction title"
                            value={title}
                            onChange={e => onChangeTitle(e)}
                            maxLength={18}
                            required
                        />
                        <label htmlFor="Transaction Title" className="form-label">Transaction Title</label>
                    </div>
                    <div className="form-group field">
                        <input
                            type="number"
                            className="form-field"
                            placeholder="Transaction amount"
                            value={amount}
                            onChange={e => onChangeAmount(e)}
                            required
                        />
                        <label htmlFor="Amount" className="form-label">Amount</label>
                    </div>

                    <div className="button-container">
                        <button type="submit" className="save-button"> <MdDoneOutline/> Save</button>
                        <button className="delete-button" onClick={onDeleteClick}> <GoTrashcan/> Delete</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default TransactionEditModal;
