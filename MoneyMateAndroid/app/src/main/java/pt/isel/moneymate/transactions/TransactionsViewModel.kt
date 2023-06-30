package pt.isel.moneymate.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.domain.TransactionType
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionsViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _state: TransactionState by mutableStateOf(TransactionState.IDLE)
    val state: TransactionState get() = _state

    private var _transactions: List<Transaction>? by mutableStateOf(emptyList())
    val transactions: List<Transaction>? get() = _transactions


        fun fetchTransactions(walletId : Int, startDate : LocalDate, endDate: LocalDate){
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedStartDate = startDate.format(formatter)
            val formattedEndDate = endDate.format(formatter)
            viewModelScope.launch {
                _state = TransactionState.GETTING_TRANSACTIONS
                try{
                    val token = sessionManager.accessToken
                    val response = Result.success(moneymateService.transactionsService.getWalletTransactions(walletId,token,formattedStartDate,formattedEndDate))
                    val transactionList = response.getOrNull()?.transactions?.map { transactionDTO ->
                        Transaction(
                            convertType(transactionDTO.amount),
                            transactionDTO.title,
                            transactionDTO.amount.toDouble(),
                            Category(transactionDTO.category.id,transactionDTO.category.name, transactionDTO.category.user)
                        )
                    }
                    _transactions = transactionList
                    _state = TransactionState.FINISHED

                }catch (e: Exception){

                }
            }
        }


    private fun convertType(amount : Float) : TransactionType {
        return if (amount >= 0) {
            TransactionType.INCOME
        } else {
            TransactionType.EXPENSE
        }
    }











    enum class TransactionState {
        IDLE,
        GETTING_TRANSACTIONS,
        FINISHED
    }
}