import React, {useEffect, useState} from 'react';
import '../../App.css'
import './Categories.css';

import CategoryIcon from "../../Components/CategoryIcon/CategoryIcon.jsx";
import CategoryService from "../../Services/CategoryService.jsx";
import CreateButton from "../../Components/IconButtons/CreateButton.jsx";
import {SyncLoader} from "react-spinners";
import {Alert} from "@mui/material";

function Categories() {
    const [userCategories, setUserCategories] = useState([])
    const [systemCategories, setSystemCategories] = useState([])
    const [loading, setLoading] = useState(false)
    const [alert, setAlert] = useState({
        show: false,
        message: '',
        severity: 'success',
    })


    useEffect( () => {
        fetchAllCategories()
    }, []);

    function fetchAllCategories(){
        setLoading(true)
        CategoryService.getAllCategories()
            .then(response => {
                    setUserCategories(response.userCategories.categories)
                    setSystemCategories(response.systemCategories.categories)
                    setLoading(false)
                }
            );
    }

    function fetchUserCategories(){
        setLoading(true)
        CategoryService.getCategoriesOfUser()
            .then(response => {
                    setUserCategories(response.categories)
                    setLoading(false)
            });
    }
    function fetchSystemCategories(){
        CategoryService.getSystemCategories()
            .then(response => setSystemCategories(response.categories));
    }

    async function createCategory(name) {
        try {
            const response = await CategoryService.createCategory(name);
            console.log(response);
            showSuccessAlert('Category created successfully!');
            fetchUserCategories();
        } catch (error) {
            console.error('Error creating category:', error);
            showErrorAlert('Failed to create category. Please try again.');
        }
    }

    async function updateCategory(categoryId, categoryName) {
        try {
            const response = await CategoryService.updateCategory(categoryId, categoryName);
            console.log(response);
            showSuccessAlert('Category updated successfully!');
            fetchUserCategories();
        } catch (error) {
            console.error('Error updating category:', error);
            showErrorAlert('Failed to update category. Please try again.');
        }
    }

    async function deleteCategory(categoryId) {
        try {
            const response = await CategoryService.deleteCategory(categoryId);
            console.log(response);
            showSuccessAlert('Category deleted successfully!');
            fetchUserCategories();
        } catch (error) {
            console.error('Error deleting category:', error);
            showErrorAlert('Failed to delete category. Please try again.');
        }
    }

    function showSuccessAlert(message) {setAlert({show: true, message: message, severity: 'success'})}
    function showErrorAlert(message) {setAlert({ show: true, message: message, severity: 'error'})}
    function closeAlert() {setAlert({show: false, message: '', severity: 'success'})}

    return (
        <div className="bg-container">
            <div className="content-container">
                <h1 className="page-title">Categories</h1>

                <div>
                    <div className="category-container">
                        <p className="list-title">Your Categories:</p>
                        <CreateButton
                            createEntity={createCategory}
                            fetchEntities={fetchUserCategories}
                        />
                    </div>

                    {loading ? (
                            <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                    ) : (
                        userCategories !== null && userCategories.length > 0 ? (
                            <div className="icons-container">
                                {userCategories.map((category, index) => (
                                    <CategoryIcon
                                        key={index}
                                        category={category}
                                        fetchEntities={fetchUserCategories}
                                        updateEntity={updateCategory}
                                        deleteEntity={deleteCategory}
                                        showEditButton={true}
                                    />
                                ))}
                            </div>
                        ) : (
                            <h2>No User Categories found</h2>
                        )
                    )}
                </div>

                <div>
                    <p className="list-title">System Categories:</p>
                    {loading ? (
                            <SyncLoader size={50} color={'#ffffff'} loading={loading} />
                    ) : (
                        <div className="icons-container">
                            {systemCategories.map((category, index) => (
                                <CategoryIcon
                                    key={index}
                                    category={category}
                                    fetchEntities={()=>{}}
                                    updateEntity={()=>{}}
                                    showEditButton={false}
                                />
                            ))}
                        </div>
                    )}
                </div>
            </div>
            {alert.show && (
                <div className="alert-container-categories">
                    <Alert variant="outlined" severity={alert.severity} onClose={closeAlert}>
                        <strong className="error-text">{alert.message}</strong>
                    </Alert>
                </div>
            )}
        </div>
    );
}

export default Categories;