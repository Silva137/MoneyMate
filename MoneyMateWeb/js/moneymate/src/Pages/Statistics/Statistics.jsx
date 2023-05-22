import React, { useState, useEffect } from 'react';
import '../../App.css'
import PieChart from "../../Components/PieChart/PieChart.jsx";

function Statistics() {

const balance = [23, 45, 67, 12, 78, 34, 56, 90, 11, 55];
const category = ['Car', 'Food', 'Travel', 'Entertainment', 'Shopping', 'Health', 'Education', 'Housing', 'Utilities', 'Miscellaneous']

return (
        <div>
            <div className="bg-container">
                <div className="content-container">
                    <h1 className="page-title">Statistics</h1>
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
