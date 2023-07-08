import React, { createContext, useContext, useEffect, useState } from "react";

export const SessionContext = createContext({
    isAuthenticated: null,
    setIsAuthenticated: () => {},
    selectedWallet: -1,
    setSelectedWallet: () => {},
    selectedStatistic: "list",
    setSelectedStatistic: () => {},

});

export const SessionProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        return !!localStorage.getItem("user");
    });

    const [selectedWallet, setSelectedWallet] = useState(() => {
        return localStorage.getItem("selectedWallet");
    });

    const [selectedStatistic, setSelectedStatistic] = useState(() => {
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
