import { DatePicker as DateRangePicker } from 'antd';
import { useState } from 'react';
import moment from 'moment';
const { RangePicker } = DateRangePicker;

function DatePicker() {
    const [dates, setDates] = useState([]);
    console.log(dates);

    const handleDateChange = (dateValues) => {
        if (dateValues && dateValues.length === 2) {
            const formattedDates = dateValues.map((date) => date.format('YYYY-MM-DD'));
            setDates(formattedDates);
        } else {
            setDates([]);
        }
    };

    return (
        <div style={{ margin: 20 }}>
            <RangePicker onChange={handleDateChange} />
        </div>
    );
}

export default DatePicker;
