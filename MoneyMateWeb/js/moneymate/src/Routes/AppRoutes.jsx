import {BrowserRouter, Route, Routes} from "react-router-dom"
import Register from "../Pages/Register/Register.jsx"
import Login from "../Pages/Login/Login.jsx"
import Wallets from "../Pages/Wallets.jsx"
import SideNavBar from "../Components/SideNavBar/SideNavBar.jsx";
import Profile from "../Pages/Profile.jsx";
import Transactions from "../Pages/Transactions.jsx";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <SideNavBar>
                <Routes>
                    <Route path='/users/login' element={<Login/>}/>
                    <Route path='/users/register' element={<Register/>}/>
                    <Route path='/wallets' element={<Wallets/>}/>
                    <Route path='/users/profile' element={<Profile/>}/>
                    <Route path='/transactions' element={<Transactions/>}/>
                </Routes>
            </SideNavBar>
        </BrowserRouter>
    );
};

export default AppRoutes;
