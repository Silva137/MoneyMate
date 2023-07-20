import AuthService from "../../Services/AuthService.jsx";
import { useNavigate } from "react-router-dom";
import {useContext, useEffect} from "react";
import {SessionContext} from "../../Utils/Session.jsx";

function Logout() {
    const {setIsAuthenticated, setLoggedUser } = useContext(SessionContext)
    const {setSelectedWallet, setSelectedSharedWallet} = useContext(SessionContext)
    const navigate = useNavigate();

    useEffect(() => {
        AuthService.logout(setIsAuthenticated, setLoggedUser)
        setSelectedWallet(null)
        setSelectedSharedWallet(null)
        navigate('/');
    }, [navigate]);

    return null;
}

export default Logout;
