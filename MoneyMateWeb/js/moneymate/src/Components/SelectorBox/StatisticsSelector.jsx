import React, {useContext, useEffect, useState} from 'react';
import { MdArrowForwardIos } from 'react-icons/md';
import {MdArrowBackIos} from 'react-icons/md';
import {SessionContext} from "../../Utils/Session.jsx";
import './SelectorBox.css';


function StatisticsSelector({handleStaticChange, selectedStatistic}) {
    const { setSelectedStatistic } = useContext(SessionContext);

    const graphic = "graphics"
    const list = "list"

    useEffect(() => {
        const storedStatistic = localStorage.getItem('selectedStatistic');
        if (storedStatistic) {
            setSelectedStatistic(storedStatistic);
        }
    }, []);

    const handleClick = (e) => { }

    return (
        <div className="wallet-selector">

            <div className="radio-inputs">
                    <label key={graphic} className={`radio ${selectedStatistic === graphic ? 'selected' : ''}`}>
                        <input
                            type="radio"
                            name="statistics"
                            value={graphic}
                            checked={selectedStatistic === graphic}
                            onChange={() => handleStaticChange(graphic)}
                            onClick={handleClick}
                        />
                        <span className="name">Graphic</span>
                    </label>

                    <label key={list} className={`radio ${selectedStatistic === list ? 'selected' : ''}`}>
                        <input
                            type="radio"
                            name="statistics"
                            value={list}
                            checked={selectedStatistic === list}
                            onChange={() => handleStaticChange(list)}
                            onClick={handleClick}
                        />
                        <span className="name">List</span>
                    </label>
            </div>
        </div>
    );
}

export default StatisticsSelector
