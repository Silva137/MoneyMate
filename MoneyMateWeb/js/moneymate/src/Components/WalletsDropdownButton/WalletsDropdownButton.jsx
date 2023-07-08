import React, { useState, useEffect } from 'react';
import './WalletsDropdownButton.css';
import WalletService from "../../Services/WalletService.jsx";

function WalletsDropdownButton({ onChange }) {
    const [wallets, setWallets] = useState([]);
    const [selectedWallet, setSelectedWallet] = useState(null);

    useEffect(() => {
        fetchWallets();
    }, []);

    const fetchWallets = async () => {
        try {
            const response = await WalletService.getWalletsOfUser()
            setWallets(response.wallets);
        } catch (error) {
            console.error('Error fetching wallets:', error);
        }
    };

    const handleWalletSelect = (event) => {
        const selectedOption = event.target.value;
        setSelectedWallet(selectedOption);
        onChange(selectedOption); // Notify the parent component about the selected category
    };

    return (
        <select className="dropdown-select" onChange={handleWalletSelect}>
            <option value="">Select Wallet</option>

            {wallets.map((wallet) => (
                <option value={wallet.id} key={wallet.id}>
                    {wallet.name}
                </option>
            ))}
        </select>
    );
}

export default WalletsDropdownButton;
