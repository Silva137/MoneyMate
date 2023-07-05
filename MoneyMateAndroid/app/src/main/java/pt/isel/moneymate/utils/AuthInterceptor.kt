package pt.isel.moneymate.utils
import android.util.Log
import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import pt.isel.moneymate.services.users.models.AuthenticationOutputModel
import pt.isel.moneymate.session.SessionManager
import java.io.IOException

class AuthInterceptor(
    private val sessionManager: SessionManager,
    private val apiEndpoint: String
) : Interceptor {

    private val okHttpClient = OkHttpClient()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        val body = response.body?.string()
        if (response.code == 401) {
            val error = Gson().fromJson(body,ApiException::class.java)
            if( error.name == "Token Expired") {
                val refreshToken = sessionManager.refreshToken
                if (refreshToken == null) {
                    sessionManager.clearSession()
                    navigateToLogin()
                    throw IOException("No refresh token")
                }

                try {
                    val tokens = refreshToken(refreshToken)
                    val accessToken = tokens?.access_token

                    if (accessToken != null) {
                        sessionManager.setSession(accessToken, refreshToken)
                        val newRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer $accessToken")
                            .build()
                        response.close()
                        return chain.proceed(newRequest)
                    } else {
                        sessionManager.clearSession()
                        navigateToLogin()
                    }

                } catch (e: Exception) {
                    Log.e("AuthInterceptor", "Error refreshing token: ${e.message}")
                    sessionManager.clearSession()
                    throw e
                }
            }
        } else if (response.code == 403) {
            sessionManager.clearSession()
            navigateToLogin()
        }

        return response
    }

    @Throws(IOException::class)
    private fun refreshToken(refreshToken: String): AuthenticationOutputModel? {
        val requestBody = "{\"refreshToken\": \"$refreshToken\"}".toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(apiEndpoint + Uris.Authentication.REFRESH_TOKEN)
            .post(body = requestBody)
            .build()

        val response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            return Gson().fromJson(responseBody, AuthenticationOutputModel::class.java)
        } else {
            // Handle unsuccessful response
            throw IOException("Failed to refresh access token: ${response.code}")
        }
    }

    private fun navigateToLogin() {
        /*
        val context = LocalContext.current
        GlobalScope.launch(Dispatchers.Main) {
            val intent = Intent(context, loginActivityClass)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
         */
    }

    private fun convertResponse(response : Response) {

    }
}
