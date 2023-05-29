import React from 'react';
import AppRoutes from './Routes/AppRoutes.jsx';
import {SessionProvider} from "./Utils/Session";

const App = () => {
    return (
        <div>
            <SessionProvider>
                <AppRoutes />
            </SessionProvider>
        </div>
    );
};

export default App;
