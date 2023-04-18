package isel.pt.moneymate.repository


import isel.pt.moneymate.repository.utils.InitValues
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletsRepositoryTests {
    // FIRST RUN CREATE-SCHEMA

    @Autowired
    private lateinit var walletRepository: WalletRepository

    val initialWalletName = InitValues.InitWallet.initialWalletName
    val userId = InitValues.InitUser.initialUserId

    @BeforeAll
    fun init(){
        // Register a new wallet
        walletRepository.createWallet(initialWalletName,userId)
    }

    @Test
    fun test() {
        //TODO()
    }

}