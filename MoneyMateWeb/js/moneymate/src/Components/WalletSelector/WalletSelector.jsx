import React, {useContext, useState} from 'react';
import './WalletSelector.css';
import { MdArrowForwardIos } from 'react-icons/md';
import {MdArrowBackIos} from 'react-icons/md';
import {SessionContext} from "../../Utils/Session.jsx";

function WalletSelector({ wallets }) {
    const { selectedWallet, setSelectedWallet } = useContext(SessionContext);
    const [startIndex, setStartIndex] = useState(0);

    const handlePrevious = () => { setStartIndex((prevIndex) => Math.max(prevIndex - 1, 0)) }

    const handleNext = () => { setStartIndex((prevIndex) => Math.min(prevIndex + 1, wallets.length - 3)) }

    const visibleWallets = wallets.slice(startIndex, startIndex + 3)

    return (
        <div className="wallet-selector">
            <MdArrowBackIos className="arrow-left" onClick={handlePrevious}></MdArrowBackIos>

            <div className="wallet-buttons">
                {selectedWallet !== null && visibleWallets.map((wallet) => (
                    <div
                        key={wallet.id}
                        className={`wallet-button ${selectedWallet == wallet.id ? 'selected' : ''}`}
                        onClick={() => setSelectedWallet(wallet.id)}
                    >
                        {wallet.name}
                    </div>
                ))}
            </div>


            <MdArrowForwardIos className="arrow-right" onClick={handleNext} />
        </div>
    );
}

export default WalletSelector
