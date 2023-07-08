import {authHeader} from "./AuthHeader.jsx";
import instance from "./AxiosInterceptor.jsx";

class UserService {

    updateUser(username) {
        const form = { username }
        return instance.patch("/api/users", form,{headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    getLoggedUser() {
        return instance.get("/api/user", {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    getUsers(){
        return instance.get("/api/users", {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }
}

export default new UserService();