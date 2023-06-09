import React, { createContext, useContext, useEffect, useState } from "react";

export const SessionContext = createContext({
    isAuthenticated: null,
    setIsAuthenticated: () => {},
    selectedWallet: null,
    setSelectedWallet: () => {},
});

export const SessionProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        return !!localStorage.getItem("user");
    });

    const [selectedWallet, setSelectedWallet] = useState(() => {
        return localStorage.getItem("selectedWallet");
    });

    useEffect(() => {
        localStorage.setItem("selectedWallet", selectedWallet);
    }, [selectedWallet]);


    return (
        <SessionContext.Provider value={{ isAuthenticated, setIsAuthenticated, selectedWallet, setSelectedWallet }}>
            {children}
        </SessionContext.Provider>
    );
};
