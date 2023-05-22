import React, {createContext, useContext, useEffect, useState} from "react";

export const SessionContext = createContext({
    isAuthenticated : null,
    setIsAuthenticated: () => {}
});

export const SessionProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        return !!localStorage.getItem('user')
    })

    return (
        <SessionContext.Provider value={{ isAuthenticated, setIsAuthenticated}}>
            {children}
        </SessionContext.Provider>
    )
}


