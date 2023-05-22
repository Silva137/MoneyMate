package pt.isel.moneymate.utils

import okhttp3.Response

abstract class ApiException(msg: String) : Exception(msg)

class UnexpectedResponseException(
    val response: Response? = null
) : ApiException("Unexpected ${response?.code} response from the API.")