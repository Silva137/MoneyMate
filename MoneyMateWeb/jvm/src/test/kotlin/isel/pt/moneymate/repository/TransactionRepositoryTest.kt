package isel.pt.moneymate.repository
/*
import isel.pt.moneymate.domain.Transaction
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

    @Autowired lateinit var transactionRepository: TransactionRepository
    @Autowired lateinit var categoryRepository: CategoryRepository
    @Autowired lateinit var usersRepository: UsersRepository
    @Autowired lateinit var walletRepository: WalletRepository

    var nOfTransactions = 0
    var userId = -1
    var walletId = -1

    @BeforeAll
    fun init(){
        val userName = "testUserForTransaction"

        val user = usersRepository.getUserByUsername(userName)
        userId = user?.id ?: usersRepository.register(userName, "usertransaction@example.com", "password")

        walletId = walletRepository.createWallet("TestWalletForTransaction", userId)
    }

    private fun getCategory(): Int {
        // Select a random available category
        val availabeCategories = categoryRepository.getCategories()

        val categorieId: Int =
            if (availabeCategories.isEmpty())
                categoryRepository.createCategory("TestCategory", userId)
            else {
                val randomCategorie = availabeCategories.randomOrNull() ?: error("No available categories found")
                randomCategorie.id
            }
        return categorieId
    }
    private fun createRandomTransaction(): Int {
        val title = "TestTransaction"
        val periodical = listOf(1, 3, 6, 9, 12).random()
        val minAmount = -100
        val maxAmount = 100
        val amount = (minAmount..maxAmount).random()
        val transactionTitle = "$title ${++nOfTransactions}"

        val categorieId = getCategory()

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
            println(it.createdAt)
        }
        return actualList
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

        val expectedList = actualList.sortedByDescending { it.createdAt }
        assertIterableEquals(actualList, expectedList)
    }

    @Test
    fun `get transactions sorted by date asc`() {
        val actualList = sortedBy(walletId, "bydate", "ASC")
        assertNotNull(actualList)

        val expectedList = actualList.sortedBy { it.createdAt }
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

    @Test
    fun `get sums from wallet`() {
        createRandomTransaction()
        createRandomTransaction()
        createRandomTransaction()

        val balanceDTO = transactionRepository.getSumsFromWallet(walletId)
        assertNotNull(balanceDTO)
        println(balanceDTO.incomeSum)
        println(balanceDTO.expensesSum)
    }

    @Test
    fun `get transactions from PW given category`() {
        val categoryId = getCategory()
        createRandomTransaction()
        createRandomTransaction()
        createRandomTransaction()
        val transactions = transactionRepository.getTransactionsFromPWGivenCategory(walletId, categoryId)

        assertNotNull(transactions)
        transactions.forEach{
            println(it.id)
            assertEquals(it.category.id, categoryId)
        }
    }

    // TODO NEED TESTING
    // Need joining querie to user or change Category() to categoryId in Mapper
    @Test
    fun `get amounts from PW by category`() {
        createRandomTransaction()
        createRandomTransaction()
        createRandomTransaction()
        val categorySumsList = transactionRepository.getAmountsFromPwByCategory(walletId)
        categorySumsList.forEach{
            println("${it.category} as ${it.amount} $")
        }
        assertNotNull(categorySumsList)
    }

    @Test
    fun `get transactions from SW given user`() {
        val walletId = 1
        val userId = 1
        val transactions = transactionRepository.getTransactionsFromSwGivenUser(walletId, userId)
        assertNotNull(transactions)
    }

    @Test
    fun `get amounts from SW by user`() {
        val walletId = 1
        val amounts = transactionRepository.getAmountsFromSwByUser(walletId)
        assertNotNull(amounts)
    }

}*/