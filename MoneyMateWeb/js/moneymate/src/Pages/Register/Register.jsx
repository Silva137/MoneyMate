import React from 'react';
import './Register.css';
import { NavLink } from 'react-router-dom';

function Register() {

    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission here, e.g. by sending a POST request to your API
    };

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
                    <input type="text" autoComplete="off" name="username" className="input" placeholder="Username" required></input>
                    <input type="email" autoComplete="off" name="email" className="input" placeholder="Email" required></input>
                    <input type="password" autoComplete="off" name="password" className="input" placeholder="Password" required></input>
                    <button type="submit" className="register-button">Sign up</button>
                </form>
                <div className="secondary-text" >
                    Already have an account?
                    <NavLink className="nav-link" to="/users/login"> Log in here</NavLink>
                </div>
            </div>
        </div>
    );
}

export default Register;
