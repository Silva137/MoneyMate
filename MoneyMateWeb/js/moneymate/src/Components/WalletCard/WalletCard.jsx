import React, {useContext, useState} from 'react';
import './WalletCard.css';
import '../../App.css';
import { RiPencilFill } from "react-icons/ri";
import WalletService from "../../Services/WalletService.jsx";
import {FaChartPie, FaShare, FaShareAlt, FaUsers} from "react-icons/fa";
import {useNavigate} from "react-router-dom";
import InviteService from "../../Services/InviteService.jsx";
import WalletCardEditModal from "./WalletCardEditModal.jsx";
import WalletCardShareModal from "./WalletCardShareModal.jsx";
import WalletCardUsers from "./WalletCardUsers.jsx";

function WalletCard({wallet, getWallets, setAlert}) {
    const [modal, setModal] = useState(null); // null means no modal ;false means editModal; true means shareModal; users means UsersOF SW
    const [walletName, setWalletName] = useState(wallet.name);
    const [userName, setUserName] = useState("");
    const navigate = useNavigate();

    const systemUser = 0

    function isSharedWallet(){return wallet.user.id === systemUser}

    /** Button click */

    function handleShareButtonClick() {
        setModal(true)
    }

    function handleViewUsersClick(){
        setModal("users")

    }

    function handleEditButtonClick() {
        setWalletName(wallet.name);
        setModal(false);
    }


    function modalClose(){
        setModal(null)
    }

    function handleStatisticsButtonClick(walletId) {
        // NAVIGATE
        if(isSharedWallet())
            navigate(`/statistics/sharedWallets/graphics/${walletId}`)
        else
            navigate(`/statistics/wallets/graphics/${walletId}`)
    }

    async function handleInviteButtonClick(event) {
        event.preventDefault();
        try {
            const response = await InviteService.createInvite(userName, wallet.id);
            console.log(response);
            showSuccessAlert('Invite sent successfully!')
        } catch (error) {
            console.error('Error updating wallet name:', error);
            showErrorAlert('Failed to send invite. Please try again.')
        }
        modalClose();
    }

    async function handleUpdateButtonClick(event) {
        event.preventDefault();
        try {
            const response = await WalletService.updateWalletName(wallet.id, walletName);
            console.log(response);
            showSuccessAlert('Wallet name updated successfully!')
            await getWallets();
        } catch (error) {
            console.error('Error updating wallet name:', error);
            showErrorAlert('Failed to update wallet name. Please try again.')
        }
        modalClose();
    }

    async function handleDeleteWalletClick() {
        if(wallet.user.id === systemUser)
            await onDeleteUserAssociation()
        else
            await onDeleteWallet()
    }

    function handleModalClose(e) {
        modalClose()
    }


    async function onDeleteWallet(){
        try {
            const response = await WalletService.deleteWallet(wallet.id);
            console.log(response);
            showSuccessAlert('Wallet deleted successfully!')
            await getWallets()
        } catch (error) {
            console.error('Error deleting wallet:', error);
            showErrorAlert('Failed to delete wallet. Please try again.')
        }
        modalClose()
    }

    async function onDeleteUserAssociation(){
        try {
            const response = await WalletService.removeUserFromSW(wallet.id);
            console.log(response);
            showSuccessAlert('Shared wallet deleted successfully!')
            await getWallets()
        } catch (error) {
            console.error('Error deleting wallet:', error);
            showErrorAlert('Failed to delete shared wallet. Please try again.')
        }
        modalClose()
    }

    function showSuccessAlert(message) {setAlert({show: true, message: message, severity: 'success'});}
    function showErrorAlert(message) {setAlert({ show: true, message: message, severity: 'error' });}


    return (
        <div className="wallet-container">
            <div className="wallet-details">
                <div className="wallet-name">{wallet.name}</div>
                <p className="balance-txt">Balance</p>
                <div className="wallet-balance">${wallet.balance.toFixed(2)}</div>
            </div>
            <div className="wallet-buttons">
                <button className="edit-button-wallet" onClick={() => handleStatisticsButtonClick(wallet.id)}>
                    <FaChartPie/>
                </button>

                <button className="edit-button-wallet" onClick={handleEditButtonClick}>
                    <RiPencilFill/>
                </button>

                { isSharedWallet()?
                    <button className="edit-button-wallet" onClick={handleViewUsersClick}>
                        <FaUsers/>
                    </button>
                    :("")
                }

                { isSharedWallet()?
                    <button className="edit-button-wallet" onClick={handleShareButtonClick}>
                        <FaShareAlt/>
                    </button>
                    :("")
                }
            </div>
            {
                modal !== null && (
                    modal === false ? (
                        <WalletCardEditModal
                            walletName={walletName}
                            onModalClose={handleModalClose}
                            onUpdateClick={handleUpdateButtonClick}
                            onChange={e => setWalletName(e.target.value)}
                            onDeleteClick={handleDeleteWalletClick}
                        />
                    ) : (
                        modal === true ? (
                            <WalletCardShareModal
                            userName={userName}
                            onModalClose={handleModalClose}
                            onSendInviteClick={handleInviteButtonClick}
                            onChange={e => setUserName(e.target.value)}
                            />
                        ): (
                            modal === "users" ? (
                                <WalletCardUsers
                                    walletId={wallet.id}
                                    walletName={walletName}
                                    onModalClose={handleModalClose}
                                />
                            ):("")
                        )
                    )
                )
            }
        </div>
    );
}

export default WalletCard;
