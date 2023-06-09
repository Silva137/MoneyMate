import {authHeader} from "./AuthHeader.jsx";
import instance from "./AxiosInterceptor.jsx";

class WalletService {

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
}

export default new WalletService();