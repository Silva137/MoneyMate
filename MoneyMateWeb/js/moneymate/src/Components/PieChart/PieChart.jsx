import React, { useRef, useEffect } from 'react';
import ReactApexChart from 'react-apexcharts';
import './PieChart.css';

function PieChart ({ balance, category, title}) {
    const apexChartRef = useRef(null);

    useEffect(() => {
        if (apexChartRef.current) {
            const chartInstance = apexChartRef.current.chart;
            chartInstance.updateOptions(getChartOptions());
            chartInstance.updateSeries(balance);
        }
    }, [balance]);


    const getChartOptions = () => {
        return {
            chart: {
                type: 'donut',
                foreColor: '#f3f3f3',
            },
            title: {
                text: title, // Add your desired title here
                align: 'center',
                offsetX: -110,
                style: {
                    fontSize: '24px',
                    fontWeight: 'bold',
                    fontFamily: "'Poppins', sans-serif",
                },
            },
            fill: {
                type: 'gradient',
            },
            stroke: {
                width: 0,
            },
            legend: {
                position: 'right',
                fontSize: '14px',
                fontWeight: 'bold',
                fontFamily: "'Poppins', sans-serif",
                width: 125,
                offsetX: 0,
                offsetY: 20,
                itemMargin: {
                    horizontal: 10,
                    vertical: 5,
                },
            },
            labels: category,
            dataLabels: {
                enabled: true,
                style: {
                    fontFamily: "'Poppins', sans-serif",
                },
            },
        };
    };

    return (
        <div id="chart">
            <ReactApexChart
                options={getChartOptions()}
                series={balance}
                type="donut"
                ref={apexChartRef}
            />
        </div>
    );
};

export default PieChart;
