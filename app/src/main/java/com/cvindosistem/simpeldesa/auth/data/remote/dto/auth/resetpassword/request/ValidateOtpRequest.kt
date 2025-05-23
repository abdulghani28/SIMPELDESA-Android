package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request

data class ValidateOtpRequest(
    val kode_lisensi: String,
    val email: String,
    val otp: String
)