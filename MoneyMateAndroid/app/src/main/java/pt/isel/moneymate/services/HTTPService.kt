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
import pt.isel.moneymate.utils.*
import java.lang.reflect.Type

open class HTTPService(
    val apiEndpoint: String,
    val okHttpClient: OkHttpClient,
    val jsonEncoder: Gson
) {


    fun <T> handleResponse(response: Response, type: Type): APIResult<T> {
        return if (response.isSuccessful) {
            try {
                val body = response.body?.string()
                val data = jsonEncoder.fromJson<T>(body, type)
                APIResult.Success(data)
            } catch (e: JsonSyntaxException) {
                APIResult.Error("Unexpected response format")
            }
        } else {
            val errorBody = response.body?.string()
            val errorMessage = extractErrorMessage(errorBody)
            APIResult.Error(errorMessage ?: "An error occurred")
        }
    }

    private fun extractErrorMessage(errorBody: String?): String? {
        return try {
            val exception = jsonEncoder.fromJson(errorBody, ApiException::class.java)
            exception.name
        } catch (e: JsonSyntaxException) {
            null
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

    fun patch(
        link: String,
        token: String,
        body: Any? = null
    ) =
        Request.Builder()
            .url(url = apiEndpoint + link)
            .header(name = AUTHORIZATION_HEADER, value = "$TOKEN_TYPE $token")
            .patch(
                body = body.let {
                    jsonEncoder
                        .toJson(body)
                        .toRequestBody(contentType = applicationJsonMediaType)
                } ?: EMPTY_REQUEST
            )
            .build()

    fun delete(
        link: String,
        token: String,
        body: Any? = null
    ) =
        Request.Builder()
            .url(url = apiEndpoint + link)
            .header(name = AUTHORIZATION_HEADER, value = "$TOKEN_TYPE $token")
            .delete(
                body = body.let {
                    jsonEncoder
                        .toJson(body)
                        .toRequestBody(contentType = applicationJsonMediaType)
                } ?: EMPTY_REQUEST
            )
            .build()


    companion object {
        private const val APPLICATION_JSON = "application/json"
        val applicationJsonMediaType = APPLICATION_JSON.toMediaType()

        const val AUTHORIZATION_HEADER = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }
}

