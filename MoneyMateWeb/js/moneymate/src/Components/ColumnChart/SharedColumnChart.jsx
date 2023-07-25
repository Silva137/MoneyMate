import React from 'react';
import ReactApexChart from 'react-apexcharts';

const SharedColumnChart = ({ balanceList, onClick, title }) => {
    function onClickColumn(columnIndex, event, chartContext, config){}

    const seriesData = [
        {
            name: 'Amount',
            data: balanceList.map(item => item.amount),
        },
    ];

    const options = {
        chart: {
            type: 'bar',
            foreColor: '#f3f3f3',
            width: '100%',
            height: '100%',
            toolbar: {
                show: false, // Hide the toolbar with export buttons
            },
            events: {
                dataPointSelection: (event, chartContext, config) => {
                    onClick(config.dataPointIndex)
                    console.log("Handle Click");
                    onClickColumn(config.dataPointIndex, event, chartContext, config)
                },
            },
        },
        title: {
            text: title,
            align: 'center',
            offsetY: 10,
            style: {
                fontSize: '24px',
                fontWeight: 'bold',
                fontFamily: "'Poppins', sans-serif",
            },
        },
        plotOptions: {
            bar: {
                colors: {
                    ranges: [
                        {
                            from: -99999999999,
                            to: -1,
                            color: '#ff5151', // Red color for negative values
                        },
                        {
                            from: 1,
                            to: 99999999999,
                            color: '#77eca6', // Green color for positive values
                        },
                    ],
                },
                columnWidth: '75%',
            },
        },
        dataLabels: {
            enabled: true,
            style: {
                fontFamily: "'Poppins', sans-serif",
            },
        },
        yaxis: {
            labels: {
                formatter: function (y) {
                    return y.toFixed(0) + '€';
                },
                style: {
                    fontSize: '12px',
                    fontWeight: 'bold',
                    fontFamily: "'Poppins', sans-serif",
                },
            },
        },
        xaxis: {
            categories: balanceList.map(item => item.user.username),
            labels: {
                rotate: -90,
                style: {
                    fontSize: '12px',
                    fontWeight: 'bold',
                    fontFamily: "'Poppins', sans-serif",
                },
                offsetY: -5,
            },
        },
        tooltip: {
            theme: "dark",
            style: {
                fontSize: "14px",
                fontFamily: "'Poppins', sans-serif",
            }
        }
    };

    return (
        <div id="chart">
            <ReactApexChart options={options} series={seriesData} type="bar" height={500} />
        </div>
    );
};

export default SharedColumnChart;
