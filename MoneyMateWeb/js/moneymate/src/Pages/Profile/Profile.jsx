import React, {useContext, useEffect, useState} from 'react';
import '../../App.css';
import './Profile.css';
import '../../Components/WalletCard/WalletCard.css'
import Avatar from '@mui/material/Avatar';
import DefaultImage from '../../assets/default-profile-image.png';
import {RiPencilFill} from "react-icons/ri";
import {CgClose} from "react-icons/cg";
import {MdDoneOutline} from "react-icons/md";
import UserService from "../../Services/UserService.jsx";

function Profile() {
    const [modal, setModal] = useState(false);
    const [image, setImage] = useState(DefaultImage);
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [loggedUser, setLoggedUser] = useState([])

    useEffect(() => {
        const fetchLoggedUser = async () => {
            try {
                const response = await UserService.getLoggedUser()
                setUsername(response.username);
                setEmail(response.email);
                setLoggedUser(response)
            } catch (error) {
                console.error('Error fetching logged user:', error)
            }
        }

        fetchLoggedUser()
    }, [])

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
        setUsername(loggedUser.username)
        setEmail(loggedUser.email)
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }

    function handleSaveChangesClick(event) {
        event.preventDefault()

        UserService.updateUser(username)
            .then((response) => {
                console.log('Profile updated successfully:', response)
                setLoggedUser(response)
                setModal(false)
            })
            .catch((error) => {
                console.error('Error updating profile:', error)
            })
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
                        <div className="form-field" >{loggedUser.username}</div>
                        <label htmlFor="Username" className="form-label">Username</label>
                    </div>
                    <div className="form-group field">
                        <div className="form-field" >{loggedUser.email}</div>
                        <label htmlFor="Email" className="form-label">Email</label>
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
                                <input type="input" className="form-field" placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} required></input>
                                <label htmlFor="Username" className="form-label">Username</label>
                            </div>
                            <div className="form-group field">
                                <input type="input" className="form-field" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required></input>
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
