import React from 'react';
import './Login.css';
import { NavLink } from 'react-router-dom';

function Login() {

    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission here, e.g. by sending a POST request to your API
    };

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
                    <input type="email" autoComplete="off" name="email" className="input" placeholder="Email" required></input>
                    <input type="password" autoComplete="off" name="password" className="input" placeholder="Password" required></input>
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
