import React from 'react';
import '../../App.css';
import './Profile.css';
import '../../Components/WalletCard/WalletCard.css'
import Avatar from '@mui/material/Avatar';
import {RiPencilFill} from "react-icons/ri";

function ProfileInfo({loggedUser, handleEditButtonClick, image }) {

    return (
        <div className="content-container">
            <h1 className="page-title">Profile</h1>
            <div className="row">
                <Avatar src={image} sx={{width: 150, height: 150, border: '4px solid #fff'}}/>
                <p className="welcome-text">Hi,<br /> {loggedUser.username}!</p>
            </div>
            <div className="fields-container">
                <div className="form-group field">
                    <div className="form-field" >{loggedUser.username}</div>
                    <label htmlFor="Username" className="form-label">Username</label>
                </div>
                <div className="form-group field">
                    <div className="form-field" >{loggedUser.email}</div>
                    <label htmlFor="Email" className="form-label">Email</label>
                </div>
                <div className="edit-profile-container">
                    <button className="edit-profile"  onClick={handleEditButtonClick}> <RiPencilFill/> Edit Profile</button>
                </div>
            </div>
        </div>
    )
}

export default ProfileInfo;
