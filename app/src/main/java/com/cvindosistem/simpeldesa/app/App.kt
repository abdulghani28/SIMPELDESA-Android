package com.cvindosistem.simpeldesa.app

import android.app.Application
import android.graphics.Bitmap
import com.cvindosistem.simpeldesa.app.di.appModule
import com.cvindosistem.simpeldesa.auth.di.authModule
import com.cvindosistem.simpeldesa.core.di.coreModule
import com.cvindosistem.simpeldesa.core.di.networkModule
import com.cvindosistem.simpeldesa.main.di.mainModule
import com.zynksoftware.documentscanner.ui.DocumentScanner
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Document Scanner first
        val configuration = DocumentScanner.Configuration()
        configuration.imageQuality = 100
        configuration.imageSize = 1000000
        configuration.imageType = Bitmap.CompressFormat.JPEG
        DocumentScanner.init(this, configuration)

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
                    mainModule
                )
            )
        }
    }
}