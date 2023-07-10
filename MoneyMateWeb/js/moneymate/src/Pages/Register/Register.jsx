import React, {useState} from 'react';
import './Register.css';
import {NavLink, useNavigate} from 'react-router-dom';
import AuthService from "../../Services/AuthService.jsx";
import {Alert} from "@mui/material";


function Register() {
    const [username, setUsername] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('');

    const navigate = useNavigate()

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await AuthService.register(username, email, password)
            navigate('/users/login')
        } catch (error) {
            if (error.response) {
                console.log('Error occurred:', error.response)
                setError(error.response.data.name);
            } else {
                setError('An error occurred. Please try again.');
            }
        }
    }

    return (
        <div className="register-bg-container">
            <div className="register-input-container">
                <div className="logo-image"></div>
                <div>
                    <span className="logo-text-first">Money </span>
                    <span className="logo-text-second">mate</span>
                </div>
                <div className="register-text">Sign up</div>
                <form onSubmit={handleSubmit}>
                    <input value={username} onChange={e => setUsername(e.target.value)} type="text" className="input" placeholder="Username" required></input>
                    <input value={email} onChange={e => setEmail(e.target.value)} type="email" className="input" placeholder="Email" required></input>
                    <input value={password} onChange={e => setPassword(e.target.value)} type="password" className="input" placeholder="Password" required></input>
                    <button type="submit" className="register-button">Sign up</button>
                </form>
                <div className="secondary-text" >
                    Already have an account?
                    <NavLink className="nav-link" to="/users/login"> Log in here</NavLink>
                </div>
                {error && (
                    <div className="alert-container">
                        <Alert variant="outlined" severity="error"><strong className="error-text">{error}</strong></Alert>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Register;
