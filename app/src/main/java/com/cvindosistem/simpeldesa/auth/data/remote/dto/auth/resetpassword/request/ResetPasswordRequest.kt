package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request

data class ResetPasswordRequest(
    val kode_lisensi: String,
    val email: String,
    val otp: String,
    val password: String
)