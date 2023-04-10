package isel.pt.moneymate.utils

import org.springframework.web.util.UriTemplate
import java.net.URI

/**
 * The Uris of the API
 */

object Uris {

    const val HOME = "/"

    object Users {
        const val CREATE = "/users/register"
        const val LOGIN = "/users/login"
        const val UPDATE = "/users"
        const val DELETE = "/users"
        const val GET_BY_ID = "/users/{id}"
    }

    object Wallets {

        /**
         * Create a Wallet:
         * Private - Given a userId and a Name
         * Shared - Given a userId and a Name, after associating otherUsers, userId becomes a "systemUser"
         */
        const val CREATE = "/wallets"

        const val GET_ALL_FROM_USER = "/wallets"
        const val GET_BY_ID = "/wallets/{walletId}"

        const val UPDATE_NAME = "/wallets/{walletId}"

        const val DELETE_BY_ID = "/wallets/{walletId}"

        const val ASSOCIATE_USER = "/wallets/associate{walletId}/users{userId}"
        const val DESSASOCIATE_USER = "/wallets/dessacoiate{walletId}/users{userId}"
    }

    object Transactions {

        /**
         * Create a Transaction:
         * For a private Wallet
         * For a shared Wallet
         * For a shared Wallet Association (A transaction that represents a Shared Wallet Balance in a Private Wallet)
         */
        const val CREATE = "/transactions/wallets/{walletId}"

        /**
         * Obtain transactions of a wallet (Shared or Private):
         *
         * (PW Search):
         * By date
         * By price
         * By category
         *
         * (SW Search):
         * By user - Given a UserId and a WalletId
         * By shared Wallet Association - Given a UserId and SharedWalletAssociatedID
         *
         * Ex: /transactions?walletId={id}&minPrice={min}&maxPrice={max}
         */
        const val GET_FROM_WALLET_OF_USER = "/transactions/wallets/{walletId}"

        /**
         * Obtain transactions of all the private Wallets of a User:
         * By date
         * By price
         * By category
         */
        const val GET_FROM_ALL_PRIVATE_WALLETS_OF_USER = "/transactions"

        const val GET_BY_ID = "/transactions/{transactionId}"
        const val UPDATE = "/transactions/{transactionId}"
        const val DELETE_BY_ID = "/transactions/{transactionId}"
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