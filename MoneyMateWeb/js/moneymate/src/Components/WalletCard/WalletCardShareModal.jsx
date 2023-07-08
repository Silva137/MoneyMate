import React, {useContext, useState} from 'react';
import './WalletCard.css';
import { RiPencilFill } from "react-icons/ri";
import { GoTrashcan } from "react-icons/go";
import { MdDoneOutline } from "react-icons/md";
import { CgClose } from "react-icons/cg";
import WalletService from "../../Services/WalletService.jsx";
import {FaChartPie} from "react-icons/fa";
import {useNavigate} from "react-router-dom";
import {SessionContext} from "../../Utils/Session.jsx";
import InviteService from "../../Services/InviteService.jsx";
import CategoriesDropdownButton from "../CategoriesDropdownButton/CategoriesDropdownButton.jsx";

function WalletCardShareModal({userName, onModalClose, onSendInviteClick, onChange}) {

    function privateHandleModalClose(e) {
        if (e.target.classList.contains('modal-overlay'))
            onModalClose(e)
    }

    return(
        <div className="modal-overlay" onClick={privateHandleModalClose}>
            <div className="modal">
                <button className="close-button" onClick={onModalClose}><CgClose/></button>
                <h2 className="modal-title">Share Wallet</h2>
                <form onSubmit={onSendInviteClick}>
                    <div className="form-group field">
                        <input
                            type="text"
                            className="form-field"
                            placeholder="User name"
                            value={userName}
                            onChange={e => onChange(e)}
                            required>
                        </input>
                        <label htmlFor="User Name" className="form-label">User Name</label>
                    </div>

                    <div className="button-container">
                        <button type="submit" className="save-button"> <MdDoneOutline/> Send</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default WalletCardShareModal
