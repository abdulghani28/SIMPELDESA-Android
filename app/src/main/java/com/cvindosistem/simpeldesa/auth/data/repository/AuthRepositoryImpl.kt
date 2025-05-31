package com.cvindosistem.simpeldesa.auth.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.api.AuthApi
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.LoginRequest
import com.cvindosistem.simpeldesa.auth.domain.model.LoginResult
import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AuthRepository {
    suspend fun login(email: String, password: String, licenseCode: String): LoginResult
    suspend fun logout(): LogoutResult
}

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository {

    override suspend fun login(email: String, password: String, licenseCode: String): LoginResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.login(LoginRequest(email, password, licenseCode))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("AuthRepository", "Login successful: ${it.token}")
                    return@withContext LoginResult.Success(it.token)
                } ?: run {
                    Log.e("AuthRepository", "Login response body is null")
                    return@withContext LoginResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Authentication failed"
                Log.e("AuthRepository", "Login failed: $errorMessage")
                return@withContext LoginResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login exception", e)
            return@withContext LoginResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun logout(): LogoutResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.logout()

            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.data == "logout") { // Ubah dari message ke data
                        Log.d("AuthRepository", "Logout successful")
                        return@withContext LogoutResult.Success
                    } else {
                        Log.e("AuthRepository", "Unexpected logout response: ${it.data}")
                        return@withContext LogoutResult.Error("Unexpected response")
                    }
                } ?: run {
                    Log.e("AuthRepository", "Logout response body is null")
                    return@withContext LogoutResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Logout failed"
                Log.e("AuthRepository", "Logout failed: $errorMessage")
                return@withContext LogoutResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Logout exception", e)
            return@withContext LogoutResult.Error(e.message ?: "Unknown error occurred")
        }
    }
}