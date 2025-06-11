package com.cvindosistem.simpeldesa.app

import android.app.Application
import android.graphics.Bitmap
import org.koin.android.ext.android.get
import android.util.Log
import com.cvindosistem.simpeldesa.app.di.appModule
import com.cvindosistem.simpeldesa.auth.di.authModule
import com.cvindosistem.simpeldesa.core.data.fcm.FcmManager
import com.cvindosistem.simpeldesa.core.data.fcm.fcmModule
import com.cvindosistem.simpeldesa.core.di.coreModule
import com.cvindosistem.simpeldesa.core.di.networkModule
import com.cvindosistem.simpeldesa.main.di.mainModule
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Kelas aplikasi utama yang menginisialisasi Firebase dan Koin saat aplikasi diluncurkan.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inisialisasi Firebase sebelum library lainnya
        FirebaseApp.initializeApp(this)

        // Inisialisasi Koin dengan berbagai modul DI
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    coreModule,
                    networkModule,
                    authModule,
                    mainModule,
                    fcmModule
                )
            )
        }

        // Inisialisasi FCM (Firebase Cloud Messaging) secara async
        initializeFcm()
    }

    /**
     * Inisialisasi Firebase Cloud Messaging secara asinkron.
     */
    private fun initializeFcm() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fcmManager = get<FcmManager>()
                fcmManager.initializeFcm()
            } catch (e: Exception) {
                Log.e("App", "Failed to initialize FCM", e)
            }
        }
    }
}
