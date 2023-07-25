import React, { useContext, useEffect, useState } from 'react';
import '../../App.css';
import TransactionService from "../../Services/TransactionService.jsx";
import { SessionContext } from "../../Utils/Session.jsx";
import { SyncLoader } from 'react-spinners';
import DisplayPage from "../../Components/DisplayPage/DisplayPage.jsx";
import dayjs from "dayjs";
import WalletService from "../../Services/WalletService.jsx";
import DatePicker from "../../Components/DatePicker/DatePicker.jsx";
import WalletSelector from "../../Components/SelectorBox/WalletSelector.jsx";
import {useNavigate, useParams} from "react-router-dom";
import SharedColumnChart from "../../Components/ColumnChart/SharedColumnChart.jsx";
import SharedPieChart from "../../Components/PieChart/SharedPieChart.jsx";
import SharedWalletSelector from "../../Components/SelectorBox/SharedWalletSelector.jsx";
import SplitButton from "../../Components/SplitButton/SplitButton.jsx";

function SharedStatistics() {
    const { selectedSharedWallet, setSelectedSharedWallet} = useContext(SessionContext)
    const { selectedStatistic, setSelectedStatistic} = useContext(SessionContext)
    const [sharedWallets, setSharedWallets] = useState([])
    const [selectedDates, setSelectedDates] = useState([dayjs().startOf('month').format('YYYY-MM-DD'), dayjs().endOf('month').format('YYYY-MM-DD')])
    const [balance, setBalance] = useState(null)
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(false)
    const [balanceList, setBalanceList] = useState(null)
    const [modal, setModal] = useState(false);
    const [selectedChartInfo, setSelectedChartInfo] = useState([]);
    const { sharedWalletId } = useParams();
    const navigate = useNavigate();


    useEffect( () => {
        async function fetch(){
            await setSelectedSharedWallet(sharedWalletId); // Graphics Change when this value change
            await setSelectedStatistic("graphics"); // Graphics Change when this value change
        }
        fetch()
    }, [navigate]);



    useEffect(() => {
        async function fetchData(){
            console.log('Selected Shared WalletId: ' + selectedSharedWallet)
            await fetchSharedWallets()
            await getChartsData()
        }
        fetchData()
    }, [selectedSharedWallet, selectedDates])

    async function onClick(index){
        // index represents the column clicked
        const userId = balanceList[index].user.id  // TODO ??????
        const selectedBalance = balanceList[index].amount
        const selectedUser = balanceList[index].user.username
        const response = await TransactionService.getTransactionsByUser(selectedSharedWallet, userId, selectedDates)
        setSelectedChartInfo([response.transactions, selectedBalance, selectedUser]);
        setModal(true)
    }

    async function fetchSharedWallets() {
        try {
            const response = await WalletService.getSharedWalletsOfUser();
            console.log(response)
            setSharedWallets(response.wallets);
        } catch (error) {
            console.error('Error fetching private wallets of user:', error);
        }
    }

    async function getChartsData() {
        try {
            setLoading(true)
            await fetchBalanceByUser(selectedSharedWallet, selectedDates)
            await fetchPosNegBalanceByUser(selectedSharedWallet, selectedDates)
        } catch (error) {
            console.error('Error fetching charts data:', error)
        } finally {
            setLoading(false)
        }
    }

    async function fetchBalanceByUser(){
        const balanceResponse = await TransactionService.getBalanceByUser(selectedSharedWallet, selectedDates)
        const balanceList = balanceResponse.balanceList
        setBalanceList(balanceList)
        console.log(balanceResponse)
    }

    async function fetchPosNegBalanceByUser(){
        const posAndNegResponse = await TransactionService.getPosNegBalanceByUser(selectedSharedWallet, selectedDates)

        const negBalances = posAndNegResponse.neg.balanceList.map(item => Math.abs(item.amount))
        const negUsers = posAndNegResponse.neg.balanceList.map(item => item.user.username)
        const posBalances = posAndNegResponse.pos.balanceList.map(item => item.amount)
        const posUsers = posAndNegResponse.pos.balanceList.map(item => item.user.username)

        setBalance([posBalances, negBalances])
        setUser([posUsers, negUsers])
        console.log(posAndNegResponse)
    }

    const handleDatePickerChange = (dates) => {
        setSelectedDates(dates)
    }

    const handleSharedWalletChange = async (walletId) => {
        await setSelectedSharedWallet(walletId); // Graphics Change when this value change
        navigate(`/statistics/sharedWallets/${selectedStatistic}/${walletId}`);
    };


    return (
        <div>
            <div className="bg-container">
                <div className="content-container">
                    <div className="row">
                        <h1 className="page-title">Shared Statistics</h1>
                        <DatePicker onChange={handleDatePickerChange} />
                        <SharedWalletSelector className="wallet-selector" wallets={sharedWallets} selectedWallet={selectedSharedWallet} handleWalletChange={handleSharedWalletChange}/>
                    </div>

                </div>
                <div>
                    <div className="content-container-statistics">
                        <div className="chart-column">
                            <div className="card-income">
                                {loading ? (
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                ) : (
                                    balance !== null && user !== null && balance[0].length > 0 && user[0].length > 0 ? (
                                        <SharedPieChart balance={balance[0]} user={user[0]} title={`Income: ${sumArray(balance[0])}€`} />
                                    ) : (
                                        <h2>No Results Found</h2>
                                    )
                                )}
                            </div>
                            <div className="card-expense">
                                {loading ? (
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                ) : (
                                    balance !== null && user !== null && balance[1].length > 0 && user[1].length > 0 ? (
                                        <SharedPieChart balance={balance[1]} user={user[1]} title={`Expenses: -${sumArray(balance[1])}€`} />
                                    ) : (
                                        <h2>No Results Found</h2>
                                    )
                                )}
                            </div>
                        </div>
                        <div className="chart-column">
                            <div className="card-sum">
                                {loading ? (
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                ) : (
                                    balanceList !== null && balanceList.length > 0? (
                                        <SharedColumnChart balanceList={balanceList} onClick={(index) => onClick(index)} title={`Total Balance: ${sumArray(balanceList.map(item => item.amount))}€`} />
                                    ) : (
                                        <h2>No Results Found</h2>
                                    )
                                )}
                            </div>
                            <SplitButton/>
                        </div>
                    </div>

                    {modal && (
                        <DisplayPage
                            transactions={selectedChartInfo[0]}
                            balance={selectedChartInfo[1]}
                            categoryName={selectedChartInfo[2]}
                            close={() => setModal(false)}
                            onClickElement={() => {}}
                        />
                    )}
                </div>
            </div>
        </div>
    );
}

function sumArray(array){
    return array.reduce((sum, value) => sum + value, 0)
}

export default SharedStatistics;
