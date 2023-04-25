package isel.pt.moneymate.domain

import isel.pt.moneymate.http.models.users.UserDTO
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val id: Int,
    val _username: String,
    val email: String,
    var passwordHash: String
) : UserDetails {
    override fun getUsername() = email

    override fun getPassword() = passwordHash

    override fun isEnabled() = true

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun getAuthorities() = emptyList<GrantedAuthority>()

    fun toDTO() = UserDTO(id,_username, email)

}



