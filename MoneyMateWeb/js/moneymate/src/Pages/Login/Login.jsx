import React, {useContext, useEffect, useState} from 'react';
import './Login.css';
import './../../App.css';
import {NavLink, useNavigate} from 'react-router-dom';
import AuthService from "../../Services/AuthService.jsx";
import {SessionContext} from "../../Utils/Session.jsx";
import { Alert} from '@mui/material';

function Login() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('');
    const navigate = useNavigate()
    const { isAuthenticated, setIsAuthenticated, setSelectedStatistic, setSelectedWallet } = useContext(SessionContext);

    console.log("Authenticated", isAuthenticated)

    useEffect(() => {
        if (isAuthenticated) {
            setSelectedStatistic("graphics")
            setSelectedWallet(-1)
            navigate('/profile');
        }
    }, [isAuthenticated]);


    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            const response = await AuthService.login(email, password, setIsAuthenticated)
            console.log(response)
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
        <div className="login-bg-container">
            <div className="login-input-container">
                <div className="logo-image"></div>
                <div>
                    <span className="logo-text-first">Money </span>
                    <span className="logo-text-second">mate</span>
                </div>
                <div className="login-text">Sign in</div>
                <form onSubmit={handleSubmit}>
                    <input onChange={e => setEmail(e.target.value)} type="email" className="input" placeholder="Email" required></input>
                    <input onChange={e => setPassword(e.target.value)} type="password" className="input" placeholder="Password" required></input>
                    <button type="submit" className="login-button">Sign in</button>
                </form>
                <div className="secondary-text" >
                    Don't have an account?
                    <NavLink className="nav-link" to="/users/register"> Register here</NavLink>
                </div>
                {error && (
                    <div className="alert-container">
                        <Alert variant="outlined" severity="error">
                            <strong className="error-text">{error}</strong>
                        </Alert>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Login;
