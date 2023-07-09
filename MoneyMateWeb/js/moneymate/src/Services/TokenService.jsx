const getLocalRefreshToken = () => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    return user?.refresh_token;
};

const getLocalAccessToken = () => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    return user?.accessToken;
};

const updateNewAccessToken = (token) => {
    let user = JSON.parse(sessionStorage.getItem("user"));
    user.accessToken = token;
    sessionStorage.setItem("user", JSON.stringify(user));
};

const getUser = () => {
    return JSON.parse(sessionStorage.getItem("user"));
};

const setUser = (user) => {
    sessionStorage.setItem("user", JSON.stringify(user));
};

const removeUser = () => {
    sessionStorage.clear()
};

const TokenService = {
    getLocalRefreshToken,
    getLocalAccessToken,
    updateNewAccessToken,
    getUser,
    setUser,
    removeUser,
};

export default TokenService;