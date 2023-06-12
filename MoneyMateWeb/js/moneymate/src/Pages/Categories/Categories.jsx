import React, {useEffect, useState} from 'react';
import '../../App.css'
import './Categories.css';

import CategoryIcon from "../../Components/CategoryIcon/CategoryIcon.jsx";
import WalletService from "../../Services/WalletService.jsx";
import CategoryService from "../../Services/CategoryService.jsx";
import WalletCard from "../../Components/WalletCard/WalletCard.jsx";
import CreateButton from "../../Components/IconButtons/CreateButton.jsx";
import categoryService from "../../Services/CategoryService.jsx";

function Categories() {
    const [userCategories, setUserCategories] = useState([])
    const [systemCategories, setSystemCategories] = useState([])

    const iconsArray = Array.from({ length: 20 });

    useEffect( () => {
        fetchUserCategories()
        fetchSystemCategories()
    }, []);

    function fetchUserCategories(){
        CategoryService.getCategoriesOfUser()
            .then(response => setUserCategories(response.categories));
    }
    function fetchSystemCategories(){
        CategoryService.getSystemCategories()
            .then(response => setSystemCategories(response.categories));
    }

    return (
        <div className="bg-container">
            <div className="content-container">
                <h1 className="page-title">Categories</h1>

                <div>
                    <div className="category-container">
                        <p className="list-title">Your Categories:</p>
                        <CreateButton
                            createEntity={(name) => { return categoryService.createCategory(name); }}
                            fetchEntities={fetchUserCategories}
                        />
                    </div>

                    <div className="icons-container">
                        {userCategories.map((category, index) => (
                            <CategoryIcon
                                key={index}
                                category={category}
                                fetchEntities={fetchUserCategories}
                                updateEntity={(categoryId, categoryName) => { return CategoryService.updateCategory(categoryId, categoryName); }}
                                deleteEntity={(categoryId) => { return CategoryService.deleteCategory(categoryId);}}
                                showEditButton={true}
                            />
                        ))}
                    </div>
                </div>

                <div>
                    <p className="list-title">System Categories:</p>
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
                </div>
            </div>
        </div>
    );
}

export default Categories;

/*
 {iconsArray.map((_, index) => (
                            <CategoryIcon key={index} category={{ name: `Categoria ${index + 1}` }} fetchEntities={fetchUserCategories} showEditButton={true}/>
                        ))}
 */