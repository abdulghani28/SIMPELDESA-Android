package com.cvindosistem.simpeldesa.auth.domain.repository

import com.cvindosistem.simpeldesa.auth.domain.model.LoginResult
import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult

interface AuthRepository {
    suspend fun login(email: String, password: String, licenseCode: String): LoginResult
    suspend fun logout(): LogoutResult
}