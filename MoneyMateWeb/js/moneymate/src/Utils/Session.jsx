import React, { createContext, useContext, useEffect, useState } from "react";

export const SessionContext = createContext({
    isAuthenticated: null,
    setIsAuthenticated: () => {},
    selectedWallet: null,
    setSelectedWallet: () => {},
    selectedStatistic: null,
    setSelectedStatistic: () => {},

});

export const SessionProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        return !!localStorage.getItem("user");
    });

    const [selectedWallet, setSelectedWallet] = useState(() => {
        const wallet = localStorage.getItem("selectedWallet");
        if(wallet == null) localStorage.setItem("selectedWallet", -1);
        return localStorage.getItem("selectedWallet");
    });

    const [selectedStatistic, setSelectedStatistic] = useState(() => {
        const statistic = localStorage.getItem("selectedStatistic");
        if(statistic == null) localStorage.setItem("selectedStatistic", "graphics");
        return localStorage.getItem("selectedStatistic");
    });

    useEffect(() => {
        localStorage.setItem("selectedWallet", selectedWallet);
    }, [selectedWallet]);

    useEffect(() => {
        localStorage.setItem("selectedStatistic", selectedStatistic);
    }, [selectedStatistic]);



    return (
        <SessionContext.Provider value={{ isAuthenticated, setIsAuthenticated, selectedWallet, setSelectedWallet, selectedStatistic, setSelectedStatistic }}>
            {children}
        </SessionContext.Provider>
    );
};
