package com.cvindosistem.simpeldesa.core.data.remote.interceptor

import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * An OkHttp interceptor that automatically adds an `Authorization` header
 * to outgoing HTTP requests if the user is authenticated.
 *
 * This is used to attach a Bearer token retrieved from [UserPreferences]
 * to each request, allowing secure communication with protected endpoints
 * on the backend.
 *
 * Typical usage:
 * - Register this interceptor in the OkHttpClient builder.
 * - Automatically handles token inclusion without modifying individual requests.
 *
 * Example header:
 * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
 *
 * Optionally, headers like `Origin` or `Content-Type` can also be appended
 * here if required for backend validation.
 */
class AuthInterceptor(
    private val userPreferences: UserPreferences
) : Interceptor {

    /**
     * Intercepts the outgoing request and appends the Authorization header
     * if the user has a valid auth token.
     *
     * @param chain The request chain from OkHttp.
     * @return The [Response] from the proceeding request.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = userPreferences.getAuthToken()

        val newRequestBuilder = originalRequest.newBuilder()

        // Optionally set origin header if CORS or backend validation requires it.
        // newRequestBuilder.header("Origin", "https://digitaldesa.avnet.id")

        if (!token.isNullOrEmpty()) {
            newRequestBuilder.header("Authorization", "Bearer $token")
        }

        return chain.proceed(newRequestBuilder.build())
    }
}
