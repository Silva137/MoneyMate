import axios from "axios";
import {authHeader} from "./AuthHeader.jsx";
import instance from "../interceptors/Axios.jsx";

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
}

export default new UserService();