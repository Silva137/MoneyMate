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

export default Statistics;
