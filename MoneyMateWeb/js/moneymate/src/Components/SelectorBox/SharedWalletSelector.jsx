import React, { useState } from 'react';
import './SelectorBox.css';
import { MdArrowForwardIos } from 'react-icons/md';
import { MdArrowBackIos } from 'react-icons/md';

function SharedWalletSelector({ wallets, handleWalletChange, selectedWallet }) {
    const [startIndex, setStartIndex] = useState(0);

    const handlePrevious = () => { setStartIndex((prevIndex) => Math.max(prevIndex - 1, 0)) }
    const handleNext = () => { setStartIndex((prevIndex) => Math.min(prevIndex + 1, wallets.length - 3)) }
    const visibleWallets = wallets.slice(startIndex, startIndex + 3)

    return (
        <div className="wallet-selector">
            <MdArrowBackIos className="arrow-left" onClick={handlePrevious}></MdArrowBackIos>

            <div className="radio-inputs">
                {visibleWallets.map((wallet, index) => (
                    <label
                        key={wallet.id}
                        className={`radio ${selectedWallet === wallet.id ? 'selected' : ''}`}
                    >
                        <input
                            type="radio"
                            name="wallet"
                            value={wallet.id}
                            checked={Number(selectedWallet) === wallet.id || (index === 0 && selectedWallet === null)}
                            onChange={() => handleWalletChange(wallet.id)}
                        />
                        <span className="name">{wallet.name}</span>
                    </label>
                ))}
                <div className="space-between"></div>
            </div>

            <MdArrowForwardIos className="arrow-right" onClick={handleNext} />
        </div>
    );
}

export default SharedWalletSelector;
