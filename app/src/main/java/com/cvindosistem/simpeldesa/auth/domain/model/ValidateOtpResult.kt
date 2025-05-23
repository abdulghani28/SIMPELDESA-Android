package com.cvindosistem.simpeldesa.auth.domain.model

sealed class ValidateOtpResult {
    data class Success(val isValid: Boolean) : ValidateOtpResult()
    data class Error(val message: String) : ValidateOtpResult()
}