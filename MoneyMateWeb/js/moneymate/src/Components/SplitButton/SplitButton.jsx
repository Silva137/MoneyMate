import React, {useContext, useState} from 'react';
import {CgClose} from "react-icons/cg";
import TransactionService from "../../Services/TransactionService.jsx";
import {SessionContext} from "../../Utils/Session.jsx";
import './SplitButton.css'

function SplitButton() {
    const [modal, setModal] = useState(false);
    const { selectedSharedWallet } = useContext(SessionContext)
    const [paymentsToSend, setPaymentsToSend] = useState([]);
    const [paymentsToReceive, setPaymentsToReceive] = useState([]);


    async function handleButtonClick() {
        try {
            setModal(true)
            const response = await TransactionService.getEqualPayments(selectedSharedWallet);
            setPaymentsToSend(response.paymentsToSend);
            setPaymentsToReceive(response.paymentsToReceive)
            console.log(response);
        } catch (error) {
            console.error('Error fetching users of wallet', error);
        }
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }

    return (
        <div>
            <button className="button-split-bills" onClick={handleButtonClick}>
                Split Bills
            </button>

            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>
                        <h2 className="modal-title">Split Bills</h2>

                        <h3 className="modal-subtitle">Payments to receive:</h3>
                        {paymentsToReceive.length === 0 ? (
                            <p>No payments to receive.</p>
                        ) : (
                            <>
                                <div className="user-cards">
                                    {paymentsToReceive.map(payment => (
                                        <div key={payment.user.id} className="user-card">
                                            <div className="user-info">
                                                <div>
                                                    <h3 className="user-name">{payment.user.username}</h3>
                                                    <p className="user-email">{payment.user.email}</p>
                                                </div>
                                                <div className="user-payment">{payment.ammount}€</div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </>
                        )}
                        <h3 className="modal-subtitle">Payments to send:</h3>
                        {paymentsToSend.length === 0 ? (
                            <p>No payments to send.</p>
                        ) : (
                            <>
                                <div className="user-cards">
                                    {paymentsToSend.map(payment => (
                                        <div key={payment.user.id} className="user-card">
                                            <div className="user-info">
                                                <div>
                                                    <h3 className="user-name">{payment.user.username}</h3>
                                                    <p className="user-email">{payment.user.email}</p>
                                                </div>
                                                <div className="user-payment">{payment.ammount}€</div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
}

export default SplitButton;
