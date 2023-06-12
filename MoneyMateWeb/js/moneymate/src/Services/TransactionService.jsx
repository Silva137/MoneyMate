import {authHeader} from "./AuthHeader.jsx";
import instance from "./AxiosInterceptor.jsx";

class TransactionService {

    createWallet(name) {
        const form = { name }
        return instance.post("/api/wallets", form,{headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    getPosNegBalanceByCategory(walletId, selectedDates) {
        const params = {
            startDate: selectedDates[0],
            endDate: selectedDates[1]
        };

        return instance.get(`/api/transactions/wallets/${walletId}/categories/posneg/balance`, {
                headers: authHeader(),
                params: params
            })
            .then(response => {
                return response.data;
            });
    }

    getSumBalanceByCategory(walletId, selectedDates) {
        const params = {
            startDate: selectedDates[0],
            endDate: selectedDates[1]
        }
        return instance.get(`/api/transactions/wallets/${walletId}/categories/balance`, {
            headers: authHeader(),
            params: params
        })
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

export default new TransactionService();