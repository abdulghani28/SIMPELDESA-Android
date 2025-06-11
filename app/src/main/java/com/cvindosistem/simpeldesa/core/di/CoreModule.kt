package com.cvindosistem.simpeldesa.core.di

import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferencesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Core module yang menyediakan dependensi utama aplikasi.
 * - Menyediakan [UserPreferences] untuk manajemen data autentikasi & cache lokal.
 */
val coreModule = module {
    // Menyediakan instance UserPreferences berbasis SharedPreferences
    single<UserPreferences> { UserPreferencesImpl(androidContext()) }
}