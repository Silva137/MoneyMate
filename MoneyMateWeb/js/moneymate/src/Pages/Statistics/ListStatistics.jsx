import React, {useContext, useEffect, useState} from 'react';
import '../../App.css';
import './Statistics.css';
import DropdownButton from "../../Components/DropdownButton/DropdownButton.jsx";
import { SessionContext } from "../../Utils/Session.jsx";
import TransactionService from "../../Services/TransactionService.jsx";
import TransactionItem from "../../Components/TransactionItem/TransactionItem.jsx";
import {SyncLoader} from "react-spinners";
import {Alert} from "@mui/material";


function ListStatistics({selectedDates}) {
    const { selectedWallet } = useContext(SessionContext);
    const [sortedBy, setSortedBy] = useState('bydate');
    const [orderBy, setOrderBy] = useState('DESC');
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(false)
    const [alert, setAlert] = useState({
        show: false,
        message: '',
        severity: 'success'
    });


    useEffect(() => {
        fetchTransactions();
    }, [selectedWallet, selectedDates, sortedBy, orderBy]); // Fetch transactions when the sorting option changes

    async function fetchTransactions() {
        try {
            setLoading(true)
            const response = await TransactionService.getAllTransactions(selectedWallet, selectedDates, sortedBy, orderBy);
            setTransactions(response.transactions);
        } catch (error) {
            console.error('Error fetching transactions:', error);
        } finally {
            setLoading(false)
        }
    }

    const sortOptions = [
        { label: 'Sort by Date', value: 'bydate' },
        { label: 'Sort by Price', value: 'byprice' },
    ];

    const orderOptions = [
        { label: 'Ascending', value: 'ASC' },
        { label: 'Descending', value: 'DESC' },
    ];

    function showSuccessAlert(message) {setAlert({show: true, message: message, severity: 'success'})}
    function showErrorAlert(message) {setAlert({ show: true, message: message, severity: 'error' });}
    function closeAlert() {setAlert({show: false, message: '', severity: 'success'})}

    return (
        <div>
            <div className="content-container">
                <div className="row">
                    <DropdownButton options={sortOptions} onChange={(e) => setSortedBy(e)} defaultOption={'bydate'}></DropdownButton>
                    <DropdownButton options={orderOptions} onChange={(e) => setOrderBy(e)} defaultOption={'DESC'}></DropdownButton>
                </div>
                {loading ? (
                    <div className="loader-container" style={{ paddingTop: '225px' }}>
                        <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                    </div>
                ) : (
                    transactions !== null && transactions.length > 0 ? (
                        <div className="transaction-list">
                            {transactions.map((transaction) => (
                                <TransactionItem key={transaction.id} transaction={transaction} getTransactions={fetchTransactions} showSuccess={showSuccessAlert} showError={showErrorAlert}/>
                            ))}
                        </div>
                    ) : (
                        <h2>No transactions found</h2>
                    )
                )}
            </div>
            {alert.show && (
                <div className="alert-container-statistics">
                    <Alert variant="outlined" severity={alert.severity} onClose={closeAlert}>
                        <strong className="error-text">{alert.message}</strong>
                    </Alert>
                </div>
            )}
        </div>
    );
}

export default ListStatistics;
