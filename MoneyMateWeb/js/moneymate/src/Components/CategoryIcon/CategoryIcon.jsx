import React from 'react';
import './CategoryIcon.css';

import { RiPencilFill } from 'react-icons/ri';
import { AiOutlineWallet } from 'react-icons/ai';
import EditButton from "../IconButtons/EditButton.jsx";
import {BiCategory, FcDonate} from "react-icons/all";

const CategoryIcon = ({ category, fetchEntities, updateEntity, deleteEntity, showEditButton }) => {
    return (
        <div className="category-card">

            <div className="category-icon">
                <BiCategory size={40} />
            </div>

            <div className="category-info">

                <div className="category-name">
                    {category.name}
                </div>

                {showEditButton && (
                    <EditButton entity={category} fetchEntities={fetchEntities} updateEntity={updateEntity} deleteEntity={deleteEntity} />
                )}
            </div>
        </div>
    );
};

export default CategoryIcon;