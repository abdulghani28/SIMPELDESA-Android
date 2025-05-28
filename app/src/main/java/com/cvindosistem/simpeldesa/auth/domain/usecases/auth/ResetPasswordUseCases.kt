package com.cvindosistem.simpeldesa.auth.domain.usecases.auth

import com.cvindosistem.simpeldesa.auth.data.repository.PasswordResetRepository
import com.cvindosistem.simpeldesa.auth.domain.model.RequestOtpResult
import com.cvindosistem.simpeldesa.auth.domain.model.ResetPasswordResult
import com.cvindosistem.simpeldesa.auth.domain.model.ValidateOtpResult


class RequestOtpUseCase(private val passwordResetRepository: PasswordResetRepository) {
    suspend operator fun invoke(email: String, licenseCode: String): RequestOtpResult {
        return passwordResetRepository.requestOtp(email, licenseCode)
    }
}

class ValidateOtpUseCase(private val passwordResetRepository: PasswordResetRepository) {
    suspend operator fun invoke(email: String, otp: String, licenseCode: String): ValidateOtpResult {
        return passwordResetRepository.validateOtp(email, otp, licenseCode)
    }
}

class ResetPasswordUseCase(private val passwordResetRepository: PasswordResetRepository) {
    suspend operator fun invoke(email: String, otp: String, newPassword: String, licenseCode: String): ResetPasswordResult {
        return passwordResetRepository.resetPassword(email, otp, newPassword, licenseCode)
    }
}