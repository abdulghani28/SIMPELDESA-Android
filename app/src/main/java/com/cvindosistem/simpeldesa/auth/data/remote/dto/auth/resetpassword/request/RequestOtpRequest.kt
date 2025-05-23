package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request

data class RequestOtpRequest(
    val kode_lisensi: String,
    val email: String
)