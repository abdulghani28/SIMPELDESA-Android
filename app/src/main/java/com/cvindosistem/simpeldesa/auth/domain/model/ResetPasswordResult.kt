package com.cvindosistem.simpeldesa.auth.domain.model

sealed class ResetPasswordResult {
    data object Success : ResetPasswordResult()
    data class Error(val message: String) : ResetPasswordResult()
}