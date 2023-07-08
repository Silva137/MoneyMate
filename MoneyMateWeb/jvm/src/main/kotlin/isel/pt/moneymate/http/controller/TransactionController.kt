package isel.pt.moneymate.http.controller

import isel.pt.moneymate.controller.models.CreateTransactionDTO
import isel.pt.moneymate.controller.models.UpdateTransactionDTO
import isel.pt.moneymate.controller.models.UpdateTransactionAmountDTO
import isel.pt.moneymate.controller.models.UpdateTransactionFrequencyDTO
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.services.TransactionService
import isel.pt.moneymate.http.utils.Uris
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.sql.Date

@RestController
class TransactionController(private val transactionService: TransactionService) {


    // TODO Passar para wallet Controller
    /**
     * Handles the request to get the sume of all lucrative transactions
     * and the sum of all despenses transactions of a private wallet
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the sum of the values of th transactions
     */
    @GetMapping(Uris.Transactions.GET_WALLET_BALANCE)
    fun getWalletBalance(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
    ): ResponseEntity<*> {
        val sum = transactionService.getWalletBalance(user,walletId,startDate,endDate)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(sum)
    }




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
        @Valid @RequestBody transactionData: CreateTransactionDTO,
        @AuthenticationPrincipal user: User,
        @PathVariable categoryId: Int,
        @PathVariable walletId: Int
    ): ResponseEntity<*> {
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
    fun getTransactionById(@PathVariable transactionId: Int): ResponseEntity<*> {
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
    fun deleteTransaction(@PathVariable transactionId: Int): ResponseEntity<*> {
        transactionService.deleteTransaction(transactionId)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Transaction $transactionId was deleted successfully")
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
    @GetMapping(Uris.Transactions.GET_ALL)
    fun getAllTransactions(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @RequestParam(defaultValue = "bydate") sortedBy: String,
        @RequestParam(defaultValue = "DESC") orderBy: String,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "500") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getAllTransactions(
            user,
            walletId,
            sortedBy,
            orderBy,
            startDate,
            endDate,
            offset,
            limit
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /**
     * Handles the request to get all income transactions of a wallet of a user
     * ordered by price or date
     *
     * @param walletId the wallet to consult the transactions
     * @param sortedBy the chosen ordenation criterium (bydate or byprice)
     * @param orderBy the chosen order(ascending or descending)
     *
     * @return the response to the request with a map of transactions
     */
    @GetMapping(Uris.Transactions.GET_INCOMES)
    fun getIncomeTransactions(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @RequestParam(defaultValue = "bydate") sortedBy: String,
        @RequestParam(defaultValue = "DESC") orderBy: String,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getIncomeTransactions(
            user,
            walletId,
            sortedBy,
            orderBy,
            startDate,
            endDate,
            offset,
            limit
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /**
     * Handles the request to get all expenses transactions of a wallet of a user
     * ordered by price or date
     *
     * @param walletId the wallet to consult the transactions
     * @param sortedBy the chosen ordenation criterium (bydate or byprice)
     * @param orderBy the chosen order(ascending or descending)
     *
     * @return the response to the request with a map of transactions
     */
    @GetMapping(Uris.Transactions.GET_EXPENSES)
    fun getExpenseTransactions(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @RequestParam(defaultValue = "bydate") sortedBy: String,
        @RequestParam(defaultValue = "DESC") orderBy: String,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getExpenseTransactions(
            user,
            walletId,
            sortedBy,
            orderBy,
            startDate,
            endDate,
            offset,
            limit
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
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
    @GetMapping(Uris.Transactions.GET_BY_CATEGORY)
    fun getByCategory(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @PathVariable categoryId: Int,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getByCategory(
            user,
            walletId,
            categoryId,
            startDate,
            endDate,
            offset,
            limit
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }


    /**
     * Handles the request to get all positive transactions of a wallet of a user
     * that belongs to a certain category
     *
     * @param walletId the wallet to consult the transactions
     * @param categoryId the category of the transactions to search
     *
     * @return the response to the request with a map of transactions organized by date
     */
    @GetMapping(Uris.Transactions.GET_POS_BY_CATEGORY)
    fun getPosByCategory(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @PathVariable categoryId: Int,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getPosByCategory(
            user,
            walletId,
            categoryId,
            startDate,
            endDate,
            offset,
            limit
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }


    /**
     * Handles the request to get all negative transactions of a wallet of a user
     * that belongs to a certain category
     *
     * @param walletId the wallet to consult the transactions
     * @param categoryId the category of the transactions to search
     *
     * @return the response to the request with a map of transactions organized by date
     */
    @GetMapping(Uris.Transactions.GET_NEG_BY_CATEGORY)
    fun getNegByCategory(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @PathVariable categoryId: Int,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getNegByCategory(
            user,
            walletId,
            categoryId,
            startDate,
            endDate,
            offset,
            limit
        )
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
    @GetMapping(Uris.Transactions.GET_BALANCE_BY_CATEGORY)
    fun getBalanceByCategory(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
    ): ResponseEntity<*> {
        val transactions = transactionService.getBalanceByCategory(
            user,
            walletId,
            startDate,
            endDate,
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /**
     * Handles the request to get the sum of all positive transactions by category belongign to a private wallet
     * and negative transactions
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the response to the request with a map with the sum of each category
     */
    @GetMapping(Uris.Transactions.GET_POS_AND_NEG_BALANCE_BY_CATEGORY)
    fun getPositiveBalanceByCategory(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int,
        @RequestParam startDate: Date,
        @RequestParam endDate: Date,
    ): ResponseEntity<*> {
        val transactions = transactionService.getPosAndNegBalanceByCategory(
            user,
            walletId,
            startDate,
            endDate,
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    // TODO All Below are not tested
    /** ----------------------------------- OverView --------------------------------   */

    /**
     * Handles the request to get all transactions of al wallets of a user
     * that belongs to a certain category
     *
     * @param walletId the wallet to consult the transactions
     * @param categoryId the category of the transactions to search
     *
     * @return the response to the request with a map of transactions organized by date
     */
    @GetMapping(Uris.Transactions.GET_ALL_BY_CATEGORY)
    fun getAllByCategory(
        @PathVariable categoryId: Int,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getAllByCategory(
            categoryId,
            offset,
            limit
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /**
     * Handles the request to get the sum of all transactions by category of all wallets
     *
     * @param walletId the wallet to consult the transactions
     *
     * @return the response to the request with a map with the sum of each category
     */
    @GetMapping(Uris.Transactions.GET_ALL_BALANCE_BY_CATEGORY)
    fun getAllBalanceByCategory(): ResponseEntity<*> {
        val transactions = transactionService.getAllBalanceByCategory()
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
    @GetMapping(Uris.Transactions.GET_BY_USER)
    fun getByUser(
        @PathVariable shId: Int,
        @PathVariable userId: Int,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getByUser(
            shId,
            userId,
            offset,
            limit
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
    @GetMapping(Uris.Transactions.GET_BALANCE_BY_USER)
    fun getBalanceByUser(
        @PathVariable shId: Int,
    ): ResponseEntity<*> {
        val transactions = transactionService.getBalanceByUser(shId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /** ----------------------------------- Regular --------------------------------   */

    /**
     * Handles the request to get all peridical transactions of all wallets of a user
     *
     *
     * @return the response to the request with a map of transactions
     */
    @GetMapping(Uris.Transactions.GET_PERIODICAL)
    fun getPeriodicalTransactions(
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val transactions = transactionService.getPeriodicalTransactions(offset, limit)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions)
    }

    /**
     * Handles the request to update a transaction
     *
     * @param transactionId the transaction to be updated
     * @param transactionData the data to be changed
     *
     * @return the response to the request with the updated transaction
     */
    @PutMapping(Uris.Transactions.UPDATE_FREQUENCY)
    fun updateTransactionFrequency(
        @PathVariable transactionId: Int,
        @RequestBody transactionData: UpdateTransactionFrequencyDTO,
    ): ResponseEntity<*> {
        val updatedTransaction = transactionService.updateTransactionFrequency(transactionId, transactionData)
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
    @PutMapping(Uris.Transactions.UPDATE_AMOUNT)
    fun updateTransactionAmount(
        @PathVariable transactionId: Int,
        @RequestBody transactionData: UpdateTransactionAmountDTO,
    ): ResponseEntity<*> {
        val updatedTransaction = transactionService.updateTransactionAmount(transactionId, transactionData)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(updatedTransaction)
    }
}


