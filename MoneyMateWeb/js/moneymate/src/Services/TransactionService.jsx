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
            console.error('Error getting transactions by category');

        }
    }

    async getTransactionsByUser(shId, userId, selectedDates){
        try{
            const params = {
                startDate: selectedDates[0],
                endDate: selectedDates[1]
            }
            const response = await instance.get(`/api/transactions/wallets/${shId}/users/${userId}`, {
                headers: authHeader(),
                params: params
            })
            return response.data
        }catch (error){
            console.error('Error getting transactions by user');

        }
    }

    async getPosNegBalanceByUser(walletId, selectedDates) {
        const params = {
            startDate: selectedDates[0],
            endDate: selectedDates[1]
        };

        return instance.get(`/api/transactions/wallets/${walletId}/users/posneg/balance`, {
            headers: authHeader(),
            params: params
        })
            .then(response => {
                return response.data;
            });
    }

    async getBalanceByUser(walletId, selectedDates){
        try{
            const params = {
                startDate: selectedDates[0],
                endDate: selectedDates[1]
            }
            const response = await instance.get(`/api/transactions/wallets/${walletId}/users/balance`, {
                headers: authHeader(),
                params: params
            })
            return response.data

        }catch (error){
            console.error('Error getting balance by user' + error);
        }
    }

    async editTransaction(transactionId,categoryId, amount, title){
        const form = { categoryId, amount, title }
        return instance.put(`/api/transactions/${transactionId}`, form, {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    async deleteTransaction(transactionId) {
        return instance.delete(`/api/transactions/${transactionId}`, {headers: authHeader()})
            .then(response => {
                return response.data
            })
    }

    async getEqualPayments(walletId){
        try{
            const response = await instance.get(`/api/transactions/wallets/${walletId}/payments`, {
                headers: authHeader(),
            })
            return response.data

        }catch (error){
            console.error('Error calculating equal payments' + error);
        }
    }
}

export default new TransactionService();