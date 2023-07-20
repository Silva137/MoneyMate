import {authHeader} from "./AuthHeader.jsx";
import instance from "./AxiosInterceptor.jsx";

class WalletService {

    /** PW */
    createWallet(name) {
        const form = { name }
        return instance.post("/api/wallets", form,{headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    getWalletsOfUser() {
        return instance.get("/api/wallets", {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    updateWalletName(walletId, name) {
        const form = { name }
        return instance.patch(`/api/wallets/${walletId}`, form, {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    deleteWallet(walletId) {
        return instance.delete(`/api/wallets/${walletId}`, {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    /** SW */

    createSharedWallet(name) {
        const form = { name }
        return instance.post("/api/wallets/shared", form,{headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    getSharedWalletsOfUser() {
        return instance.get("/api/wallets/shared", {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    getUsersOfSW(walletId) {
        return instance.get(`/api/wallets/${walletId}/users`, {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    removeUserFromSW(walletId) {
        return instance.delete(`/api/wallets/${walletId}/removeUser`, {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }
}

export default new WalletService();