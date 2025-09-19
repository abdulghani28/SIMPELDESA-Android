package com.cvindosistem.simpeldesa.core.data.fcm

import org.koin.dsl.module

/**
 * Modul Koin untuk menyediakan instance [FcmManager] sebagai singleton.
 */
val fcmModule = module {
    single<FcmManager> { FcmManager(get(), get()) }
}
