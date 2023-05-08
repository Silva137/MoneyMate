import {BrowserRouter, Route, Routes} from "react-router-dom"
import Register from "../Pages/Register/Register.jsx"
import Login from "../Pages/Login/Login.jsx"
import Transactions from "../Pages/Transactions/Transactions.jsx"
import Wallets from "../Pages/Wallets/Wallets.jsx"
import SideNavBar from "../Components/SideNavBar/SideNavBar.jsx"
import Profile from "../Pages/Profile/Profile.jsx"
import Categories from "../Pages/Categories/Categories.jsx"
import Statistics from "../Pages/Statistics/Statistics.jsx"
import Overview from "../Pages/Overview/Overview.jsx"

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <SideNavBar>
                <Routes>
                    <Route path='/users/login' element={<Login/>}/>
                    <Route path='/users/register' element={<Register/>}/>
                    <Route path='/wallets' element={<Wallets/>}/>
                    <Route path='/profile' element={<Profile/>}/>
                    <Route path='/transactions' element={<Transactions/>}/>
                    <Route path='/categories' element={<Categories/>}/>
                    <Route path='/statistics' element={<Statistics/>}/>
                    <Route path='/overview' element={<Overview/>}/>
                </Routes>
            </SideNavBar>
        </BrowserRouter>
    );
};

export default AppRoutes;
