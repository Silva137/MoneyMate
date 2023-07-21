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
        const val GET_USER_CATEGORIES = "/usercategories"
        const val GET_SYSTEM_CATEGORIES = "/systemcategories"
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
        const val CREATE_PW = "/wallets"// TODO AFTER CHANGE URI, for now avoid confilcts

        const val CREATE_SW = "/wallets/shared"

        const val GET_PW_OF_USER = "/wallets"// TODO AFTER CHANGE URI, for now avoid confilcts
        const val GET_SW_OF_USER = "/wallets/shared"
        //const val GET_BY_ID = "/wallets/{walletId}"

        const val GET_USERS_OF_SW = "/wallets/{walletId}/users"
        const val DELETE_USER_FROM_SW = "/wallets/{walletId}/removeUser"

        const val UPDATE_NAME = "/wallets/{walletId}"

        const val DELETE_BY_ID = "/wallets/{walletId}"
    }

    object Transactions {
        /**Domain Requests */
        const val CREATE = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_BY_ID = "/transactions/{transactionId}"
        const val UPDATE = "/transactions/{transactionId}"
        const val DELETE_BY_ID = "/transactions/{transactionId}"


        /**Requests of a PW and Overview Insights Page */
        // Pedidos pagina List em statsics
        const val GET_ALL = "/transactions/wallets/{walletId}"

        // Pedidos click no grafico
        const val GET_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/{categoryId}"
        const val GET_NEG_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/{categoryId}/neg"
        const val GET_POS_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/{categoryId}/pos"

        // Pedidos amostragem de grafico
        const val GET_BALANCE_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/balance"
        const val GET_POS_AND_NEG_BALANCE_BY_CATEGORY = "/transactions/wallets/{walletId}/categories/posneg/balance"

        // TODO Deprecated not used
        const val GET_ALL_BY_CATEGORY = "/transactions/categories/{categoryId}"
        const val GET_ALL_BALANCE_BY_CATEGORY = "/transactions/categoryAmounts"
        const val GET_INCOMES = "/transactions/wallets/{walletId}/incomes"
        const val GET_EXPENSES = "/transactions/wallets/{walletId}/expenses"

        /**Requests of a SW Insights Page */
        // Pedidos click no grafico da direita
        const val GET_BY_USER = "/transactions/wallets/{walletId}/users/{userId}"

        // Pedidos amostragem de grafico
        const val GET_BALANCE_BY_USER = "/transactions/wallets/{walletId}/users/balance"
        const val GET_POS_AND_NEG_BALANCE_BY_USER = "/transactions/wallets/{walletId}/users/posneg/balance"
        const val CALCULATE_EQUAL_PAYMENTS = "/transactions/wallets/{walletId}/payments"

        /**Requests of a Regular Transactions */
        const val GET_PERIODICAL = "/transactions/periodical"
        const val UPDATE_FREQUENCY = "/transactions/{transactionId}/frquency"
        const val UPDATE_AMOUNT = "/transactions/{transactionId}/amount"

        const val GET_WALLET_BALANCE = "/transactions/wallets/{walletId}/balance"    //should be a Wallet URI?
        // TODO PASSAR ESTES PEDIDOS PARA WALLET
        const val GET_OVERALL_BALANCE = "/transactions/amount}"
        const val GET_ALL_WALLETS_BALANCE = "/transactions/walletAmounts}"         // Soma de todas as transacoes de cada wallet

    }

    object Invites {
        const val CREATE = "invitations/wallets/{walletId}"
        const val UPDATE = "invitations/{inviteId}"
        const val GET_ALL = "invitations"
    }
}