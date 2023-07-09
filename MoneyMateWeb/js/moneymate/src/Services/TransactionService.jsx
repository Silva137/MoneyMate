import {authHeader} from "./AuthHeader.jsx";
import instance from "./AxiosInterceptor.jsx";

class TransactionService {

    createTransaction(walletId, categoryId, amount, title) {
        const form = { amount, title }
        return instance.post(`/api/transactions/wallets/${walletId}/categories/${categoryId}`, form,{headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    getAllTransactions(walletId, selectedDates, sortedBy, orderBy) {
        const params = {
            sortedBy: sortedBy,
            orderBy: orderBy,
            startDate: selectedDates[0],
            endDate: selectedDates[1]
        };

        console.log(params.startDate)
        console.log(params.endDate)

        return instance.get(`/api/transactions/wallets/${walletId}`, {
            headers: authHeader(),
            params: params
        })
            .then(response => {
                console.log("response")
                console.log(response)
                return response.data;
            });
    }

    async getPosNegBalanceByCategory(walletId, selectedDates) {

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

    async getTransactionsByCategory(walletId, categoryId, selectedDates){
        try{
            const params = {
                startDate: selectedDates[0],
                endDate: selectedDates[1]
            }
            const response = await instance.get(`/api/transactions/wallets/${walletId}/categories/${categoryId}`, {
                headers: authHeader(),
                params: params
            })

            return response.data

        }catch (error){
            console.error('Error getting transactions');

        }
    }

    deleteWallet(walletId) {
        return instance.delete(`/api/wallets/${walletId}`, {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }
}

export default new TransactionService();