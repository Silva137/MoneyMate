import { DatePicker as DateRangePicker } from 'antd';
import dayjs from 'dayjs';
import {useEffect, useState} from 'react';
const { RangePicker } = DateRangePicker;
import './DatePicker.css';

function DatePicker({ onChange }) {
    const [dates, setDates] = useState([]);

    const rangePresets = [
        {
            label: 'Today',
            value: [dayjs().startOf('day'), dayjs().endOf('day')],
        },
        {
            label: 'Yesterday',
            value: [dayjs().subtract(1, 'day').startOf('day'), dayjs().subtract(1, 'day').endOf('day')],
        },
        {
            label: 'This Week',
            value: [dayjs().startOf('week'), dayjs().endOf('week')],
        },
        {
            label: 'This Month',
            value: [dayjs().startOf('month'), dayjs().endOf('month')],
        },
        {
            label: 'This Year',
            value: [dayjs().startOf('year'), dayjs().endOf('year')],
        },
    ]

    const handleDateChange = (dateValues) => {
        if (dateValues && dateValues.length === 2) {
            const formattedDates = dateValues.map((date) => date.format('YYYY-MM-DD'))
            setDates(formattedDates);
            onChange(formattedDates); // Call the callback function
        } else {
            setDates([])
            onChange([]);
        }
    }

    return (
        <div className="date-container">
            <RangePicker presets={rangePresets} onChange={handleDateChange} defaultValue={[dayjs().startOf('month'), dayjs().endOf('month')]} />
        </div>
    );
}

export default DatePicker
