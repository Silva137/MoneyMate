package pt.isel.moneymate.utils

import okhttp3.Response

open class ApiException(val name: String, override val message: String, val status : Int) : Exception(message)

/*
class UnexpectedResponseException(
    val response: Response? = null
) : ApiException("Unexpected ${response?.code} response from the API.")

 */