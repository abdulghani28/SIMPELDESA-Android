package com.cvindosistem.simpeldesa.core.data.fcm

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.api.AuthApi
import com.cvindosistem.simpeldesa.auth.data.remote.dto.fcm.FcmTokenRequest
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FcmManager(
    private val authApi: AuthApi,
    private val userPreferences: UserPreferences
) {
    companion object {
        private const val TAG = "FcmManager"
    }

    suspend fun initializeFcm() {
        try {
            // Check if Firebase is initialized
            if (FirebaseApp.getApps(FirebaseApp.getInstance().applicationContext).isEmpty()) {
                Log.e(TAG, "Firebase is not initialized")
                return
            }

            // Get FCM token
            val token = FirebaseMessaging.getInstance().token.await()
            Log.d(TAG, "FCM Token: $token")

            // Send token to server if user is logged in
            if (userPreferences.isLoggedIn()) {
                updateFcmTokenOnServer(token)
            } else {
                Log.d(TAG, "User not logged in, token will be sent after login")
            }

        } catch (e: Exception) {
            Log.e(TAG, "Failed to get FCM token", e)
        }
    }

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