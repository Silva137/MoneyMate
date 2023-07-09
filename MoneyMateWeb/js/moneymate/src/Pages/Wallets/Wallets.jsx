import React, {useState, useEffect, useContext} from 'react';
import WalletCard from '../../Components/WalletCard/WalletCard';
import '../../App.css'
import './Wallets.css';
import '../../Components/WalletCard/WalletCard.css';
import {SyncLoader} from "react-spinners";
import {HiPlus} from "react-icons/hi";
import {CgClose} from "react-icons/cg";
import {MdDoneOutline} from "react-icons/md";
import WalletService from "../../Services/WalletService.jsx";

function Wallets() {
    const [modal, setModal] = useState(false)
    const [wallets, setWallets] = useState([])
    const [sharedWallets, setSharedWallets] = useState([])
    const [walletName, setWalletName] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [request, setRequest] = useState('');

    useEffect( () => {
        getWallets()
    }, []);

    async function getWallets(){
        setIsLoading(true);
        await fetchPrivateWallets();
        await fetchSharedWallets();
        setIsLoading(false);
    }

    async function fetchPrivateWallets() {
        try {
            const response = await WalletService.getWalletsOfUser();
            setWallets(response.wallets);
        } catch (error) {
            console.error('Error fetching private wallets of user:', error);
        }
    }

    async function fetchSharedWallets() {
        try {
            const response = await WalletService.getSharedWalletsOfUser()
            setSharedWallets(response.wallets)
        } catch (error) {
            console.error('Error fetching shared wallets of user:', error)
        }
    }

    async function handleSubmit(event) {
        event.preventDefault();
        if (request === "private") {
            return onCreateWallet(event);
        } else if (request === "shared") {
            return onCreateSharedWallet(event);
        }
    }

    function handleCreatePWButtonClick() {
        setModal(true)
        setRequest("private")
    }

    function handleCreateSWButtonClick() {
        setModal(true)
        setRequest("shared")
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }

    async function onCreateWallet(event) {
        // Api call to create a new wallet
        event.preventDefault()
        try {
            const response = await WalletService.createWallet(walletName)
            console.log(response)
            await fetchPrivateWallets()
        } catch (error) {
            console.error('Error creating a new wallet:', error)
        }
        setModal(false)
    }

    async function onCreateSharedWallet(event) {
        // Api call to create a new wallet
        event.preventDefault()
        try {
            const response = await WalletService.createSharedWallet(walletName)
            console.log(response)
            await fetchSharedWallets()
        } catch (error) {
            console.error('Error creating a new wallet:', error)
        }
        setModal(false)
    }


    return (
        <div className="bg-container">
            <div className="content-container">
                <h1 className="page-title">Wallets</h1>
                <p className="list-title">Private wallets</p>
                <div className="list-container">
                    {isLoading ? (
                        <div className="loader-container">
                            <SyncLoader size={50} color={'#67d9a0'} loading={isLoading} />
                        </div>
                    ) : (
                        <div className={`wallet-list ${wallets.length > 4 ? 'scrollable' : ''}`}>
                            {wallets.map((wallet, index) => (
                                <WalletCard key={index} wallet={wallet} getWallets={getWallets} />
                            ))}
                        </div>
                    )}
                    <button className="add-button" onClick={handleCreatePWButtonClick}><HiPlus/></button>
                </div>
                <p className="list-title">Shared wallets</p>
                <div className="list-container">
                    {isLoading ? (
                        <div className="loader-container">
                            <SyncLoader size={50} color={'#67d9a0'} loading={isLoading} />
                        </div>
                    ) : (
                        <div className={`wallet-list ${sharedWallets.length > 4 ? 'scrollable' : ''}`}>
                            {sharedWallets.map((wallet, index) => (
                                <WalletCard key={index} wallet={wallet} getWallets={getWallets}  />
                            ))}
                        </div>
                    )}
                    <button className="add-button" onClick={handleCreateSWButtonClick}><HiPlus/></button>
                </div>
            </div>
            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>
                        <h2 className="modal-title">Create Wallet</h2>
                        <form onSubmit={handleSubmit}>
                            <div className="form-group field">
                                <input type="input" className="form-field" placeholder="Wallet name" value={walletName} onChange={e => setWalletName(e.target.value)} required/>
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

export default Wallets
