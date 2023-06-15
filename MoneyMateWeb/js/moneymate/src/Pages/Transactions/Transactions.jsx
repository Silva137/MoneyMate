import React, {useContext, useEffect, useState} from 'react';
import '../../App.css';
import './Transactions.css';
import DatePicker from "../../Components/DatePicker/DatePicker.jsx";
import DropdownButton from "../../Components/DropdownButton/DropdownButton.jsx";
import { SessionContext } from "../../Utils/Session.jsx";
import dayjs from "dayjs";
import TransactionService from "../../Services/TransactionService.jsx";
import TransactionItem from "../../Components/TransactionItem/TransactionItem.jsx";


function Transactions() {
    const { selectedWallet } = useContext(SessionContext);
    const [selectedDates, setSelectedDates] = useState([
        dayjs().startOf('month').format('YYYY-MM-DD'),
        dayjs().endOf('month').format('YYYY-MM-DD')
    ]);
    const [sortedBy, setSortedBy] = useState('bydate');
    const [orderBy, setOrderBy] = useState('DESC');
    const [transactions, setTransactions] = useState([]);

    const handleDatePickerChange = (dates) => {
        setSelectedDates(dates);
    };


    useEffect(() => {
        fetchTransactions();
    }, [selectedDates, sortedBy, orderBy]); // Fetch transactions when the sorting option changes

    async function fetchTransactions() {
        try {
            const response = await TransactionService.getAllTransactions(
                selectedWallet,
                selectedDates,
                sortedBy,
                orderBy
            );
            setTransactions(response.transactions);
        } catch (error) {
            console.error('Error fetching transactions:', error);
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

    return (
        <div className="bg-container">
            <div className="content-container">
                <h1 className="page-title">Transactions</h1>
                <div className="row">
                    <DatePicker onChange={handleDatePickerChange} />
                    <DropdownButton options={sortOptions} onChange={(e) => setSortedBy(e)} defaultOption={'bydate'}></DropdownButton>
                    <DropdownButton options={orderOptions} onChange={(e) => setOrderBy(e)} defaultOption={'ASC'}></DropdownButton>
                </div>
                <div className="transaction-list">
                    {transactions.map((transaction) => (
                        <TransactionItem key={transaction.id} transaction={transaction} />
                    ))}
                </div>
            </div>
        </div>
    );
}

export default Transactions;
