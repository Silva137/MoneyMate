import React, { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { SessionContext } from "../Utils/Session.jsx";

const PrivateRoutes = () => {
    const { isAuthenticated } = useContext(SessionContext);
    console.log("Authenticated", isAuthenticated);
    return isAuthenticated ? <Outlet /> : <Navigate to="/users/login" />;
};

export default PrivateRoutes;
