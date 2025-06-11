package com.cvindosistem.simpeldesa.core.data.fcm

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.api.AuthApi
import com.cvindosistem.simpeldesa.auth.data.remote.dto.fcm.FcmTokenRequest
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

/**
 * Manajer untuk mengelola Firebase Cloud Messaging (FCM).
 *
 * Bertanggung jawab untuk:
 * - Menginisialisasi FCM dan mengambil token perangkat.
 * - Mengirim token FCM ke server jika pengguna telah login.
 * - Berlangganan dan berhenti berlangganan dari topik FCM.
 *
 * @property authApi API otentikasi untuk mengirim token FCM ke server.
 * @property userPreferences Preferensi lokal untuk mengecek status login pengguna.
 */
class FcmManager(
    private val authApi: AuthApi,
    private val userPreferences: UserPreferences
) {
    companion object {
        private const val TAG = "FcmManager"
    }

    /**
     * Menginisialisasi Firebase Cloud Messaging:
     * - Memastikan Firebase sudah diinisialisasi.
     * - Mengambil token FCM terbaru dari Firebase.
     * - Jika pengguna login, token dikirim ke server.
     *
     * Harus dipanggil setelah Firebase diinisialisasi.
     */
    suspend fun initializeFcm() {
        try {
            if (FirebaseApp.getApps(FirebaseApp.getInstance().applicationContext).isEmpty()) {
                Log.e(TAG, "Firebase is not initialized")
                return
            }

            val token = FirebaseMessaging.getInstance().token.await()
            Log.d(TAG, "FCM Token: $token")

            if (userPreferences.isLoggedIn()) {
                updateFcmTokenOnServer(token)
            } else {
                Log.d(TAG, "User not logged in, token will be sent after login")
            }

        } catch (e: Exception) {
            Log.e(TAG, "Failed to get FCM token", e)
        }
    }

    /**
     * Mengirim token FCM ke server backend.
     *
     * @param token Token FCM yang akan dikirim ke server.
     */
    suspend fun updateFcmTokenOnServer(token: String) {
        try {
            if (!userPreferences.isLoggedIn()) {
                Log.w(TAG, "User not logged in, cannot update FCM token")
                return
            }

            val response = authApi.updateFcmToken(FcmTokenRequest(token))
            if (response.isSuccessful) {
                Log.d(TAG, "FCM token updated successfully: ${response.body()?.message}")
            } else {
                Log.e(TAG, "Failed to update FCM token: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception updating FCM token", e)
        }
    }

    /**
     * Mendaftarkan perangkat ke sebuah topik FCM.
     *
     * @param topic Nama topik yang akan dilanggan.
     */
    fun subscribeToTopic(topic: String) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Subscribed to topic: $topic")
                    } else {
                        Log.e(TAG, "Failed to subscribe to topic: $topic", task.exception)
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to subscribe to topic: $topic", e)
        }
    }

    /**
     * Menghapus langganan perangkat dari sebuah topik FCM.
     *
     * @param topic Nama topik yang akan dihentikan langganannya.
     */
    fun unsubscribeFromTopic(topic: String) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Unsubscribed from topic: $topic")
                    } else {
                        Log.e(TAG, "Failed to unsubscribe from topic: $topic", task.exception)
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to unsubscribe from topic: $topic", e)
        }
    }
}
