import React from 'react';
import './SelectorBox.css';


function CreateTransactionSelector({handleWalletTypeChanged, selectedWalletType}) {

    const wallet = "Wallet"
    const sharedWallet = "Shared Wallet"
    const handleClick = (e) => { }

    return (
        <div className="wallet-selector">
            <div className="radio-inputs">
                <label
                    key={wallet}
                    className={`radio ${selectedWalletType === wallet ? 'selected' : ''}`}
                >
                    <input
                        type="radio"
                        name="Wallet"
                        value={wallet}
                        checked={selectedWalletType === wallet}
                        onChange={() => handleWalletTypeChanged(wallet)}
                        onClick={handleClick}
                    />
                    <span className="name">Wallet</span>
                </label>

                <label
                    key={sharedWallet}
                    className={`radio ${selectedWalletType === sharedWallet ? 'selected' : ''}`}
                >
                    <input
                        type="radio"
                        name="Shared Wallet"
                        value={sharedWallet}
                        checked={selectedWalletType === sharedWallet}
                        onChange={() => handleWalletTypeChanged(sharedWallet)}
                        onClick={handleClick}
                    />
                    <span className="name">Shared Wallet</span>
                </label>
            </div>

        </div>
    );
}

export default CreateTransactionSelector
