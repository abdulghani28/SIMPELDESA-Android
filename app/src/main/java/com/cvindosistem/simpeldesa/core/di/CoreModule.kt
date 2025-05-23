package com.cvindosistem.simpeldesa.core.di

import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferencesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single<UserPreferences> { UserPreferencesImpl(androidContext()) }
}