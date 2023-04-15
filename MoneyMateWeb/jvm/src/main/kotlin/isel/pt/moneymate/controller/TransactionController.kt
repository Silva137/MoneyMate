package isel.pt.moneymate.controller

import isel.pt.moneymate.controller.models.TransactionInputModel
import isel.pt.moneymate.services.TransactionService
import isel.pt.moneymate.utils.Uris
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TransactionController(private val transactionService: TransactionService) {

    /**
     * Handles the request to create a new Transaction (For a PW, SW, or Association betwwen W)
     *
     * @param transactionData all the data needed to create a transactin
     * @param walletId the wallet to associate this transaction
     *
     * @return the response to the request with the new transaction
     */
    @PostMapping(Uris.Transactions.CREATE)
    fun createTransaction(
        @PathVariable walletId: Int,
        @PathVariable categoryId: Int,
        @RequestBody transactionData: TransactionInputModel
    ) : ResponseEntity<*> {
        val transaction = transactionService.createTransaction(
            1, // How to get user?
            walletId,
            categoryId,
            transactionData.toTransactionInputDTO()
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transaction)
    }


    /**
     * Handles the request to get all transactions of a wallet of a user
     * ordered by price or date
     *
     * @param walletId the wallet to consult the transactions
     * @param order the chosen ordered
     *
     * @return the response to the request with a map of transactions
     */
    @PostMapping(Uris.Transactions.GET_ALL_OF_WALLET_ORDERED)
    fun getTransactionsFromWalletOrdered(
        @PathVariable walletId: Int,
        @RequestParam criterion: String,
        @RequestParam order: Int,
    ) : ResponseEntity<*> {
        val transactions = transactionService.getTransactionsFromWalletOrdered(
            walletId,
            criterion,
            order
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transactions)
    }

    /**
     * Handles the request to get the sume of all lucrativa transactions
     * or all despenses transactions of a private wallet
     *
     * @param walletId the wallet to consult the transactions
     * @param value indicates the transactions to sum, profits or despenses
     *
     * @return the sum of the values of th transactions
     */
    @PostMapping(Uris.Transactions.GET_AMOUNT_FROM_WALLET)
    fun getSumsFromWallet(
        @PathVariable walletId: Int,
        @RequestParam criterion: String,
    ) : ResponseEntity<*> {
        val sum = transactionService.getSumsFromWallet(
            walletId,
            criterion,
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(sum)
    }

    /** ----------------------------------- PW --------------------------------   */

    /**
     * Handles the request to get all transactions of a wallet of a user
     * that belongs to a certain category
     *
     * @param walletId the wallet to consult the transactions
     * @param categoryId the category of the transactions to search
     *
     * @return the response to the request with a map of transactions organized by date
     */
    @PostMapping(Uris.Transactions.GET_ALL_OF_PW_GIVEN_CATEGORY_BY_DATE)
    fun getTransactionsFromPWGivenCategory(
        @PathVariable walletId: Int,
        @PathVariable categoryId: String
    ) : ResponseEntity<*> {

        val transactions = transactionService.getTransactionsFromPWGivenCategory(
            walletId,
            categoryId,
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transactions)
    }

    /**
     * Handles the request to get the sum of all transactions by category belongign to a private wallet
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the response to the request with a map with the sum of each category
     */
    @PostMapping(Uris.Transactions.GET_AMOUNTS_FROM_PW_BY_CATEGORY)
    fun getAmountsFromPwByCategory(
        @PathVariable walletId: Int,
    ) : ResponseEntity<*> {

        val transactions = transactionService.getAmountsFromPwByCategory(
            walletId,
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transactions)
    }

    /** ----------------------------------- SW --------------------------------   */

    /**
     * Handles the request to get all transactions of a wallet of a user
     * that belongs to a certain user
     *
     * @param walletId the wallet to consult the transactions
     * @param userId the user that belongs to the SW
     *
     * @return the response to the request with a map of transactions organized by date
     */
    @PostMapping(Uris.Transactions.GET_ALL_OF_SW_GIVEN_USER_BY_DATE)
    fun getTransactionsFromSwGivenUser(
        @PathVariable walletId: Int,
        @RequestParam userId: Int
    ) : ResponseEntity<*> {

        val transactions = transactionService.getTransactionsFromSwGivenUser(
            walletId,
            userId,
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transactions)
    }

    /**
     * Handles the request to get the sum of all transactions by user
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the response to the request with a map with the sum of each user
     */
    @PostMapping(Uris.Transactions.GET_AMOUNTS_FROM_SW_BY_USER)
    fun getAmountsFromSwByUser(
        @PathVariable walletId: Int,
    ) : ResponseEntity<*> {

        val transactions = transactionService.getAmountsFromSwByUser(
            walletId,
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transactions)
    }

    /** ----------------------------------- OverView --------------------------------   */

    /**
     * Handles the request to get the sume of all lucrative transactions
     * or all despenses transactions of all private wallets
     *
     * @param value indicates the transactions to sum, profits or despenses
     *
     * @return the sum of the values of th transactions
     */
    @PostMapping(Uris.Transactions.GET_AMOUNT_FROM_WALLETS)
    fun getSumsFromWallets(
        @RequestParam criterion: String,
    ) : ResponseEntity<*> {
        val sum = transactionService.getSumsFromWallets(
            criterion,
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(sum)
    }

    /**
     * Handles the request to get all transactions of all private wallets of a user
     * that belongs to a certain category
     *
     * @param categoryId the category of the transactions to search
     *
     * @return the response to the request with a map of transactions organized by date
     */
    @PostMapping(Uris.Transactions.GET_ALL_GIVEN_CATEGORY_BY_DATE)
    fun getTransactionsFromAllWalletsGivenCategory(
        @PathVariable categoryId: String
    ) : ResponseEntity<*> {

        val transactions = transactionService.getTransactionsFromAllWalletsGivenCategory(
            categoryId,
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transactions)
    }

    /**
     * Handles the request to get the sum of all transactions by category of all private wallets
     *
     * @return the response to the request with a map with the sum of each category
     */
    @PostMapping(Uris.Transactions.GET_AMOUNTS_BY_CATEGORY)
    fun getAmountsFromAllWalletsByCategory(
        @PathVariable walletId: Int,
    ) : ResponseEntity<*> {

        val transactions = transactionService.getAmountsFromAllWalletsByCategory()

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transactions)
    }
}
