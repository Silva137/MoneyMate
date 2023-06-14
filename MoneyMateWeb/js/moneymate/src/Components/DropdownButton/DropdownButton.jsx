import React, { useState } from 'react';
import './DropdownButton.css';
function DropdownButton({ options, onChange, defaultOption }) {
    const [selectedOption, setSelectedOption] = useState(defaultOption);

    const handleOptionSelect = (event) => {
        const selectedValue = event.target.value;
        console.log(selectedValue)
        setSelectedOption(selectedValue);
        onChange(selectedValue); // Notify the parent component about the selected option
    };

    return (
        <select className="dropdown-select" onChange={handleOptionSelect} value={selectedOption}>
            <option value="">Select Option</option>

            {options.map((option) => (
                <option value={option.value} key={option.value}>
                    {option.label}
                </option>
            ))}
        </select>
    );
}

export default DropdownButton;
