package pt.isel.moneymate.utils

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


    suspend fun <T> Request.send(okHttpClient: OkHttpClient, parseResponse: (Response) -> T): T =
        suspendCancellableCoroutine { continuation ->
            val call = okHttpClient.newCall(request = this)

            call.enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        continuation.resume(parseResponse(response))
                    } catch (t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                }
            })

            continuation.invokeOnCancellation { call.cancel() }
        }
    fun Response.getBodyOrThrow(): ResponseBody =
    body ?: throw IllegalStateException("Response body is null")


inline fun <reified T> Gson.fromJson(json: JsonReader): T = fromJson(json, T::class.java)

