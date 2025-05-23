package com.cvindosistem.simpeldesa.core.data.remote.interceptor

import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userPreferences: UserPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = userPreferences.getAuthToken()

        val newRequestBuilder = originalRequest.newBuilder()
//            .header("Origin", "https://digitaldesa.avnet.id")

        if (!token.isNullOrEmpty()) {
            newRequestBuilder.header("Authorization", "Bearer $token")
        }

        return chain.proceed(newRequestBuilder.build())
    }
}
