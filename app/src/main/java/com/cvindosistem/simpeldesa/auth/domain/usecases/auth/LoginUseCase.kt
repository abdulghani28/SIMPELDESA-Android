package com.cvindosistem.simpeldesa.auth.domain.usecases.auth

import com.cvindosistem.simpeldesa.auth.domain.model.LoginResult
import com.cvindosistem.simpeldesa.auth.domain.repository.AuthRepository


class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String, licenseCode: String): LoginResult {
        return authRepository.login(email, password, licenseCode)
    }
}