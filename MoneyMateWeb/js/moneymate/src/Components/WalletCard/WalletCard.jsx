import React, {useState} from 'react';
import './WalletCard.css';
import { RiPencilFill } from "react-icons/ri";
import { GoTrashcan } from "react-icons/go";
import { MdDoneOutline } from "react-icons/md";
import { CgClose } from "react-icons/cg";

function WalletCard(props) {
    const [modal, setModal] = useState(false);
    const [walletName, setWalletName] = useState(props.name);

    function handleEditButtonClick() {
        setModal(!modal);
    }

    function handleWalletNameChange(e) {
        setWalletName(e.target.value);
    }

    function handleSaveChangesClick() {
        // Do something with the new wallet name (e.g. update it in the database)
        setModal(false);
    }

    function handleDeleteWalletClick() {
        // Do something to delete the wallet (e.g. remove it from the database)
        setModal(false);
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false);
    }

    return (
        <div className="wallet-container">
            <div className="wallet-header">
                <div className="wallet-name">{props.name}</div>
                <button className="edit-button" onClick={handleEditButtonClick}>
                    <RiPencilFill/>
                </button>
            </div>
            <h2 className="balance-txt">Balance</h2>
            <div className="wallet-balance">${props.balance.toFixed(2)}</div>
            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>
                        <h2 className="modal-title">Edit Wallet</h2>
                        <form onSubmit={handleSaveChangesClick}>
                            <div className="form-group field">
                                <input type="text" className="form-field" placeholder="Wallet name" value={walletName} onChange={handleWalletNameChange} required></input>
                                <label htmlFor="Wallet Name" className="form-label">Wallet Name</label>
                            </div>
                            <div className="button-container">
                                <button type="submit" className="save-button"> <MdDoneOutline/> Save</button>
                                <button className="delete-button" onClick={handleDeleteWalletClick}> <GoTrashcan/> Delete</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default WalletCard;
