import axios from "axios";
import TokenService from "./TokenService.jsx";

import { useNavigate } from "react-router-dom";

const instance = axios.create({
    headers: {
        "Content-Type": "application/json",
    },
})


// Before making request, do the following
instance.interceptors.request.use(
    (config) => {
        // console.log("getLocalAccessToken", TokenService.getLocalAccessToken());
        const token = TokenService.getLocalAccessToken()
        if (token) {
            config.headers["Authorization"] = 'Bearer ' + token
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)



//TODO when refresh token expires, doesnt go directly to login page, have to refresh page first
instance.interceptors.response.use(
    (res) => {
        return res
    },
    async (err) => {
        const originalConfig = err.config

        if (err.response) {
            if ((err.response.status === 401) && !originalConfig._retry) {  // access token expired

                // handle infinite loop
                originalConfig._retry = true

                const refreshToken = TokenService.getLocalRefreshToken()
                console.log("refresh", refreshToken);
                try {

                    const rs = await instance.post("/api/auth/refresh-token", { refreshToken })

                    console.log("response", rs)

                    const accessToken  = rs.data.access_token

                    console.log("accessToken generated", accessToken)
                    TokenService.updateNewAccessToken(accessToken)

                    return instance(originalConfig)
                } catch (_error) {
                    console.log("inside error")
                    TokenService.removeUser()
                    return Promise.reject(_error)
                }
            }
            else if (err.response.status === 403) {   // Refresh token expired

                console.log("Refresh token expired");
                TokenService.removeUser();
                navigateToLogout();
            }
        }
        return Promise.reject(err)
    }
)

export default instance

 const navigateToLogout = () => {
    const navigate = useNavigate();
    navigate("/logout");
}