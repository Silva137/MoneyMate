import React, { useState, useEffect } from 'react';
import WalletCard from '../../Components/WalletCard/WalletCard';
import '../../App.css'
import './Wallets.css';
import '../../Components/WalletCard/WalletCard.css';
import {HiPlus} from "react-icons/hi";
import {CgClose} from "react-icons/cg";
import {MdDoneOutline} from "react-icons/md";
import {GoTrashcan} from "react-icons/go";

function Wallets() {
    const [modal, setModal] = useState(false);

    const [wallets, setWallets] = useState([
        { name: 'Wallet 1', balance: 100 },
        { name: 'Wallet 2', balance: 200 },
        { name: 'Wallet 3', balance: 300 },
        { name: 'Wallet 4', balance: 400 },
        { name: 'Wallet 5', balance: 500 },
        { name: 'Wallet 6', balance: 600 },
        { name: 'Wallet 7', balance: 700 },
        { name: 'Wallet 8', balance: 800 },
        { name: 'Wallet 9', balance: 900 },
        { name: 'Wallet 10', balance: 1000 },
    ]);

    function handleAddButtonClick() {
        setModal(true);
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false);
    }

    function handleSaveChangesClick() {
        // Api call to save the new wallet
        setModal(false);
    }


    return (
        <div className="bg-container">
            <div className="content-container">
                <h1 className="page-title">Wallets</h1>
                <h2 className="list-title">Private wallets</h2>
                <div className="list-container">
                    <div className={`wallet-list ${wallets.length > 4 ? 'scrollable' : ''}`}>
                        {wallets.map((wallet, index) => (
                            <WalletCard key={index} name={wallet.name} balance={wallet.balance} />
                        ))}
                    </div>
                    <button className="add-button" onClick={handleAddButtonClick}><HiPlus/></button>
                </div>
                <h2 className="list-title">Shared wallets</h2>
                <div className="list-container">
                    <div className={`wallet-list ${wallets.length > 4 ? 'scrollable' : ''}`}>
                        {wallets.map((wallet, index) => (
                            <WalletCard key={index} name={wallet.name} balance={wallet.balance} />
                        ))}
                    </div>
                    <button className="add-button" onClick={handleAddButtonClick}><HiPlus/></button>
                </div>
            </div>
            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>
                        <h2 className="modal-title">Create Wallet</h2>
                        <form onSubmit={handleSaveChangesClick}>
                            <div className="form-group field">
                                <input type="input" className="form-field" placeholder="Wallet name" required></input>
                                <label htmlFor="Wallet Name" className="form-label">Wallet Name</label>
                            </div>
                            <button type="submit" className="save-button"> <MdDoneOutline/> Save</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Wallets;
