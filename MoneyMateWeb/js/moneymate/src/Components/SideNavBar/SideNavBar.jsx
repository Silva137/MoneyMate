import './SideNavBar.css';
import React, {useContext, useEffect, useState} from 'react';
import {navItems} from "./navData.jsx";
import {NavLink, useLocation} from 'react-router-dom';
import {CgClose} from "react-icons/cg";
import {MdDoneOutline} from "react-icons/md";
import TransactionService from "../../services/TransactionService";
import CategoriesDropdownButton from "../CategoriesDropdownButton/CategoriesDropdownButton.jsx";
import WalletsDropdownButton from "../WalletsDropdownButton/WalletsDropdownButton.jsx";
import { SessionContext } from "../../Utils/Session.jsx";
import InviteTypeSelector from "../SelectorBox/InviteTypeSelector.jsx";
import CreateTransactionSelector from "../SelectorBox/CreateTransactionSelector.jsx";

const SideNavBar = ({ children }) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [modal, setModal] = useState(false)
    const [amount, setAmount] = useState(0)
    const [title, setTitle] = useState("")
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [selectedTransactionWallet, setSelectedTransactionWallet] = useState(null);
    const { selectedStatistic, selectedWallet, selectedSharedWallet } = useContext(SessionContext);
    const [selectedWalletType, setSelectedWalletType] = useState("Wallet")


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

    const handleSelectedWalletChange = (wallet) => {
        setSelectedTransactionWallet(wallet)
    }

    const handleWalletTypeChanged = async (type) => {
        setSelectedWalletType(type)
    }

    async function handleCreateTransactionClick(event) {
        event.preventDefault()
        try {
            console.log(selectedCategory)
            const response = await TransactionService.createTransaction(selectedTransactionWallet, selectedCategory, amount, title)
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
                        <img src="../../../logo.png" className="logo" alt="logo" />
                        {isExpanded && (<h1 className="logo-text">MoneyMate</h1>)}
                    </div>
                    {navItems.map((item, index) => (
                        <NavLink
                            to={
                                index === 1
                                    ? `${item.link}/${selectedStatistic}/${selectedWallet}`
                                    : index === 2
                                        ? `${item.link}/graphics/${selectedSharedWallet}`
                                        : item.link
                            }
                            key={index}
                            className={item.link === null ? 'link-add' : 'link'}
                            activeclassname={item.link === null ? 'active' : 'active-add'}
                            onClick={item.link === null ? handleCreateTransactionNavItemClick : null}
                        >
                            <div className="icon">{item.icon}</div>
                            {isExpanded && <div className="icon_text">{item.text}</div>}
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
                        <CreateTransactionSelector handleWalletTypeChanged={handleWalletTypeChanged} selectedWalletType={selectedWalletType}/>
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
                                <CategoriesDropdownButton onChange={handleSelectedCategoryChange} type={selectedWalletType} />
                            </div>
                            <div className="form-group field">
                                <WalletsDropdownButton onChange={handleSelectedWalletChange} type={selectedWalletType}/>
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