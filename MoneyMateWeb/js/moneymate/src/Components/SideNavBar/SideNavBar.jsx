import './SideNavBar.css';
import React, {useEffect, useState} from 'react';
import {navItems} from "./navData.jsx";
import {NavLink, useLocation} from 'react-router-dom';

const SideNavBar = ({ children }) => {
    const [isExpanded, setIsExpanded] = useState(false);

    const location = useLocation();
    const hideNavBar =
        location.pathname === "/" || //TODO create landing page
        location.pathname === "/users/login" ||
        location.pathname === "/users/register";

    const handleMouseEnter = () => {setIsExpanded(true)}
    const handleMouseLeave = () => {setIsExpanded(false)}

    useEffect(() => {
        setIsExpanded(false); // Reset the isExpanded state when the navigation occurs
    }, [location.pathname]);

    return (
        <div className="container">
            {hideNavBar ? null : (
                <div className="sidebar" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                    <div className="top_section">
                        <img src="../../logo.png" className="logo" alt="logo"/>
                        {isExpanded && (<h1 className="logo-text">MoneyMate</h1>)}
                    </div>
                    {navItems.map((item, index) => (
                        <NavLink to={item.link} key={index} className="link" activeclassname="active">
                            <div className="icon">{item.icon}</div>
                            {isExpanded && (<div className="icon_text">{item.text}</div>)}
                        </NavLink>
                    ))}
                </div>
            )}
            <main>{children}</main>
        </div>
    );
};


export default SideNavBar;
