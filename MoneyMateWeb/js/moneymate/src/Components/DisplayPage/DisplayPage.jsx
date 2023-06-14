import React, {useState} from 'react';

const DisplayPage = ({close, onClickElement}) => {
    const categories = [
        { id: 1, name: 'Category 1' },
        { id: 2, name: 'Category 2' },
        { id: 3, name: 'Category 3' },
    ];

    return (
        <div className="modal-overlay">
            <div className="modal modal-large">
                <button className="close-button" onClick={close}>
                    X
                </button>
                <h2 className="modal-title">Categories</h2>
                <ul className="category-list">
                    {categories.map((category) => (
                        <li key={category.id} className="category-item">
                            {category.name}
                        </li>
                    ))}
                </ul>
            </div>
        </div>

    );
};

export default DisplayPage;
