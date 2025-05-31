package com.cvindosistem.simpeldesa.auth.domain.model

sealed class LogoutResult {
    data object Success : LogoutResult()
    data class Error(val data: String) : LogoutResult()
}