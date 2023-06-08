package isel.pt.moneymate.http.models.wallets

import isel.pt.moneymate.domain.Wallet
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.models.users.toDTO
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date


/** ----------------------------------- Simple Wallet --------------------------------   */

data class WalletDTO (
    val id: Int,
    @field:NotBlank(message = "Wallet name is required")
    val name: String,
    @field:NotNull(message = "User is required")
    val user: UserDTO,
    @field:DateTimeFormat //(pattern = "dd-MM-yyyy")
    val createdAt: Date,
){
    constructor(wallet: Wallet): this(
        wallet.id,
        wallet.name,
        wallet.user.toDTO(),
        wallet.createdAt,
    )
}
fun Wallet.toDTO() = WalletDTO(this)


/** ----------------------------------- Wallet with balanceo --------------------------------   */

data class WalletWithBalanceDTO (
    val id: Int,
    @field:NotBlank(message = "Wallet name is required")
    val name: String,
    @field:NotNull(message = "User is required")
    val user: UserDTO,
    @field:DateTimeFormat //(pattern = "dd-MM-yyyy")
    val createdAt: Date,
    val balance: Int?
){
    constructor(wallet: Wallet, balance: Int): this(
        wallet.id,
        wallet.name,
        wallet.user.toDTO(),
        wallet.createdAt,
        balance
    )
}

fun Wallet.toDTO(balance: Int) = WalletWithBalanceDTO(this, balance)

/*
data class WalletsDTO (
    val wallets: List<WalletDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = wallets.size
)

fun List<Wallet>.toDTO(): WalletsDTO{
    val wallets = this.map{ it.toDTO()}
    return WalletsDTO(wallets)
}

 */

/** ----------------------------------- List Wallets with balanceo --------------------------------   */

data class WalletsWithBalanceDTO (
    val wallets: List<WalletWithBalanceDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = wallets.size
)

fun Map<Wallet, Int>.toDTO(): WalletsWithBalanceDTO {
    val wallets = this.map { (wallet, balance) -> wallet.toDTO(balance) }
    return WalletsWithBalanceDTO(wallets)
}