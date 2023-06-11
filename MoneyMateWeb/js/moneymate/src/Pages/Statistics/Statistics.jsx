import React, {useContext, useEffect, useState} from 'react';
import '../../App.css'
import './Statistics.css'
import PieChart from "../../Components/PieChart/PieChart.jsx";
import WalletService from "../../Services/WalletService.jsx";
import TransactionService from "../../Services/TransactionService.jsx";
import WalletSelector from "../../Components/WalletSelector/WalletSelector.jsx";
import {SessionContext} from "../../Utils/Session.jsx";
import DatePicker from "../../Components/DatePicker/DatePicker.jsx";

function Statistics() {
    const [wallets, setWallets] = useState([])
    const { selectedWallet, setSelectedWallet } = useContext(SessionContext);
    const balance = [23, 45, 67, 12, 78, 34, 56, 90, 11, 55];
    const category = ['Car', 'Food', 'Travel', 'Entertainment', 'Shopping', 'Health', 'Education', 'Housing', 'Utilities', 'Miscellaneous']

    useEffect( () => {
        fetchPrivateWallets();
        console.log(selectedWallet)
        fetchChartsData();
    }, [])

    async function fetchPrivateWallets() {
        try {
            const response = await WalletService.getWalletsOfUser();
            setWallets(response.wallets);
        } catch (error) {
            console.error('Error fetching private wallets of user:', error);
        }
    }

    async function fetchChartsData() {
        try {
            const response = await TransactionService.getBalanceByCategory(selectedWallet);
            console.log(response)
            //setWallets(response.wallets); //change to setBalance
        } catch (error) {
            console.error('Error fetching charts data:', error);
        }
    }

        const handleDateRangeChange = (ranges) => {
            // Handle the selected date range here
            console.log('Selected Date Range:', ranges);
        }

return (
        <div>
            <div className="bg-container">
                <div className="content-container">
                    <div className="sideByside-container">
                        <h1 className="page-title">Statistics</h1>
                        <DatePicker/>
                        <WalletSelector className="wallet-selector" wallets={wallets} />

                    </div>
                    <div className="card-income">
                        <h2>Income</h2>
                        <PieChart balance={balance} category={category} />
                    </div>
                    <div className="card-expense">
                        <h2>Expenses</h2>
                        <PieChart balance={balance} category={category} />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Statistics;
