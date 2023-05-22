package isel.pt.moneymate.http.utils

/**
 * The Uris of the API
 */

object Uris {

    const val HOME = "/"

    object Authentication {
        const val REGISTER = "/auth/register"
        const val LOGIN = "/auth/login"
        const val LOGOUT = "/auth/logout"
        const val REFRESH_TOKEN = "/auth/refresh-token"
    }

    object Users {
        const val GET_BY_ID = "/users/{id}"
        const val GET_USER = "/user"
        const val GET_USERS = "/users"
        const val UPDATE = "/users"
        const val DELETE = "/users"
    }

    object Category {
        /**
         * Create a new Category:
         * System - Created autmatically for all Users
         * Custom - Created expecifically by a User
         */
        const val CREATE = "/categories"
        const val GET_CATEGORIES = "/categories"
        const val GET_BY_ID = "/categories/{categoryId}"
        const val UPDATE = "/categories/{categoryId}"
        const val DELETE_BY_ID = "/categories/{categoryId}"
    }

    object Wallets {
        /**
         * Create a Wallet:
         * Private - Given a userId and a Name
         * Shared - Given a userId and a Name, after associating otherUsers, userId becomes a "systemUser"
         */
        const val CREATE = "/wallets"

        const val GET_WALLETS_OF_USER = "/wallets"
        const val GET_BY_ID = "/wallets/{walletId}"

        const val UPDATE_NAME = "/wallets/{walletId}"

        const val DELETE_BY_ID = "/wallets/{walletId}"
    }

    object Association{
        const val ASSOCIATE_USER = "/wallets/associate{walletId}/users{userId}"
        const val DESSASOCIATE_USER = "/wallets/dessacoiate{walletId}/users{userId}"

    }

    object Transactions {
        // TODO PASSAR ESTES PEDIDOS PARA WALLET
        const val GET_OVERALL_BALANCE = "/transactions/amount}"
        const val GET_ALL_WALLETS_BALANCE = "/transactions/walletAmounts}"         // Soma de todas as transacoes de cada wallet


        /**Other Requests */
        const val CREATE = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_BY_ID = "/transactions/{transactionId}"
        const val UPDATE = "/transactions/{transactionId}"
        const val DELETE_BY_ID = "/transactions/{transactionId}"
        // TODO PASSAR PARA WALLET
        const val GET_WALLET_BALANCE = "/transactions/wallets/{walletId}/balance"    //should be a Wallet URI?

        /**Requets of transactions Page (Common to both wallet types) */
        const val GET_ALL = "/transactions/wallets/{walletId}"
        const val GET_INCOMES = "/transactions/wallets/{walletId}/incomes"
        const val GET_EXPENSES = "/transactions/wallets/{walletId}/expenses"

        /**Requests of a PW Insights Page */
        const val GET_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_BALANCE_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/balance"


        /**Requests of overViewPage */
        const val GET_ALL_BY_CATEGORY = "/transactions/categories/{categoryId}"
        const val GET_ALL_BALANCE_BY_CATEGORY = "/transactions/categoryAmounts"

        /**Requests of a SW Insights Page */
        const val GET_BY_USER = "/transactions/wallets/{shId}/users/{userId}"
        const val GET_BALANCE_BY_USER = "/transactions/wallets/{shId}/users/userAmounts"

        /**Requests of a Regular Transactions */
        const val GET_PERIODICAL = "/transactions/periodical"
        const val UPDATE_FREQUENCY = "/transactions/{transactionId}/frquency"
        const val UPDATE_AMOUNT = "/transactions/{transactionId}/amount"
    }
}