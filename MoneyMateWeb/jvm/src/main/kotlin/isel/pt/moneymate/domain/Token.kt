package isel.pt.moneymate.domain

data class Token(
    var id: Int? = null,
    var token: String,
    var revoked: Boolean = false,
    var expired: Boolean = false,
    var user: User
)



