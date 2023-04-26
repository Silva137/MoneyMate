import {BrowserRouter, Route, Routes} from "react-router-dom"
import Register from "./Pages/Register"
import Login from "./Pages/Login"
import Wallets from "./Pages/Wallets.jsx"
import SideNavBar from "./Components/SideNavBar/SideNavBar.jsx";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <SideNavBar>
                <Routes>
                    <Route path='/wallets' element={<Wallets/>}/>
                    <Route path='/users/login' element={<Login/>}/>
                    <Route path='/users/register' element={<Register/>}/>
                </Routes>
            </SideNavBar>
        </BrowserRouter>
    );
};

export default AppRoutes;
