package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login

data class LoginResponse(
    val `data`: Data,
    val token: String

) {
    data class Data(
        val Notfilled: List<String>,
        val email: String,
        val id: String,
        val isPengaturanDesaNotFilled: Boolean,
        val is_owner: Boolean,
        val organisasi_id: String,
        val username: String
    )
}

data class ErrorResponse(
    val error: String,
    val message: String
)