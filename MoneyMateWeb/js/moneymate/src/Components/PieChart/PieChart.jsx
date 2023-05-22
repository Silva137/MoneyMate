import React from 'react';
import ReactApexChart from 'react-apexcharts';
import './PieChart.css';


class PieChart extends React.Component {
    constructor(props) {
        super(props);

        const {balance, category } = this.props;

        this.state = {
            series: balance,
            options: {
                chart: {
                    type: 'donut',
                    foreColor: '#ffffff',
                    events: {
                        dataPointSelection: function(event, chartContext, config) {
                            const categoryClicked = config.dataPointIndex;
                            console.log('Category clicked:', category[categoryClicked]);
                        }.bind(this),
                    },
                },
                responsive: [
                    {
                        breakpoint: 480,
                        options: {
                            chart: {
                                width: 200,
                            },
                            legend: {
                                position: 'bottom',
                            },
                        },
                    },
                ],
                labels: category,
                plotOptions: {
                    pie: {

                    },
                },
            },
        };
    }


        render() {
        return (
            <div id="chart" className="pie-chart-container">
                <ReactApexChart options={this.state.options} series={this.state.series} type="donut" />
            </div>
        );
    }

}

export default PieChart;