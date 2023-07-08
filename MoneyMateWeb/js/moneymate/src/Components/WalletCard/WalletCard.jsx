import React, {useContext, useState} from 'react';
import './WalletCard.css';
import { RiPencilFill } from "react-icons/ri";
import { GoTrashcan } from "react-icons/go";
import { MdDoneOutline } from "react-icons/md";
import { CgClose } from "react-icons/cg";
import WalletService from "../../Services/WalletService.jsx";
import {FaChartPie, FaShare, FaShareAlt} from "react-icons/fa";
import {useNavigate} from "react-router-dom";
import {SessionContext} from "../../Utils/Session.jsx";
import InviteService from "../../Services/InviteService.jsx";
import WalletCardEditModal from "./WalletCardEditModal.jsx";
import WalletCardShareModal from "./WalletCardShareModal.jsx";

function WalletCard({wallet, getWallets}) {
    const [modal, setModal] = useState(null); // null means no modal false means editModal true means shareModal

    // Info about the modals
    const [walletName, setWalletName] = useState(wallet.name);
    const [userName, setUserName] = useState("");

    // Used to get session information about wht was previsoly selected
    const { selectedWallet, selectedStatistic } = useContext(SessionContext);
    const navigate = useNavigate();

    const systemUser = 0

    function isSharedWallet(){
        return wallet.user.id === systemUser
    }

    /** Button click */

    function handleShareButtonClick() {
        setModal(true)
    }

    function handleEditButtonClick() {
        setModal(false)
    }

    function modalClose(){
        setModal(null)
    }

    function handleStatisticsButtonClick(walletId) {
        // NAVIGATE
        if(isSharedWallet())
            navigate(`/statistics/sharedWallets/${selectedStatistic}/${selectedWallet}`)
        else
            navigate(`/statistics/wallets/${selectedStatistic}/${selectedWallet}`)
    }

    async function handleInviteButtonClick(event) {
        event.preventDefault();
        try {
            console.log("INSIDE SEND INVITE")
            const response = await InviteService.createInvite(userName, wallet.id);
            console.log(response);
            await getWallets()
        } catch (error) {
            console.error('Error updating wallet name:', error);
        }
        modalClose();
    }
    async function handleUpdateButtonClick(event) {
        event.preventDefault();
        try {
            const response = await WalletService.updateWalletName(wallet.id, walletName);
            console.log(response);
            await getWallets()
        } catch (error) {
            console.error('Error updating wallet name:', error);
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

    /** Others */


    // To support nomral text input
    const onEditModalWalletChange = (e) => {
        setUserName(e.target.value);
    };

    // To support dropDown
    const onShareModalUserChange = (selectedUser) => {
        console.log("USERNAME RECEIVED")
        console.log(selectedUser)

        setUserName(selectedUser);
    };
    async function onDeleteWallet(){
        try {
            const response = await WalletService.deleteWallet(wallet.id);
            console.log(response);
            await getWallets()
        } catch (error) {
            console.error('Error deleting wallet:', error);
        }
        modalClose()
    }

    async function onDeleteUserAssociation(){
        try {
            const response = await WalletService.removeUserFromSW(wallet.id);
            console.log(response);
            await getWallets()
        } catch (error) {
            console.error('Error deleting wallet:', error);
        }
        modalClose()
    }


    return (
        <div className="wallet-container">
            <div className="wallet-header">
                <div className="wallet-name">{wallet.name}</div>

                <button className="edit-button-wallet" onClick={() => handleStatisticsButtonClick(wallet.id)}>
                    <FaChartPie/>
                </button>

                <button className="edit-button-wallet" onClick={handleEditButtonClick}>
                    <RiPencilFill/>
                </button>

                { isSharedWallet()?
                    (<button className="edit-button-wallet" onClick={handleShareButtonClick}>
                        <FaShareAlt/>
                    </button>):
                    ("")
                }

            </div>
            <p className="balance-txt">Balance</p>
            <div className="wallet-balance">${wallet.balance.toFixed(2)}</div>

            {
                modal !== null && (
                    modal === false ? (
                        <WalletCardEditModal
                            walletName={walletName}
                            onModalClose={handleModalClose}
                            onUpdateClick={handleUpdateButtonClick}
                            onChange={e => onEditModalWalletChange(e)}
                            onDeleteClick={handleDeleteWalletClick}
                        />
                    ) : (
                        <WalletCardShareModal
                            userName={userName}
                            onModalClose={handleModalClose}
                            onSendInviteClick={handleInviteButtonClick}
                            onChange={e => onEditModalWalletChange(e)}
                        />
                    )
                )
            }
        </div>
    );
}

export default WalletCard;
