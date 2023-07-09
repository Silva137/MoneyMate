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
import DisplayPage from "../../Components/DisplayPage/DisplayPage.jsx";
import WalletSelector from "../../Components/SelectorBox/WalletSelector.jsx";
import {useNavigate, useParams} from 'react-router-dom';
import StatisticsSelector from "../../Components/SelectorBox/StatisticsSelector.jsx";
import GraphicStatistics from "./GraphicStatistics.jsx";
import ListStatistics from "./ListStatistics.jsx";

function Statistics({type}) {
    const [wallets, setWallets] = useState([])
    const [selectedDates, setSelectedDates] = useState([dayjs().startOf('month').format('YYYY-MM-DD'), dayjs().endOf('month').format('YYYY-MM-DD')])
    const { selectedStatistic, setSelectedStatistic} = useContext(SessionContext)
    const { selectedWallet, setSelectedWallet } = useContext(SessionContext);
    const navigate = useNavigate();
    const { walletId } = useParams();

    useEffect( () => {
        fetchPrivateWallets();
    }, []);

    useEffect( () => {
        async function fetch(){
            await setSelectedWallet(walletId); // Graphics Change when this value change
            await setSelectedStatistic(type); // Graphics Change when this value change
        }
        fetch()
    }, [navigate]);

    async function fetchPrivateWallets() {
        try {
            //setIsLoading(true);
            const response = await WalletService.getWalletsOfUser();
            console.log(response)
            setWallets(response.wallets);
        } catch (error) {
            console.error('Error fetching private wallets of user:', error);
        } finally {
            //setIsLoading(false);
        }
    }
    const handleDatePickerChange = (dates) => {
        setSelectedDates(dates)
    }

    const handleWalletChange = async (walletId) => {
        await setSelectedWallet(walletId); // Graphics Change when this value change
        navigate(`/statistics/wallets/${selectedStatistic}/${walletId}`);
    };

    const handleStatisticChange = async (statistic) => {
        await setSelectedStatistic(statistic); // Graphics Change when this value change
        navigate(`/statistics/wallets/${statistic}/${selectedWallet}`);
    };

    return (
        <div>
            <div className="bg-container">
                <div className="content-container">
                    <div className="row">
                        <h1 className="page-title">Statistics</h1>
                        <DatePicker onChange={handleDatePickerChange} />
                        <WalletSelector className="wallet-selector" wallets={wallets} selectedWallet={selectedWallet} handleWalletChange={handleWalletChange}/>
                        <StatisticsSelector className="wallet-selector" selectedStatistic={selectedStatistic} handleStaticChange={handleStatisticChange} />
                    </div>

                </div>
                {selectedStatistic === 'graphics' ? (
                    <GraphicStatistics selectedDates={selectedDates} />
                ) : (
                    <ListStatistics selectedDates={selectedDates} />
                )}
            </div>
        </div>
    );
}
/**
 return (
 <div>
 <GraphicStatistics selectedDates={selectedDates}

 <div className="bg-container">
 <div className="content-container">
 <div className="row">
 <h1 className="page-title">Statistics</h1>
 <DatePicker onChange={handleDatePickerChange} />
 </div>
 <WalletSelector className="wallet-selector" wallets={wallets} />
 <StatisticsSelector className="wallet-selector" />


 </div>
 <div className="content-container-statistics">
 <div className="chart-column">
 <div className="card-income">
 {loading ? (
                                <div className="loader-container">
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                </div>
                            ) : (
                                balance !== null && category !== null && balance[0].length > 0 && category[0].length > 0 ? (
                                    <PieChart balance={balance[0]} category={category[0]} title={`Income: ${sumArray(balance[0])}€`} />
                                ) : (
                                    <p>No Results Found</p>
                                )
                            )}
 </div>
 <div className="card-expense">
 {loading ? (
                                <div className="loader-container">
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                </div>
                            ) : (
                                balance !== null && category !== null && balance[1].length > 1 && category[1].length > 1 ? (
                                    <PieChart balance={balance[1]} category={category[1]} title={`Expenses: -${sumArray(balance[1])}€`} />
                                ) : (
                                    <p>No Results Found</p>
                                )
                            )}
 </div>
 </div>
 <div className="chart-column">
 <div className="card-sum">
 {loading ? (
                                <div className="loader-container">
                                    <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                                </div>
                            ) : (
                                balanceList !== null && balanceList.length > 0? (
                                    <ColumnChart balanceList={balanceList} onClick={(index) => onClick(index)} title={`Balance: ${sumArray(balanceList.map(item => item.balance))}€`} />
                                ) : (
                                    <p>No Results Found</p>
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
 </div>
 );
 */

function sumArray(array){
    return array.reduce((sum, value) => sum + value, 0)
}

export default Statistics;
