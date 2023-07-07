package isel.pt.moneymate.services

import AllRegularTransactionsDTO
import RegularTransactionDTO
import isel.pt.moneymate.controller.models.*
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.InvalidParameterException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.exceptions.UnauthorizedException
import isel.pt.moneymate.repository.RegularTransactionRepository
import isel.pt.moneymate.repository.TransactionRepository
import isel.pt.moneymate.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import toDTO
import java.sql.Date
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class RegularTransactionsService(
    private val regularTransactionsRepository: RegularTransactionRepository,
    private val transactionRepository: TransactionRepository,
) {

    fun updateRegularTransaction(
        user: User,
        regularTransactionId: Int,
        regularTransactionData: UpdateRegularTransactionDTO,
    ): RegularTransactionDTO {

        val currRegularTrans = getRegularTransactionById(regularTransactionId)

        // Altera informaçoes relativas aos valores de transacacao
        transactionRepository.updateTransaction(
            currRegularTrans.id,
            regularTransactionData.categoryId,
            regularTransactionData.amount,
            regularTransactionData.title,
        )

        // Altera frequency
        regularTransactionsRepository.updateTransaction(
            regularTransactionId,
            regularTransactionData.frquency,
        )
        return getRegularTransactionById(regularTransactionId)
    }

    fun getAll(user: User): AllRegularTransactionsDTO{
        val regularTransactions = regularTransactionsRepository.getAll(user.id)
            ?: throw NotFoundException("Regular Transactions Not Found")
        return regularTransactions.toDTO()
    }

    fun getRegularTransactionById(regularTransactionId: Int): RegularTransactionDTO {
        val regularTransaction = regularTransactionsRepository.getTransactionById(regularTransactionId)
            ?: throw NotFoundException("Regular Transaction with id $regularTransactionId not found")
        return regularTransaction.toDTO()
    }
}