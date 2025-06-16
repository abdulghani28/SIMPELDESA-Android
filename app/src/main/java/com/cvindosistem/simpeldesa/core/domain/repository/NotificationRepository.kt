package com.cvindosistem.simpeldesa.core.domain.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.core.data.remote.api.NotificationApi
import com.cvindosistem.simpeldesa.core.domain.model.notification.NotifikasiResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface NotifikasiRepository {
    suspend fun getNotifikasi(): NotifikasiResult
}

class NotifikasiRepositoryImpl(
    private val notificationApi: NotificationApi
) : NotifikasiRepository {

    override suspend fun getNotifikasi(): NotifikasiResult = withContext(Dispatchers.IO) {
        try {
            val response = notificationApi.getNotification()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("NotifikasiRepository", "Fetched notifikasi")
                    return@withContext NotifikasiResult.Success(it)
                } ?: run {
                    Log.e("NotifikasiRepository", "Notifikasi response body is null")
                    return@withContext NotifikasiResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch notifikasi"
                Log.e("NotifikasiRepository", "Notifikasi failed: $errorMessage")
                return@withContext NotifikasiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("NotifikasiRepository", "Notifikasi exception", e)
            return@withContext NotifikasiResult.Error(e.message ?: "Unknown error occurred")
        }
    }
}