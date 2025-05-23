package com.cvindosistem.simpeldesa.auth.domain.repository

import com.cvindosistem.simpeldesa.auth.domain.model.RequestOtpResult
import com.cvindosistem.simpeldesa.auth.domain.model.ResetPasswordResult
import com.cvindosistem.simpeldesa.auth.domain.model.ValidateOtpResult

interface PasswordResetRepository {
    suspend fun requestOtp(email: String, licenseCode: String): RequestOtpResult
    suspend fun validateOtp(email: String, otp: String, licenseCode: String): ValidateOtpResult
    suspend fun resetPassword(email: String, otp: String, newPassword: String, licenseCode: String): ResetPasswordResult
}