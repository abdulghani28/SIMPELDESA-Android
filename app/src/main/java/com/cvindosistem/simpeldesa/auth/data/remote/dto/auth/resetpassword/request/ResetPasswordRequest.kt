package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request

data class ResetPasswordRequest(
    val email: String,
    val otp: String,
    val password: String
)