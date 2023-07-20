import {BrowserRouter, Routes, Route, Navigate, Outlet} from 'react-router-dom';
import Register from "../Pages/Register/Register.jsx"
import Login from "../Pages/Login/Login.jsx"
import Transactions from "../Pages/Transactions/Transactions.jsx"
import Wallets from "../Pages/Wallets/Wallets.jsx"
import SideNavBar from "../Components/SideNavBar/SideNavBar.jsx"
import Profile from "../Pages/Profile/Profile.jsx"
import Categories from "../Pages/Categories/Categories.jsx"
import Statistics from "../Pages/Statistics/Statistics.jsx"
import Overview from "../Pages/Overview/Overview.jsx"
import Logout from "../Pages/Logout/Logout.jsx"
import PrivateRoutes from "./PrivateRoutes.jsx";
import SharedStatistics from "../Pages/SharedStatistics/SharedStatistics.jsx";


const AppRoutes = () => {
    return (
        <BrowserRouter>
            <SideNavBar>
                <Routes>
                    <Route path='/' element={<Login/>}/> //TODO create landing page
                    <Route path='/users/login' element={<Login/>}/>
                    <Route path='/users/register' element={<Register/>}/>
                    <Route element={<PrivateRoutes />}>
                        <Route path='/wallets' element={<Wallets/>}/>
                        <Route path='/profile' element={<Profile/>}/>
                        <Route path='/transactions' element={<Transactions/>}/>
                        <Route path='/categories' element={<Categories/>}/>
                        <Route path='/statistics/wallets/graphics/:walletId' element={<Statistics type="graphics" />} />
                        <Route path='/statistics/wallets/list/:walletId' element={<Statistics type="list" />} />
                        <Route path='/statistics/sharedWallets/graphics/:sharedWalletId' element={<SharedStatistics />} />

                        <Route path='/overview' element={<Overview/>}/>
                        <Route path='/logout' element={<Logout/>}/>
                    </Route>
                </Routes>
            </SideNavBar>
        </BrowserRouter>
    );
};

export default AppRoutes;
