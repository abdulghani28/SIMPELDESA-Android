package com.cvindosistem.simpeldesa.core.data.fcm

import com.cvindosistem.simpeldesa.auth.domain.usecases.UpdateFcmTokenUseCase
import org.koin.dsl.module

val fcmModule = module {
    single<FcmManager> { FcmManager(get(), get()) }
}