package isel.pt.moneymate.utils

/**
 * The Uris of the API
 */

object Uris {

    const val HOME = "/"

    object Authentication {
        const val REGISTER = "/auth/register"
        const val LOGIN = "/auth/login"
    }

    object Users {
        const val UPDATE = "/users"
        const val DELETE = "/users"
        const val GET_BY_ID = "/users/{id}"
        const val GET_ALL_USERS = "/users"
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

        /**
         * Other Requests
         */
        const val CREATE = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_BY_ID = "/transactions/{transactionId}"
        const val UPDATE = "/transactions/{transactionId}"
        const val DELETE_BY_ID = "/transactions/{transactionId}"

        /**
         * Requets of transactions Page (Common to both wallet types)
         */
        const val GET_ALL_OF_WALLET_ORDERED = "/transactions/wallets/{walletId}"
        const val GET_AMOUNT_FROM_WALLET = "/transactions/wallets/{walletId}/amount"         // Soma do lucro ou da despesa

        /**
         * Requests of a PW Insights Page
         */
        const val GET_ALL_OF_PW_GIVEN_CATEGORY_BY_DATE = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_AMOUNTS_FROM_PW_BY_CATEGORY = "/transactions/wallets/{walletId}/categoryAmounts"

        /**
         * Requests of a SW Insights Page
         */
        const val GET_ALL_OF_SW_GIVEN_USER_BY_DATE = "/transactions/wallets/{walletId}/users/{userId}"
        const val GET_AMOUNTS_FROM_SW_BY_USER = "/transactions/wallets/{walletId}/users/userAmounts"

        /**
         * Requests of overViewPage
         */
        const val GET_AMOUNT_FROM_WALLETS = "/transactions/amount}"         // Soma do lucro ou da despesa
        const val GET_ALL_GIVEN_CATEGORY_BY_DATE = "/transactions/categories/{categoryId}"
        const val GET_AMOUNTS_BY_CATEGORY = "/transactions/categoryAmounts"
        const val GET_AMOUNT_BY_WALLETS = "/transactions/walletAmounts}"         // Soma do lucro ou da despesa

        const val GET_ALL_FROM_ASSOCIATION = "/transactions/wallets/{walletId}"
    }

    object Category {
        /**
         * Create a new Category:
         * System - Created autmatically for all Users
         * Custom - Created expecifically by a User
         */
        const val CREATE = "/categories"

        /**
         * Get all system Categories (userId = "system") or all cateogries of a User
         */
        const val GET_CATEGORIES = "/categories"

        const val GET_BY_ID = "/categories/{categoryId}"
        const val UPDATE = "/categories/{categoryId}"
        const val DELETE_BY_ID = "/categories/{categoryId}"
    }
}