import React, {useContext, useEffect, useState} from 'react';
import './WalletSelector.css';
import { MdArrowForwardIos } from 'react-icons/md';
import {MdArrowBackIos} from 'react-icons/md';
import {SessionContext} from "../../Utils/Session.jsx";

function WalletSelector({ wallets }) {
    const { selectedWallet, setSelectedWallet } = useContext(SessionContext);
    const [startIndex, setStartIndex] = useState(0);

    useEffect(() => {
        const storedWallet = localStorage.getItem('selectedWallet');
        if (storedWallet) {
            setSelectedWallet(storedWallet);
        }
    }, []);

    const handlePrevious = () => { setStartIndex((prevIndex) => Math.max(prevIndex - 1, 0)) }

    const handleNext = () => { setStartIndex((prevIndex) => Math.min(prevIndex + 1, wallets.length - 3)) }

    const visibleWallets = wallets.slice(startIndex, startIndex + 3)

    const handleClick = (e) => {
        const walletId = e.target.value;
        console.log('walletId ' + walletId + ' selectedWallet ' + selectedWallet)
        if (walletId == selectedWallet) {
            setSelectedWallet("null");
            localStorage.removeItem('selectedWallet');
        }

    }

    return (
        <div className="wallet-selector">
            <MdArrowBackIos className="arrow-left" onClick={handlePrevious}></MdArrowBackIos>

            <div className="radio-inputs">
                {selectedWallet !== null && visibleWallets.map((wallet) => (
                    <label
                        key={wallet.id}
                        className={`radio ${selectedWallet === wallet.id ? 'selected' : ''}`}
                    >
                        <input
                            type="radio"
                            name="wallet"
                            value={wallet.id}
                            checked={Number(selectedWallet) === wallet.id}
                            onChange={() => setSelectedWallet(wallet.id)}
                            onClick={handleClick}
                        />
                        <span className="name">{wallet.name}</span>
                    </label>
                ))}
            </div>


            <MdArrowForwardIos className="arrow-right" onClick={handleNext} />
        </div>
    );
}

export default WalletSelector
