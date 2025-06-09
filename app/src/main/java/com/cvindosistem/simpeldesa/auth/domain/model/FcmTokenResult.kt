package com.cvindosistem.simpeldesa.auth.domain.model

sealed class FcmTokenResult {
    object Success : FcmTokenResult()
    data class Error(val message: String) : FcmTokenResult()
}