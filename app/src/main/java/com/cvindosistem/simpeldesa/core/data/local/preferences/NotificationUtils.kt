package com.cvindosistem.simpeldesa.core.data.local.preferences

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.edit

object NotificationUtils {
    private const val PREFS_NAME = "notification_prefs"
    private const val KEY_HAS_UNREAD = "has_unread_notifications"

    /**
     * Menyimpan status notifikasi yang belum dibaca
     */
    fun setHasUnreadNotifications(context: Context, hasUnread: Boolean) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.edit { putBoolean(KEY_HAS_UNREAD, hasUnread) }
    }

    /**
     * Mengambil status notifikasi yang belum dibaca
     */
    fun hasUnreadNotifications(context: Context): Boolean {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(KEY_HAS_UNREAD, false)
    }

    /**
     * Menandai semua notifikasi sebagai sudah dibaca
     */
    fun markAllNotificationsAsRead(context: Context) {
        setHasUnreadNotifications(context, false)
    }
}

/**
 * BroadcastReceiver untuk menangani notifikasi baru
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        if (intent.action == "com.cvindosistem.simpeldesa.NOTIFICATION_RECEIVED") {
            val hasUnread = intent.getBooleanExtra("has_unread", false)
            NotificationUtils.setHasUnreadNotifications(context, hasUnread)
        }
    }
}