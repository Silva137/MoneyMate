import React, {useState} from 'react';
import './WalletCard.css';
import { RiPencilFill } from "react-icons/ri";
import { GoTrashcan } from "react-icons/go";
import { MdDoneOutline } from "react-icons/md";
import { CgClose } from "react-icons/cg";
import WalletService from "../../Services/WalletService.jsx";

function WalletCard({wallet, setWallets, setIsLoading}) {
    const [modal, setModal] = useState(false);
    const [walletName, setWalletName] = useState(wallet.name);


    async function fetchPrivateWallets() {
        try {
            setIsLoading(true);
            const response = await WalletService.getWalletsOfUser();
            setWallets(response.wallets);
        } catch (error) {
            console.error('Error fetching private wallets of user:', error);
        } finally {
            setIsLoading(false);
        }
    }

    function handleEditButtonClick() {
        setModal(true)
    }

    async function handleSaveChangesClick(event) {
        event.preventDefault();
        try {
            const response = await WalletService.updateWalletName(wallet.id, walletName);
            console.log(response);
            await fetchPrivateWallets()
        } catch (error) {
            console.error('Error updating wallet name:', error);
        }
        setModal(false);
    }

    async function handleDeleteWalletClick() {
        try {
            const response = await WalletService.deleteWallet(wallet.id);
            console.log(response);
            await fetchPrivateWallets()
        } catch (error) {
            console.error('Error deleting wallet:', error);
        }
        setModal(false);
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }

    return (
        <div className="wallet-container">
            <div className="wallet-header">
                <div className="wallet-name">{wallet.name}</div>
                <button className="edit-button-wallet" onClick={handleEditButtonClick}>
                    <RiPencilFill/>
                </button>
            </div>
            <p className="balance-txt">Balance</p>
            <div className="wallet-balance">${wallet.balance.toFixed(2)}</div>
            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>
                        <h2 className="modal-title">Edit Wallet</h2>
                        <form onSubmit={handleSaveChangesClick}>
                            <div className="form-group field">
                                <input type="text" className="form-field" placeholder="Wallet name" value={walletName} onChange={e => setWalletName(e.target.value)} required></input>
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
