import React, { createContext, useContext, useEffect, useState } from "react";

export const SessionContext = createContext({
    isAuthenticated: null,
    setIsAuthenticated: () => {},
    selectedWallet: null,
    setSelectedWallet: () => {},
    selectedSharedWallet: null,
    setSelectedSharedWallet: () => {},
    selectedStatistic: null,
    setSelectedStatistic: () => {},

});

export const SessionProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        return !!sessionStorage.getItem("user");
    });

    const [selectedWallet, setSelectedWallet] = useState(() => {
        const wallet = sessionStorage.getItem("selectedWallet");
        if(wallet == null) sessionStorage.setItem("selectedWallet", -1);
        return sessionStorage.getItem("selectedWallet");
    });

    const [selectedSharedWallet, setSelectedSharedWallet] = useState(() => {
        const sharedWallet = sessionStorage.getItem("selectedSharedWallet");
        if(sharedWallet == null) sessionStorage.setItem("selectedSharedWallet", -1);
        return sessionStorage.getItem("selectedSharedWallet");
    });

    const [selectedStatistic, setSelectedStatistic] = useState(() => {
        const statistic = sessionStorage.getItem("selectedStatistic");
        if(statistic == null) sessionStorage.setItem("selectedStatistic", "graphics");
        return sessionStorage.getItem("selectedStatistic");
    });

    useEffect(() => {
        sessionStorage.setItem("selectedWallet", selectedWallet);
        sessionStorage.setItem("selectedSharedWallet", selectedSharedWallet);
    }, [selectedWallet, selectedSharedWallet]);

    useEffect(() => {
        sessionStorage.setItem("selectedStatistic", selectedStatistic);
    }, [selectedStatistic]);



    return (
        <SessionContext.Provider value={{ isAuthenticated, setIsAuthenticated, selectedWallet, setSelectedWallet,selectedSharedWallet, setSelectedSharedWallet, selectedStatistic, setSelectedStatistic }}>
            {children}
        </SessionContext.Provider>
    );
};
