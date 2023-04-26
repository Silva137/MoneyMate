import './SideNavBar.css';
import React, { useState } from 'react';
import {FaBars} from 'react-icons/fa';
import {navItems} from "./navData.jsx";
import { NavLink } from 'react-router-dom';

const SideNavBar = ({children}) => {
    const[isOpen ,setIsOpen] = useState(false);
    const toggle = () => setIsOpen (!isOpen);

    return (
        <div className="container">
            <div style={{width: isOpen ? "200px" : "60px"}} className="sidebar">
                <div className="top_section">
                    <img style={{display: isOpen ? "block" : "none"}} src="../../public/logo.png" className="logo"/>
                    <h1 style={{display: isOpen ? "block" : "none"}} className="logo-text">MoneyMate</h1>
                    <div style={{marginLeft: isOpen ? "10px" : "0px"}} className="bars">
                        <FaBars onClick={toggle}/>
                    </div>
                </div>
                {
                    navItems.map((item, index)=>(
                        <NavLink to={item.link} key={index} className="link" activeclassname="active">
                            <div className="icon">{item.icon}</div>
                            <div style={{display: isOpen ? "block" : "none"}} className="icon_text">{item.text}</div>
                        </NavLink>
                    ))
                }
            </div>
            <main>{children}</main>
        </div>
    );
}

export default SideNavBar;
