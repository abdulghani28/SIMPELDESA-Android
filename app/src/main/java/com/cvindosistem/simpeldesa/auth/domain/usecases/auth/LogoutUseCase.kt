package com.cvindosistem.simpeldesa.auth.domain.usecases.auth

import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult
import com.cvindosistem.simpeldesa.auth.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): LogoutResult {
        return authRepository.logout()
    }
}