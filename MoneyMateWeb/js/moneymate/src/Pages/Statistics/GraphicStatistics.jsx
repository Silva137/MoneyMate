import React, { useContext, useEffect, useState } from 'react';
import '../../App.css';
import './Statistics.css';
import PieChart from "../../Components/PieChart/PieChart.jsx";
import ColumnChart from "../../Components/ColumnChart/ColumnChart.jsx";
import TransactionService from "../../Services/TransactionService.jsx";
import { SessionContext } from "../../Utils/Session.jsx";
import { SyncLoader } from 'react-spinners';
import DisplayPage from "../../Components/DisplayPage/DisplayPage.jsx";


function GraphicStatistics({selectedDates}) {
    const { selectedWallet} = useContext(SessionContext)

    const [balance, setBalance] = useState(null)
    const [category, setCategory] = useState(null)
    const [loading, setLoading] = useState(false)
    const [balanceList, setBalanceList] = useState(null)
    const [modal, setModal] = useState(false);
    const [selectedChartInfo, setSelectedChartInfo] = useState([]);

    useEffect(() => {
        console.log('Selected WalletId: ' + selectedWallet)
        getChartsData()
    }, [selectedWallet, selectedDates])

    async function onClick(index){
        // index represents the column clicked
        const categoryId = balanceList[index].category.id
        const selectedBalance = balanceList[index].balance
        const selectedCategory = balanceList[index].category.name
        const response = await fetchTransactionsByCategory(categoryId)
        setSelectedChartInfo([response.transactions, selectedBalance, selectedCategory]);
        setModal(true)
    }
    async function fetchTransactionsByCategory(categoryId){
        return TransactionService.getTransactionsByCategory(selectedWallet, categoryId, selectedDates)
    }

    async function fetchSumBalanceByCategory(selectedWallet, selectedDates){
        const balanceResponse = await TransactionService.getSumBalanceByCategory(selectedWallet, selectedDates)
        const balanceList = balanceResponse.balanceList
        setBalanceList(balanceList)
        console.log(balanceResponse)
    }

    async function fetchPosNegBalanceByCategory(selectedWallet, selectedDates){
        const posAndNegResponse = await TransactionService.getPosNegBalanceByCategory(selectedWallet, selectedDates)

        const negBalances = posAndNegResponse.neg.balanceList.map(item => Math.abs(item.balance))
        const negCategories = posAndNegResponse.neg.balanceList.map(item => item.category.name)
        const posBalances = posAndNegResponse.pos.balanceList.map(item => item.balance)
        const posCategories = posAndNegResponse.pos.balanceList.map(item => item.category.name)

        setBalance([posBalances, negBalances])
        setCategory([posCategories, negCategories])
        console.log(posAndNegResponse)
    }


    async function getChartsData() {
        try {
            setLoading(true)
            await fetchSumBalanceByCategory(selectedWallet, selectedDates)
            await fetchPosNegBalanceByCategory(selectedWallet, selectedDates)
        } catch (error) {
            console.error('Error fetching charts data:', error)
        } finally {
            setLoading(false)
        }
    }

    return (
        <div>
            <div className="content-container-statistics">
                <div className="chart-column">
                    <div className="card-income">
                        {loading ? (
                            <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                        ) : (
                            balance !== null && category !== null && balance[0].length > 0 && category[0].length > 0 ? (
                                <PieChart balance={balance[0]} category={category[0]} title={`Income: ${sumArray(balance[0])}€`} />
                            ) : (
                                <h2>No Results Found</h2>
                            )
                        )}
                    </div>
                    <div className="card-expense">
                        {loading ? (
                            <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                        ) : (
                            balance !== null && category !== null && balance[1].length > 0 && category[1].length > 0 ? (
                                <PieChart balance={balance[1]} category={category[1]} title={`Expenses: -${sumArray(balance[1])}€`} />
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
                                <ColumnChart balanceList={balanceList} onClick={(index) => onClick(index)} title={`Total Balance: ${sumArray(balanceList.map(item => item.balance))}€`} />
                            ) : (
                                <h2>No Results Found</h2>
                            )
                        )}
                    </div>
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
    );
}

function sumArray(array){
    return array.reduce((sum, value) => sum + value, 0)
}

export default GraphicStatistics;
