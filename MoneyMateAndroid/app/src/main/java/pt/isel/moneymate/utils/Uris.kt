package pt.isel.moneymate.utils

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

        const val ASSOCIATE_USER = "/wallets/associate{walletId}/users{userId}"
        const val DESSASOCIATE_USER = "/wallets/dessacoiate{walletId}/users{userId}"
    }

    object Transactions {
        /**Other Requests */
        const val CREATE = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_BY_ID = "/transactions/{transactionId}"
        const val UPDATE = "/transactions/{transactionId}"
        const val DELETE_BY_ID = "/transactions/{transactionId}"

        /**Requets of transactions Page (Common to both wallet types) */
        const val GET_WALLET_TRANSACTIONS_SORTED_BY = "/transactions/wallets/{walletId}"
        const val GET_WALLET_BALANCE = "/transactions/wallets/{walletId}/balance"    //should be a Wallet URI?

        /**Requests of a PW Insights Page */
        const val GET_PW_TRANSACTIONS_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_PW_CATEGORIES_BALANCE = "/transactions/wallets/{walletId}/categories/balance"

        /**Requests of a SW Insights Page */
        const val GET_SW_TRANSACTIONS_BY_USER = "/transactions/wallets/{shId}/users/{userId}"
        const val GET_SW_USERS_BALANCE = "/transactions/wallets/{shId}/users/userAmounts"

        /**Requests of overViewPage */
        const val GET_AMOUNT_FROM_WALLETS = "/transactions/amount}"         // Somas do lucro e da despesa
        const val GET_ALL_GIVEN_CATEGORY_BY_DATE = "/transactions/categories/{categoryId}"
        const val GET_AMOUNTS_BY_CATEGORY = "/transactions/categoryAmounts"
        const val GET_AMOUNT_BY_WALLETS = "/transactions/walletAmounts}"         // Soma de todas as transacoes de cada wallet

        const val GET_ALL_FROM_ASSOCIATION = "/transactions/wallets/{walletId}"
    }
}