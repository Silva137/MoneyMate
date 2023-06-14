import './SideNavBar.css';
import React, {useContext, useEffect, useState} from 'react';
import {navItems} from "./navData.jsx";
import {NavLink, useLocation} from 'react-router-dom';
import {CgClose} from "react-icons/cg";
import {MdDoneOutline} from "react-icons/md";
import TransactionService from "../../services/TransactionService";
import {SessionContext} from "../../Utils/Session.jsx";
import CategoriesDropdownButton from "../CategoriesDropdownButton/CategoriesDropdownButton.jsx";

const SideNavBar = ({ children }) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [modal, setModal] = useState(false)
    const [amount, setAmount] = useState(0)
    const [title, setTitle] = useState("")
    const [selectedCategory, setSelectedCategory] = useState(null);
    const {selectedWallet} = useContext(SessionContext)


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


    function handleCreateTransactionNavItemClick() {
        setModal(true)
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }

    const handleSelectedCategoryChange = (category) => {
        setSelectedCategory(category)
    }

    async function handleCreateTransactionClick(event) {
        // Api call to create a new transaction
        event.preventDefault()
        try {
            console.log(selectedCategory)
            const response = await TransactionService.createTransaction(selectedWallet, selectedCategory, amount, title)
            console.log(response)
        } catch (error) {
            console.error('Error creating a new transaction:', error)
        }
        setModal(false)
    }

    return (
        <div className="container">
            {hideNavBar ? null : (
                <div className={`sidebar ${isExpanded ? "expanded" : ""}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                    <div className="top_section">
                        <img src="../../logo.png" className="logo" alt="logo" />
                        {isExpanded && (<h1 className="logo-text">MoneyMate</h1>)}
                    </div>
                    {navItems.map((item, index) => (
                        <NavLink
                            to={item.link}
                            key={index}
                            className={index === 3 ? "link-add" : "link"}
                            activeclassname={index === 3 ? "active" : "active-add"}
                            onClick={index === 3 ? handleCreateTransactionNavItemClick : null}
                        >
                            <div className="icon">{item.icon}</div>
                            {isExpanded && (<div className="icon_text">{item.text}</div>)}
                        </NavLink>
                    ))}
                </div>
            )}
            <main>{children}</main>
            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}>
                            <CgClose />
                        </button>
                        <h2 className="modal-title">Create New Transaction</h2>
                        <form onSubmit={handleCreateTransactionClick}>
                            <div className="form-group field">
                                <input type="text" className="form-field" placeholder="Transaction title" value={title} onChange={(e) => setTitle(e.target.value)} required />
                                <label htmlFor="Title" className="form-label">
                                    Title
                                </label>
                            </div>
                            <div className="form-group field">
                                <input type="number" className="form-field" placeholder="Transaction amount" value={amount} onChange={(e) => setAmount(e.target.value)} required />
                                <label htmlFor="Amount" className="form-label">
                                    Amount
                                </label>
                            </div>
                            <div className="form-group field">
                                <CategoriesDropdownButton onChange={handleSelectedCategoryChange} />
                            </div>
                            <button type="submit" className="save-button">
                                <MdDoneOutline /> Create Transaction
                            </button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};


export default SideNavBar;