import React, { useState } from 'react';
import '../../App.css';
import './Profile.css';
import '../../Components/WalletCard/WalletCard.css'
import Avatar from '@mui/material/Avatar';
import DefaultImage from '../../assets/default-profile-image.png';
import {RiPencilFill} from "react-icons/ri";
import {CgClose} from "react-icons/cg";
import {MdDoneOutline} from "react-icons/md";

function Profile() {
    const [modal, setModal] = useState(false)
    const [image, setImage] = useState(DefaultImage)
    const [username, setUsername] = useState("username")
    const [email, setEmail] = useState("goncalosilva17@gmail.com")


    const handleImageChange = (event) => {
        const file = event.target.files[0]
        const reader = new FileReader()
        reader.onload = (event) => {
            setImage(event.target.result)
        }
        reader.readAsDataURL(file)
    }

    function handleEditButtonClick() {
        setModal(true)
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }

    function handleSaveChangesClick() {
        // API call to update the user's profile
        setModal(false)
    }

    function handleUsernameChange(e) {
        setUsername(e.target.value)
    }

    function handleEmailChange(e) {
        setEmail(e.target.value)
    }

    return (
        <div className="bg-container">
            <div className="content-container">
                <h1 className="page-title">Profile</h1>
                <div className="row">
                    <Avatar src={image} sx={{width: 150, height: 150, border: '4px solid #fff'}}/>
                    <p className="welcome-text">Hi,<br />Walter White!</p>
                    <button className="edit-profile"  onClick={handleEditButtonClick}> <RiPencilFill/> Edit Profile</button>
                </div>
                <div className="fields-container">
                    <div className="form-group field">
                        <input type="text" className="form-field" placeholder="Username" value={username} disabled></input>
                        <label htmlFor="Wallet Name" className="form-label">Wallet Name</label>
                    </div>
                    <div className="form-group field">
                        <input type="text" className="form-field" placeholder="Email" value={email} disabled></input>
                        <label htmlFor="Wallet Name" className="form-label">Wallet Name</label>
                    </div>
                </div>
            </div>
            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>
                        <h2 className="modal-title">Edit Profile</h2>
                        <form onSubmit={handleSaveChangesClick}>
                            <label htmlFor="avatar-input">
                                <Avatar src={image}
                                        sx={{width: 150, height: 150, border: '4px solid #fff', cursor: 'pointer'}}
                                />
                                <input id="avatar-input" type="file" accept="image/*" style={{ display: 'none' }} onChange={handleImageChange}/>
                            </label>
                            <div className="form-group field">
                                <input type="input" className="form-field" placeholder="Username" value={username} onChange={handleUsernameChange}  required></input>
                                <label htmlFor="Username" className="form-label">Username</label>
                            </div>
                            <div className="form-group field">
                                <input type="input" className="form-field" placeholder="Email" value={email} onChange={handleEmailChange}  required></input>
                                <label htmlFor="Email" className="form-label">Email</label>
                            </div>
                            <button type="submit" className="save-button"> <MdDoneOutline/> Save</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Profile;
