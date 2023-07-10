import React, {useState} from 'react';
import './IconButtons.css';

import { RiPencilFill } from 'react-icons/ri';
import {GoTrashcan} from "react-icons/go";
import {MdDoneOutline} from "react-icons/md";
import {CgClose} from "react-icons/cg";
import CategoryService from "../../Services/CategoryService.jsx";
import WalletService from "../../Services/WalletService.jsx";
import EditButton from "./EditButton.jsx";
import {HiPlus} from "react-icons/hi";


const createButton = ({createEntity, fetchEntities}) => {
    const [modal, setModal] = useState(false);
    const [entityName, setEntityName] = useState('');

    function handleButtonClick() {
        setModal(true)
    }

    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }

    async function handleSaveClick(event) {
        event.preventDefault()
        await createEntity(entityName)
        await fetchEntities()
        setModal(false)
    }

    return (
        <div>
            <button
                className="round-button-add" onClick={handleButtonClick}> +
            </button>
            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>
                        <h2 className="modal-title">Create Category</h2>
                        <form onSubmit={handleSaveClick}>
                            <div className="form-group field">
                                <input type="input" className="form-field" placeholder="Category name"  onChange={e => setEntityName(e.target.value)} required/>
                                <label htmlFor="Category Name" className="form-label">Category Name</label>
                            </div>
                            <button type="submit" className="save-button"> <MdDoneOutline/> Save</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default createButton;
