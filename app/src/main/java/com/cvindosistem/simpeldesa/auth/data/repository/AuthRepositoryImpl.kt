package com.cvindosistem.simpeldesa.auth.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.api.AuthApi
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.LoginRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.fcm.FcmTokenRequest
import com.cvindosistem.simpeldesa.auth.domain.model.FcmTokenResult
import com.cvindosistem.simpeldesa.auth.domain.model.LoginResult
import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult
import com.cvindosistem.simpeldesa.core.data.fcm.FcmManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Abstraksi untuk manajemen autentikasi pengguna.
 *
 * Implementasi default disediakan oleh [AuthRepositoryImpl].
 */
interface AuthRepository {

    /**
     * Melakukan proses login menggunakan email, password, dan license code.
     *
     * @param email alamat email atau NIK
     * @param password kata sandi pengguna
     * @param licenseCode kode lisensi untuk verifikasi wilayah/akses
     * @return [LoginResult] yang berisi token jika berhasil, atau pesan error
     */
    suspend fun login(email: String, password: String): LoginResult

    /**
     * Melakukan logout pengguna dari server dan mengakhiri sesi.
     *
     * @return [LogoutResult] status logout
     */
    suspend fun logout(): LogoutResult

    /**
     * Mengirim dan memperbarui token FCM milik pengguna ke backend.
     *
     * @param fcmToken token Firebase Cloud Messaging
     * @return [FcmTokenResult] hasil update token
     */
    suspend fun updateFcmToken(fcmToken: String): FcmTokenResult
}

/**
 * Implementasi dari [AuthRepository] yang menggunakan [AuthApi] untuk komunikasi jaringan
 * dan [FcmManager] untuk manajemen token notifikasi.
 *
 * ⚠️ Pastikan instance Retrofit dan FcmManager sudah diinject melalui Koin atau Hilt.
 */
class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val fcmManager: FcmManager
) : AuthRepository {

    /**
     * Melakukan login dan menangani semua kemungkinan hasil dengan error handling.
     * Jika berhasil, mengembalikan token dari backend.
     */
    override suspend fun login(email: String, password: String): LoginResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("AuthRepository", "Login successful: ${it.token}")
                    return@withContext LoginResult.Success(it.token)
                }
                Log.e("AuthRepository", "Login response body is null")
                return@withContext LoginResult.Error("Unknown error occurred")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
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

    /**
     * Melakukan logout dan memverifikasi respons logout dari backend.
     * Logout dianggap berhasil jika field `data == "logout"`.
     */
    override suspend fun logout(): LogoutResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.logout()

            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.data == "logout") {
                        Log.d("AuthRepository", "Logout successful")
                        return@withContext LogoutResult.Success
                    }
                    Log.e("AuthRepository", "Unexpected logout response: ${it.data}")
                    return@withContext LogoutResult.Error("Unexpected response")
                }
                Log.e("AuthRepository", "Logout response body is null")
                return@withContext LogoutResult.Error("Unknown error occurred")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
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

    /**
     * Mengirim token FCM ke backend agar pengguna dapat menerima push notification.
     * Pastikan token valid sebelum mengirim.
     */
    override suspend fun updateFcmToken(fcmToken: String): FcmTokenResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.updateFcmToken(FcmTokenRequest(fcmToken))
            if (response.isSuccessful) {
                Log.d("AuthRepository", "FCM token updated successfully")
                FcmTokenResult.Success
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Failed to update FCM token"
                Log.e("AuthRepository", "FCM token update failed: $errorMessage")
                FcmTokenResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "FCM token update exception", e)
            FcmTokenResult.Error(e.message ?: "Failed to update FCM token")
        }
    }
}