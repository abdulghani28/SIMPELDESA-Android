package com.cvindosistem.simpeldesa.auth.domain.usecases

import com.cvindosistem.simpeldesa.auth.data.repository.AuthRepository
import com.cvindosistem.simpeldesa.auth.domain.model.FcmTokenResult

class UpdateFcmTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(fcmToken: String): FcmTokenResult {
        return authRepository.updateFcmToken(fcmToken)
    }
}