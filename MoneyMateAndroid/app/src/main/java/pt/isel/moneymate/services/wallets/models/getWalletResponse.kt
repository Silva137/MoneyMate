package pt.isel.moneymate.services.wallets.models

data class getWalletResponse(
    val wallets: List<Wallet>,
    val totalCount: Int
)