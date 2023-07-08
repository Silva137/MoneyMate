import React, {useContext, useEffect, useState} from 'react';
import './SelectorBox.css';
import { MdArrowForwardIos } from 'react-icons/md';
import {MdArrowBackIos} from 'react-icons/md';
import {SessionContext} from "../../Utils/Session.jsx";
import {useNavigate, useParams} from 'react-router-dom';

function WalletSelector({ wallets, handleWalletChange, selectedWallet }) {
    const { setSelectedWallet } = useContext(SessionContext);
    const [startIndex, setStartIndex] = useState(0);

    // Whts the need of this??
    useEffect(() => {
        const storedWallet = localStorage.getItem('selectedWallet');
        if (storedWallet) {
            setSelectedWallet(storedWallet);
        }
    }, []);

    const handlePrevious = () => { setStartIndex((prevIndex) => Math.max(prevIndex - 1, 0)) }
    const handleNext = () => { setStartIndex((prevIndex) => Math.min(prevIndex + 1, wallets.length - 3)) }
    const visibleWallets = wallets.slice(startIndex, startIndex + 3)
    const handleClick = (e) => { }

    return (
        <div className="wallet-selector">
            <MdArrowBackIos className="arrow-left" onClick={handlePrevious}></MdArrowBackIos>

            <div className="radio-inputs">
                <>
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
                                onChange={() => handleWalletChange(wallet.id)}
                                onClick={handleClick}
                            />
                            <span className="name">{wallet.name}</span>
                        </label>
                    ))}
                    <div className="space-between"></div>
                </>

                <label className={`radio ${selectedWallet === -1 ? 'selected' : 'overAllRadio'}`}>
                    <input
                        type="radio"
                        name="wallet"
                        value={-1}
                        checked={selectedWallet === -1}
                        onChange={() => handleWalletChange(-1)}
                        onClick={handleClick}
                    />
                    <span className="name">OverAll</span>
                </label>
            </div>

            <MdArrowForwardIos className="arrow-right" onClick={handleNext} />
        </div>
    );
}

export default WalletSelector
