package isel.pt.moneymate.domain

/**
 * The User entity.
 *
 * @property id the id of the user
 * @property username the username of the user
 * @property email the email of the user
 * @property passwordHash the hashed password of the user
 */

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
)