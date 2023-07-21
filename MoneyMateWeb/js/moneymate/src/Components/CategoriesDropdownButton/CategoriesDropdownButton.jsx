import React, { useState, useEffect } from 'react';
import './CategoriesDropdownButton.css';
import CategoryService from "../../Services/CategoryService.jsx";

function CategoriesDropdownButton({ onChange, type }) {
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(null);

    useEffect(() => {
        fetchCategories();
    }, [type]);

    const fetchCategories = async () => {
        try {
            const response = await CategoryService.getAllCategories()
            const userCategories = response.userCategories.categories
            const systemCategories = response.systemCategories.categories
            const allCategories = userCategories.concat(systemCategories)
            if (type === "Wallet") setCategories(allCategories)
            else if (type === "Shared Wallet")  setCategories(systemCategories)
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    };

    const handleCategorySelect = (event) => {
        const selectedOption = event.target.value;
        setSelectedCategory(selectedOption);
        onChange(selectedOption); // Notify the parent component about the selected category
    };

    return (
        <select className="dropdown-select" onChange={handleCategorySelect}>
            <option value="">Select Category</option>

            {categories.map((category) => (
                <option value={category.id} key={category.id}>
                    {category.name}
                </option>
            ))}
        </select>
    );
}

export default CategoriesDropdownButton;
