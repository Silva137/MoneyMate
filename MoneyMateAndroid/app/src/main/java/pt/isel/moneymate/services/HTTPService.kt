package pt.isel.moneymate.services

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonReader
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.EMPTY_REQUEST
import pt.isel.moneymate.utils.UnexpectedResponseException
import pt.isel.moneymate.utils.fromJson
import pt.isel.moneymate.utils.getBodyOrThrow
import pt.isel.moneymate.utils.send
import java.lang.reflect.Type

open class HTTPService(
    val apiEndpoint: String,
    val okHttpClient: OkHttpClient,
    val jsonEncoder: Gson
) {


     fun <T> handleResponse(response : Response, type: Type) : T {
         return if (response.isSuccessful) {
             try {
                 val body = response.body?.string()
                 jsonEncoder.fromJson<T>(body, type)
             }
             catch (e: JsonSyntaxException) {
                 throw UnexpectedResponseException(response)
             }
         }
         else {
             throw UnexpectedResponseException(response = response)
         }
     }

    fun get(
        link: String,
        token: String
    ) =
        Request.Builder()
            .url(url = apiEndpoint + link)
            .header(name = AUTHORIZATION_HEADER, value = "$TOKEN_TYPE $token")
            .build()

    fun post(
        link: String,
        token: String,
        body: Any? = null
    ) =
        Request.Builder()
            .url(url = apiEndpoint + link)
            .header(name = AUTHORIZATION_HEADER, value = "$TOKEN_TYPE $token")
            .post(
                body = body.let {
                    jsonEncoder
                        .toJson(body)
                        .toRequestBody(contentType = applicationJsonMediaType)
                } ?: EMPTY_REQUEST
            )
            .build()


    fun postNoAuth(
        link: String,
        body: Any? = null
    ) =
        Request.Builder()
            .url(url = apiEndpoint + link)
            .post(
                body = jsonEncoder
                    .toJson(body)
                    .toRequestBody(contentType = applicationJsonMediaType)
            )
            .build()


    companion object {
        private const val APPLICATION_JSON = "application/json"
        val applicationJsonMediaType = APPLICATION_JSON.toMediaType()

        const val AUTHORIZATION_HEADER = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }
}

