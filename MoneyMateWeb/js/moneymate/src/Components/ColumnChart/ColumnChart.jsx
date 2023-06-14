import React from 'react';
import ReactApexChart from 'react-apexcharts';

const ColumnChart = ({ balanceList, onClick, title }) => {

    function onClickColumn(columnIndex, event, chartContext, config){
        console.log(columnIndex)
        console.log(balanceList[columnIndex])
        console.log(event)
        console.log(chartContext)
        console.log(config)
    }

    const seriesData = [
        {
            name: 'Amount',
            data: balanceList.map(item => item.balance),
        },
    ];

    const options = {
        chart: {
            type: 'bar',
            foreColor: '#f3f3f3',
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
            offsetX: -80,
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
                            from: -9999,
                            to: -1,
                            color: '#ff5151', // Red color for negative values
                        },
                        {
                            from: 1,
                            to: 9999,
                            color: '#77eca6', // Green color for positive values
                        },
                    ],
                },
                columnWidth: '65%',
            },
        },
        dataLabels: {
            enabled: true,
            style: {
                fontFamily: "'Poppins', sans-serif",
            },
        },
        yaxis: {
            title: {
                text: 'Total Balance',
                style: {
                    fontSize: '14px',
                    fontWeight: 'bold',
                    fontFamily: "'Poppins', sans-serif",
                },
            },
            labels: {
                formatter: function (y) {
                    return y.toFixed(0) + '$';
                },
                style: {
                    fontSize: '12px',
                    fontWeight: 'bold',
                    fontFamily: "'Poppins', sans-serif",
                },
            },
        },
        xaxis: {
            title: {
                text: 'Categories',
                style: {
                    fontSize: '14px',
                    fontWeight: 'bold',
                    fontFamily: "'Poppins', sans-serif",
                },
            },
            categories: balanceList.map(item => item.category.name),
            labels: {
                rotate: -90,
                style: {
                    fontSize: '12px',
                    fontWeight: 'bold',
                    fontFamily: "'Poppins', sans-serif",
                },
            },
        },
    };

    return (
        <div id="chart">
            <ReactApexChart options={options} series={seriesData} type="bar" height={370} />
        </div>
    );
};

export default ColumnChart;
