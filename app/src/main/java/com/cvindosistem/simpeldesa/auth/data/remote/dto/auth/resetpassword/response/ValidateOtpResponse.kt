package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.response

data class ValidateOtpResponse(
    val data: OtpValidationData
) {
    data class OtpValidationData(
        val valid: Boolean
    )
}