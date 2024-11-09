package com.cvindosistem.simpeldesa

import android.app.Application
import com.cvindosistem.simpeldesa.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SimpelDesaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SimpelDesaApplication)
            modules(appModule)
        }
    }
}