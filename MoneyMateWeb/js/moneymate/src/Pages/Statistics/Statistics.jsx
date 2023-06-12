import React, { useContext, useEffect, useState } from 'react';
import '../../App.css';
import './Statistics.css';
import PieChart from "../../Components/PieChart/PieChart.jsx";
import ColumnChart from "../../Components/ColumnChart/ColumnChart.jsx";
import WalletService from "../../Services/WalletService.jsx";
import TransactionService from "../../Services/TransactionService.jsx";
import { SessionContext } from "../../Utils/Session.jsx";
import DatePicker from "../../Components/DatePicker/DatePicker.jsx";
import dayjs from "dayjs";
import { SyncLoader } from 'react-spinners';

function Statistics() {
    const [wallets, setWallets] = useState([])
    const [selectedDates, setSelectedDates] = useState([dayjs().startOf('month').format('YYYY-MM-DD'), dayjs().endOf('month').format('YYYY-MM-DD')])
    const { selectedWallet } = useContext(SessionContext)
    const [balance, setBalance] = useState(null)
    const [category, setCategory] = useState(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        fetchPrivateWallets();
    }, [])

    useEffect(() => {
        console.log('Selected WalletId: ' + selectedWallet)
        console.log(selectedDates)
        if (selectedDates.length !== 0) {
            fetchChartsData()
        }
    }, [selectedDates])

    async function fetchPrivateWallets() {
        try {
            const response = await WalletService.getWalletsOfUser()
            setWallets(response.wallets)
        } catch (error) {
            console.error('Error fetching private wallets of user:', error)
        }
    }

    async function fetchChartsData() {
        try {
            setLoading(true)
            const response = await TransactionService.getPosNegBalanceByCategory(selectedWallet, selectedDates)
            const response2 = await TransactionService.getSumBalanceByCategory(selectedWallet, selectedDates)
            console.log(response)
            console.log(response2)
            const sumBalances = response2.balanceList.map(item => item.balance)
            const sumCategories = response2.balanceList.map(item => item.category.name)
            const negBalances = response.neg.balanceList.map(item => Math.abs(item.balance))
            const negCategories = response.neg.balanceList.map(item => item.category.name)
            const posBalances = response.pos.balanceList.map(item => item.balance)
            const posCategories = response.pos.balanceList.map(item => item.category.name)
            setBalance([posBalances, negBalances, sumBalances])
            setCategory([posCategories, negCategories, sumCategories])
        } catch (error) {
            console.error('Error fetching charts data:', error)
        } finally {
            setLoading(false)
        }
    }

    const handleDatePickerChange = (dates) => {
        setSelectedDates(dates)
    }

    return (
        <div>
            <div className="bg-container">
                <div className="content-container">
                    <div className="row">
                        <h1 className="page-title">Statistics</h1>
                        <DatePicker onChange={handleDatePickerChange} />
                    </div>
                </div>
                <div className="content-container-statistics">
                    <div className="chart-row">
                        <div className="card-income">
                            {loading ? (
                                <div className="loader-container">
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                </div>
                            ) : (
                                balance !== null && category !== null && balance[0].length > 0 && category[0].length > 0 ? (
                                    <PieChart balance={balance[0]} category={category[0]} title="Income" />
                                ) : (
                                    <p>No Results Found</p>
                                )
                            )}
                        </div>
                        <div className="card-sum">
                            {loading ? (
                                <div className="loader-container">
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                </div>
                            ) : (
                                balance !== null && category !== null && balance[2].length > 0 && category[2].length > 0 ? (
                                    <ColumnChart balance={balance[2]} category={category[2]} title="Category Balance" />
                                ) : (
                                    <p>No Results Found</p>
                                )
                            )}
                        </div>
                    </div>
                    <div className="card-expense">
                        {loading ? (
                            <div className="loader-container">
                                <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                            </div>
                        ) : (
                            balance !== null && category !== null && balance[1].length > 1 && category[1].length > 1 ? (
                                <PieChart balance={balance[1]} category={category[1]} title="Expenses" />
                            ) : (
                                <p>No Results Found</p>
                            )
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Statistics;
