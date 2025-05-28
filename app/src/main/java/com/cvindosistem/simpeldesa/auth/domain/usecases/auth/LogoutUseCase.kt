package com.cvindosistem.simpeldesa.auth.domain.usecases.auth

import com.cvindosistem.simpeldesa.auth.data.repository.AuthRepository
import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): LogoutResult {
        return authRepository.logout()
    }
}