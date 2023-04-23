package isel.pt.moneymate.http.controller

import isel.pt.moneymate.controller.models.CreateTransactionDTO
import isel.pt.moneymate.controller.models.UpdateTransactionDTO
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.services.TransactionService
import isel.pt.moneymate.http.utils.Uris
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
        @Valid  @RequestBody transactionData: CreateTransactionDTO,
        @AuthenticationPrincipal user: User,
        @PathVariable categoryId: Int,
        @PathVariable walletId: Int
    ) : ResponseEntity<*> {
        val transaction = transactionService.createTransaction(transactionData, user, categoryId, walletId)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(transaction)
    }

    /**
     * Handles the request to get a transaction
     *
     * @param transactionId the transaction to be returned
     *
     * @return the response to the request with the transaction requested
     */
    @GetMapping(Uris.Transactions.GET_BY_ID)
    fun getTransactionById(@PathVariable transactionId: Int) : ResponseEntity<*> {
        val updatedTransaction = transactionService.getTransactionById(transactionId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(updatedTransaction)
    }

    /**
     * Handles the request to update a transaction
     *
     * @param transactionId the transaction to be updated
     * @param transactionData the data to be changed
     *
     * @return the response to the request with the updated transaction
     */
    @PutMapping(Uris.Transactions.UPDATE)
    fun updateTransaction(
        @PathVariable transactionId: Int,
        @RequestBody transactionData: UpdateTransactionDTO,
    ): ResponseEntity<*> {
        val updatedTransaction = transactionService.updateTransaction(transactionId, transactionData)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(updatedTransaction)
    }

    /**
     * Handles the request to delete a transaction
     *
     * @param transactionId the transaction to be deleted
     *
     * @return the response to the request with info "deleted with sucess"
     */
    @DeleteMapping(Uris.Transactions.DELETE_BY_ID)
    fun deleteTransaction(@PathVariable transactionId: Int) : ResponseEntity<*> {
        transactionService.deleteTransaction(transactionId)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Transaction $transactionId was deleted successfully!")
    }

    /**
     * Handles the request to get all transactions of a wallet of a user
     * ordered by price or date
     *
     * @param walletId the wallet to consult the transactions
     * @param sortedBy the chosen ordenation criterium (bydate or byprice)
     * @param orderBy the chosen order(ascending or descending)
     *
     * @return the response to the request with a map of transactions
     */
    @GetMapping(Uris.Transactions.GET_WALLET_TRANSACTIONS_SORTEDBY)
    fun getWalletTransactionsSortedBy(
        @PathVariable walletId: Int,
        @RequestParam(defaultValue = "bydate") sortedBy: String,
        @RequestParam(defaultValue = "DESC") orderBy: String,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ) : ResponseEntity<*> {
        val transactions = transactionService.getWalletTransactionsSortedBy(
            walletId,
            sortedBy,
            orderBy,
            offset,
            limit
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }


    /**
     * Handles the request to get the sume of all lucrative transactions
     * and the sum of all despenses transactions of a private wallet
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the sum of the values of th transactions
     */
    @GetMapping(Uris.Transactions.GET_WALLET_BALANCE)
    fun getWalletBalance(@PathVariable walletId: Int) : ResponseEntity<*> {
        val sum = transactionService.getWalletBalance(walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
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
    @GetMapping(Uris.Transactions.GET_PW_TRANSACTIONS_BY_CATEGORY)
    fun getPWTransactionsByCategory(
        @PathVariable walletId: Int,
        @PathVariable categoryId: Int
    ) : ResponseEntity<*> {
        val transactions = transactionService.getPWTransactionsByCategory(walletId, categoryId,)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /**
     * Handles the request to get the sum of all transactions by category belongign to a private wallet
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the response to the request with a map with the sum of each category
     */
    @GetMapping(Uris.Transactions.GET_PW_CATEGORY_BALANCE)
    fun getPWCategoriesBalance(@PathVariable walletId: Int) : ResponseEntity<*> {
        val transactions = transactionService.getPWCategoriesBalance(walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
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
    @GetMapping(Uris.Transactions.GET_ALL_OF_SW_GIVEN_USER_BY_DATE)
    fun getTransactionsFromSwGivenUser(
        @PathVariable walletId: Int,
        @PathVariable userId: Int
    ) : ResponseEntity<*> {

        val transactions = transactionService.getTransactionsFromSwGivenUser(
            walletId,
            userId,
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /**
     * Handles the request to get the sum of all transactions by user
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the response to the request with a map with the sum of each user
     */
    @GetMapping(Uris.Transactions.GET_AMOUNTS_FROM_SW_BY_USER)
    fun getAmountsFromSwByUser(
        @PathVariable walletId: Int,
    ) : ResponseEntity<*> {

        val transactions = transactionService.getAmountsFromSwByUser(
            walletId,
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }
    /*


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
}*/
}
