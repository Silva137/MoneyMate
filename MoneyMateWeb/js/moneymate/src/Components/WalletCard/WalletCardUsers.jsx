import React, {useEffect, useState} from 'react';
import './WalletCard.css';
import { CgClose } from "react-icons/cg";
import WalletService from "../../Services/WalletService.jsx";
import Avatar from "@mui/material/Avatar";
import DefaultImage from '../../assets/default-profile-image.png';

function WalletCardUsers({walletId, walletName, onModalClose}) {
    const [users, setUsers] = useState([])

    useEffect(() => {
        fetchUsersOfWallet()
    }, [])
    function privateHandleModalClose(e) {
        if (e.target.classList.contains('modal-overlay'))
            onModalClose(e)
    }

    async function fetchUsersOfWallet() {
        try {
            const response = await WalletService.getUsersOfSW(walletId);
            setUsers(response.users);
            console.log("response.users");
            console.log(response.users);
        } catch (error) {
            console.error('Error fetching users of wallet', error);
        }
    }

    return (
        <div className="modal-overlay" onClick={privateHandleModalClose}>
            <div className="modal">
                <button className="close-button" onClick={onModalClose}>
                    <CgClose />
                </button>
                <h2 className="modal-title-1">Members Of Wallet</h2>
                <h2 className="modal-title-2">{walletName}</h2>

                <div className="user-cards">
                    {users.map(user => (
                        <div key={user.id} className="user-card">
                            <div className="user-info">
                                <div>
                                    <h3 className="user-name">{user.username}</h3>
                                    <p className="user-email">{user.email}</p>
                                </div>
                                <div className="user-avatar">
                                    <Avatar
                                        src={DefaultImage}
                                        sx={{ width: 50, height: 50, border: '4px solid #fff' }}
                                    />
                                </div>

                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );

}

export default WalletCardUsers
