package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.repository.utils.InitValues
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class TransactionRepositoryTest {

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    var nOfTransactions = 0
    val userId = InitValues.InitUser.initialUserId
    val walletId = InitValues.InitWallet.initialWalletId

    private fun createRandomTransaction(): Int {
        val title = "TestTransaction"
        val periodical = listOf(1, 3, 6, 9, 12).random()
        val minAmount = -100
        val maxAmount = 100
        val amount = (minAmount..maxAmount).random()
        val transactionTitle = "$title ${++nOfTransactions}"

        // Select a random available category
        val availabeCategories = categoryRepository.getCategories()

        val categorieId: Int =
            if (availabeCategories.isEmpty())
                categoryRepository.createCategory("TestCategory", userId)
            else {
                val randomCategorie = availabeCategories.randomOrNull() ?: error("No available categories found")
                randomCategorie.id
            }

        val transactionId = transactionRepository.createTransaction(
            userId, walletId, categorieId, amount, transactionTitle, periodical
        )

        println(transactionId)
        return transactionId
    }

    private fun sortedBy(wId: Int, criterion: String, order: String): List<Transaction> {
        createRandomTransaction()
        createRandomTransaction()
        createRandomTransaction()
        val actualList = transactionRepository.getTransactionsSortedBy(wId, criterion, order)

        println("Printing transaction dates")
        actualList.forEach{
            println(it.dateOfCreation)
        }
        return actualList
    }

    @BeforeAll
    fun init(){
        val tId = createRandomTransaction()
        assertNotNull(tId)
    }

    @Test
    fun `create Transaction`() {
        val transaction = createRandomTransaction()
        assertNotNull(transaction)
    }

    @Test
    fun `get transaction by id`() {
        val createTransactionId = createRandomTransaction()

        val returnedTransaction = transactionRepository.getTransactionById(createTransactionId)

        assertNotNull(returnedTransaction)
        assertEquals(returnedTransaction!!.id, createTransactionId)
    }

    @Test
    fun `update transaction`() {
        val createTransactionId = createRandomTransaction()
        val transaction = transactionRepository.getTransactionById(createTransactionId)
        val updatedTitle = "TestTransactionUpdated"

        assertNotNull(transaction)

        // TODO Alterar restantes valores categoria e amount
        transactionRepository.updateTransaction(
            transaction!!.id, transaction.category.id, transaction.amount, updatedTitle
        )

        val updatedTransaction = transactionRepository.getTransactionById(createTransactionId)

        assertNotNull(updatedTransaction)
        assertEquals(transaction.amount, updatedTransaction!!.amount)
        assertEquals(transaction.category.id, updatedTransaction.category.id)
        assertEquals(updatedTitle, updatedTransaction.title)
    }

    @Test
    fun `delete transaction`() {
        val createTransactionId = createRandomTransaction()
        transactionRepository.deleteTransaction(createTransactionId)
        val deletedTransaction = transactionRepository.getTransactionById(createTransactionId)
        assertNull(deletedTransaction)
    }

    @Test
    fun `get transactions sorted by date desc`() {
        val actualList = sortedBy(walletId, "bydate", "DESC")
        assertNotNull(actualList)

        val expectedList = actualList.sortedByDescending { it.dateOfCreation }
        assertIterableEquals(actualList, expectedList)
    }

    @Test
    fun `get transactions sorted by date asc`() {
        val actualList = sortedBy(walletId, "bydate", "ASC")
        assertNotNull(actualList)

        val expectedList = actualList.sortedBy { it.dateOfCreation }
        assertIterableEquals(actualList, expectedList)
    }

    @Test
    fun `get transactions sorted by price desc`() {
        val actualList = sortedBy(walletId, "byprice", "DESC")
        assertNotNull(actualList)

        val expectedList = actualList.sortedByDescending { it.amount }
        assertIterableEquals(actualList, expectedList)
    }

    @Test
    fun `get transactions sorted by price asc`() {
        val actualList = sortedBy(walletId, "byprice", "ASC")
        assertNotNull(actualList)

        val expectedList = actualList.sortedBy { it.amount }
        assertIterableEquals(actualList, expectedList)
    }
}