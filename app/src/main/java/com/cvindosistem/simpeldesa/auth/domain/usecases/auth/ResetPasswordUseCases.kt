package com.cvindosistem.simpeldesa.auth.domain.usecases.auth

import com.cvindosistem.simpeldesa.auth.data.repository.PasswordResetRepository
import com.cvindosistem.simpeldesa.auth.domain.model.RequestOtpResult
import com.cvindosistem.simpeldesa.auth.domain.model.ResetPasswordResult
import com.cvindosistem.simpeldesa.auth.domain.model.ValidateOtpResult

/**
 * SEMUA USE CASE MEMILIKI POLA SERUPA
 */

/**
 * Use case untuk mengirim permintaan OTP untuk reset password.
 *
 * Memanggil metode requestOtp pada [PasswordResetRepository] dan mengembalikan hasilnya dalam bentuk [RequestOtpResult].
 *
 * @property passwordResetRepository Repository untuk proses reset password.
 */
class RequestOtpUseCase(private val passwordResetRepository: PasswordResetRepository) {
    /**
     * Menjalankan proses permintaan OTP dengan email dan kode lisensi.
     *
     * @return [RequestOtpResult] hasil dari permintaan OTP.
     */
    suspend operator fun invoke(email: String, licenseCode: String): RequestOtpResult {
        return passwordResetRepository.requestOtp(email, licenseCode)
    }
}

/**
 * Use case untuk memvalidasi OTP pada proses reset password.
 *
 * Memanggil metode validateOtp pada [PasswordResetRepository] dan mengembalikan hasilnya dalam bentuk [ValidateOtpResult].
 *
 * @property passwordResetRepository Repository untuk proses reset password.
 */
class ValidateOtpUseCase(private val passwordResetRepository: PasswordResetRepository) {
    /**
     * Menjalankan proses validasi OTP dengan email, OTP, dan kode lisensi.
     *
     * @return [ValidateOtpResult] hasil dari validasi OTP.
     */
    suspend operator fun invoke(email: String, otp: String, licenseCode: String): ValidateOtpResult {
        return passwordResetRepository.validateOtp(email, otp, licenseCode)
    }
}

/**
 * Use case untuk mereset password pengguna.
 *
 * Memanggil metode resetPassword pada [PasswordResetRepository] dan mengembalikan hasilnya dalam bentuk [ResetPasswordResult].
 *
 * @property passwordResetRepository Repository untuk proses reset password.
 */
class ResetPasswordUseCase(private val passwordResetRepository: PasswordResetRepository) {
    /**
     * Menjalankan proses reset password dengan email, OTP, password baru, dan kode lisensi.
     *
     * @return [ResetPasswordResult] hasil dari proses reset password.
     */
    suspend operator fun invoke(email: String, otp: String, newPassword: String, licenseCode: String): ResetPasswordResult {
        return passwordResetRepository.resetPassword(email, otp, newPassword, licenseCode)
    }
}
