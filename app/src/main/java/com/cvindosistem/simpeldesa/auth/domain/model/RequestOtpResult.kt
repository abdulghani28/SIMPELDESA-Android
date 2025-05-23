package com.cvindosistem.simpeldesa.auth.domain.model

sealed class RequestOtpResult {
    data object Success : RequestOtpResult()
    data class Error(val message: String) : RequestOtpResult()
}