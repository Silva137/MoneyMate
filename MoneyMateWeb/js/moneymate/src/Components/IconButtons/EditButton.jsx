import React, {useState} from 'react';
import './IconButtons.css';

import { RiPencilFill } from 'react-icons/ri';
import {GoTrashcan} from "react-icons/go";
import {MdDoneOutline} from "react-icons/md";
import {CgClose} from "react-icons/cg";
import CategoryService from "../../Services/CategoryService.jsx";
import WalletService from "../../Services/WalletService.jsx";

/**
 * This function is used to create a pop-up either for a wallet
 * or a category update
 *
 * @param entity the object to be updated
 * @param fetchEntities function to refresh entities displayed
 * @param updateEntity
 * @param deleteEntity
 * @returns {JSX.Element}
 * @constructor
 */
const EditButton = ({entity, fetchEntities, updateEntity, deleteEntity}) => {
    const [modal, setModal] = useState(false);
    const [entityName, setEntityName] = useState(entity.name);
    const text = "Category" // Todo receive as parameter

    function handleButtonClick() {
        setEntityName(entity.name)
        setModal(true)
    }
    function handleOverlayClick(e) {
        if (e.target.classList.contains('modal-overlay'))
            setModal(false)
    }
    async function handleSaveClick(){
        event.preventDefault();
        await updateEntity(entity.id, entityName)
        await fetchEntities()
        setModal(false);
    }

    async function handleDeleteClick(){
        await deleteEntity(entity.id)
        await fetchEntities()
        setModal(false);
    }

    return (
        <div>
            <button className="edit-button-wallet" onClick={handleButtonClick}>
                <RiPencilFill />
            </button>

            {modal && (
                <div className="modal-overlay" onClick={handleOverlayClick}>
                    <div className="modal">
                        <button className="close-button" onClick={() => setModal(false)}><CgClose/></button>

                        <div>
                            <h2 className="modal-title">Edit {text}</h2>
                            <form onSubmit={handleSaveClick}>
                                <div className="form-group field">
                                    <input type="text" className="form-field" placeholder="${text} name" value={entityName} onChange={e => setEntityName(e.target.value)} required></input>
                                    <label htmlFor="${text} Name" className="form-label">{text} Name</label>
                                </div>
                                <div className="button-container">
                                    <button type="submit" className="save-button"> <MdDoneOutline/> Save</button>
                                    <button className="delete-button" onClick={handleDeleteClick}> <GoTrashcan/> Delete</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            )}
        </div>

    );
};

export default EditButton;


/*
       try {
           const response = await CategoryService.updateCategory(category.id, categoryName);
           console.log(response);
           await fetchCategories()
       } catch (error) {
           console.error('Error updating category name:', error);
       }
       setModal(false);

        */