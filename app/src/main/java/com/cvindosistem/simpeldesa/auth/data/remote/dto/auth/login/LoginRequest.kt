package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login

data class LoginRequest(
    val email: String,
    val password: String,
//    val kode_lisensi: String
)