package isel.pt.moneymate.exceptions

import org.springframework.http.MediaType

data class ProblemJson(
    val name: String,
    val message: String,
    val status: Int
    ) {
        companion object {
            val MEDIA_TYPE = MediaType.APPLICATION_PROBLEM_JSON
        }
    }