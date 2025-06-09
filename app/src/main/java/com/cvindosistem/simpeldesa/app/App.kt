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

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase first
        FirebaseApp.initializeApp(this)

        // Then initialize Koin
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

        initializeFcm()
    }

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