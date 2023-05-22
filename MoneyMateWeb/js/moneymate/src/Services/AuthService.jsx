import axios from "axios";
import TokenService from "./TokenService";

class AuthService {

    register(username, email, password) {
        const form = { username, email, password }
        return axios.post('/api/auth/register', form)
            .then(response => {
                return response.data
            })
    }

    login(email, password, setIsAuthenticated) {
        const form = { email, password }
        return axios
            .post("/api/auth/login", form)
            .then(response => {
                if (response.data.access_token) {
                    TokenService.setUser(response.data)
                    setIsAuthenticated(true)
                }
                return response.data
            })
    }

    logout(setIsAuthenticated) {
        TokenService.removeUser()
        setIsAuthenticated(false)
    }

    getCurrentUser() {
        return TokenService.getUser()
    }
}

export default new AuthService();