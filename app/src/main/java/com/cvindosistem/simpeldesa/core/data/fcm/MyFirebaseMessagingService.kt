package com.cvindosistem.simpeldesa.core.data.fcm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.app.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

/**
 * Layanan untuk menerima pesan dari Firebase Cloud Messaging (FCM).
 *
 * - Menangani token baru dari FCM.
 * - Menangani pesan notifikasi dan data.
 * - Menampilkan notifikasi lokal ke pengguna.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val NOTIFICATION_CHANNEL_ID = "portal_desa_notifications"
        private const val NOTIFICATION_CHANNEL_NAME = "Portal Desa Notifications"
    }

    /**
     * Membuat channel notifikasi saat service dibuat.
     */
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    /**
     * Dipanggil saat pesan FCM diterima.
     *
     * @param remoteMessage Pesan yang diterima dari FCM.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")
        Log.d(TAG, "Message data: ${remoteMessage.data}")

        remoteMessage.notification?.let { notification ->
            Log.d(TAG, "Message Notification Body: ${notification.body}")
            showNotification(
                title = notification.title ?: "Portal Desa",
                body = notification.body ?: "",
                data = remoteMessage.data
            )
        }

        if (remoteMessage.data.isNotEmpty()) {
            handleDataMessage(remoteMessage.data)
        }
    }

    /**
     * Dipanggil saat token FCM baru dibuat.
     *
     * @param token Token baru dari FCM.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fcmManager = get<FcmManager>()
                fcmManager.updateFcmTokenOnServer(token)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update token on server", e)
            }
        }
    }

    /**
     * Menangani payload data dari pesan FCM.
     *
     * @param data Map data dari pesan.
     */
    private fun handleDataMessage(data: Map<String, String>) {
        val type = data["type"]
        val title = data["title"] ?: "Portal Desa"
        val body = data["body"] ?: ""

        when (type) {
            "surat_update" -> {
                showNotification(title, body, data)
            }
            "announcement" -> {
                showNotification(title, body, data)
            }
            else -> {
                showNotification(title, body, data)
            }
        }
    }

    /**
     * Menampilkan notifikasi ke pengguna.
     *
     * @param title Judul notifikasi.
     * @param body Isi pesan notifikasi.
     * @param data Payload tambahan untuk intent (opsional).
     */
    private fun showNotification(title: String, body: String, data: Map<String, String> = emptyMap()) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            data.forEach { (key, value) ->
                putExtra(key, value)
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
        }
    }

    /**
     * Membuat notification channel untuk perangkat Android 8+.
     */
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifications for Portal Desa app"
            enableLights(true)
            lightColor = Color.BLUE
            enableVibration(true)
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}