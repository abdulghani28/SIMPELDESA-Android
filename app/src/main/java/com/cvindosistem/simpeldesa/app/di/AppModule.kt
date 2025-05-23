package com.cvindosistem.simpeldesa.app.di

import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferencesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<UserPreferences> { UserPreferencesImpl(androidContext()) }

}