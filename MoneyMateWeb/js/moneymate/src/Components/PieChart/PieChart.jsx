import React from 'react';
import ReactApexChart from 'react-apexcharts';
import './PieChart.css';

class PieChart extends React.Component {
    constructor(props) {
        super(props);
        const { balance, category } = this.props;

        const dataPointSelectionHandler = (event, chartContext, config) => {
            const categoryClicked = config.dataPointIndex;
            console.log('Category clicked:', category[categoryClicked]);
        };

        const chartOptions = {
            type: 'donut',
            foreColor: '#f3f3f3',
            events: {
                dataPointSelection: dataPointSelectionHandler,
            },
        };

        const fillOptions = {
            type: 'gradient',
        };

        const strokeOptions = {
            width: 0,
        };

        const legendOptions = {
            position: 'right',
            fontSize: '14px',
            offsetY: -25,
            offsetX: 0,
            itemMargin: {
                horizontal: 10,
                vertical: 5,
            },
        };

        const dataLabelsOptions = {
            enabled: true,
            style: {
                fontFamily: "'Poppins', sans-serif",
            },
        };

        const options = {
            chart: chartOptions,
            fill: fillOptions,
            stroke: strokeOptions,
            legend: legendOptions,
            labels: category,
            dataLabels: dataLabelsOptions,
        };

        this.state = {
            series: balance,
            options: options,
        };
    }

    render() {
        return (
            <div id="chart">
                <ReactApexChart options={this.state.options} series={this.state.series} type="donut" />
            </div>
        );
    }
}

export default PieChart;
