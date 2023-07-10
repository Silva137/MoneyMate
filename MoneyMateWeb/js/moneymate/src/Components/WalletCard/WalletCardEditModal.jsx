import React from 'react';
import './WalletCard.css';
import { GoTrashcan } from "react-icons/go";
import { MdDoneOutline } from "react-icons/md";
import { CgClose } from "react-icons/cg";


function WalletCardEditModal({walletName, onModalClose, onUpdateClick, onChange, onDeleteClick}) {

    function privateHandleModalClose(e) {
        if (e.target.classList.contains('modal-overlay'))
            onModalClose(e)
    }

    return(
        <div className="modal-overlay" onClick={privateHandleModalClose}>
            <div className="modal">
                <button className="close-button" onClick={onModalClose}><CgClose/></button>
                <h2 className="modal-title">Edit Wallet</h2>
                <form onSubmit={onUpdateClick}>
                    <div className="form-group field">
                        <input
                            type="text"
                            className="form-field"
                            placeholder="Wallet name"
                            value={walletName}
                            onChange={e => onChange(e)}
                            maxLength={18}
                            required>
                        </input>
                        <label htmlFor="Wallet Name" className="form-label">Wallet Name</label>
                    </div>
                    <div className="button-container">
                        <button type="submit" className="save-button"> <MdDoneOutline/> Save</button>
                        <button className="delete-button" onClick={onDeleteClick}> <GoTrashcan/> Delete</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default WalletCardEditModal
