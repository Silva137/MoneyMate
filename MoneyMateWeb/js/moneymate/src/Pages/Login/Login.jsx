import React, {useContext, useEffect, useState} from 'react';
import './Login.css';
import {NavLink, useNavigate} from 'react-router-dom';
import AuthService from "../../Services/AuthService.jsx";
import {SessionContext} from "../../Utils/Session.jsx";

function Login() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate()
    const { isAuthenticated, setIsAuthenticated } = useContext(SessionContext);

    console.log("Authenticated", isAuthenticated)

    useEffect(() => {
        if (isAuthenticated) {
            navigate('/profile');
        }
    }, [isAuthenticated]);


    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            const response = await AuthService.login(email, password, setIsAuthenticated)
            console.log(response)
        } catch (error) {
            console.log('Error occurred:', error.response)
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
            </div>
        </div>
    );
}

export default Login;
