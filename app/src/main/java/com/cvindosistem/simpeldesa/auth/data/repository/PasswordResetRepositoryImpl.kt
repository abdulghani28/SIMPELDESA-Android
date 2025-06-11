package com.cvindosistem.simpeldesa.auth.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.api.AuthApi
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request.RequestOtpRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request.ResetPasswordRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request.ValidateOtpRequest
import com.cvindosistem.simpeldesa.auth.domain.model.RequestOtpResult
import com.cvindosistem.simpeldesa.auth.domain.model.ResetPasswordResult
import com.cvindosistem.simpeldesa.auth.domain.model.ValidateOtpResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Abstraksi untuk fitur reset password melalui OTP.
 *
 * Digunakan saat pengguna lupa kata sandi dan membutuhkan proses verifikasi melalui email.
 */
interface PasswordResetRepository {

    /**
     * Meminta OTP untuk reset password yang akan dikirim ke email pengguna.
     *
     * @param email alamat email pengguna yang terdaftar
     * @param licenseCode kode lisensi wilayah/akses
     * @return [RequestOtpResult] status permintaan OTP
     */
    suspend fun requestOtp(email: String, licenseCode: String): RequestOtpResult

    /**
     * Memvalidasi OTP yang telah dikirim ke email pengguna.
     *
     * @param email email pengguna
     * @param otp kode OTP yang dimasukkan pengguna
     * @param licenseCode kode lisensi
     * @return [ValidateOtpResult] hasil validasi OTP (true/false atau error)
     */
    suspend fun validateOtp(email: String, otp: String, licenseCode: String): ValidateOtpResult

    /**
     * Melakukan reset password pengguna setelah OTP valid.
     *
     * @param email email pengguna
     * @param otp kode OTP yang sudah diverifikasi
     * @param newPassword kata sandi baru
     * @param licenseCode kode lisensi
     * @return [ResetPasswordResult] status reset password
     */
    suspend fun resetPassword(email: String, otp: String, newPassword: String, licenseCode: String): ResetPasswordResult
}

/**
 * Implementasi dari [PasswordResetRepository] yang menggunakan [AuthApi] untuk
 * menangani proses OTP dan reset password dari backend.
 *
 * ⚠️ Pastikan endpoint backend sesuai dengan struktur `RequestOtpRequest`, `ValidateOtpRequest`, dan `ResetPasswordRequest`.
 */
class PasswordResetRepositoryImpl(
    private val authApi: AuthApi
) : PasswordResetRepository {

    /**
     * Mengirim permintaan OTP ke backend berdasarkan email dan licenseCode.
     * OTP akan dikirim ke email jika berhasil.
     */
    override suspend fun requestOtp(email: String, licenseCode: String): RequestOtpResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.requestOtp(RequestOtpRequest(licenseCode, email))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("PasswordResetRepository", "OTP request successful")
                    return@withContext RequestOtpResult.Success
                }
                Log.e("PasswordResetRepository", "OTP response body is null")
                return@withContext RequestOtpResult.Error("Unknown error occurred")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) { null }

                val errorMessage = errorResponse?.message ?: "Failed to send OTP"
                Log.e("PasswordResetRepository", "OTP request failed: $errorMessage")
                return@withContext RequestOtpResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("PasswordResetRepository", "OTP request exception", e)
            return@withContext RequestOtpResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    /**
     * Memverifikasi OTP yang dikirim pengguna ke backend.
     * Backend akan mengembalikan status validitas OTP.
     */
    override suspend fun validateOtp(email: String, otp: String, licenseCode: String): ValidateOtpResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.validateOtp(ValidateOtpRequest(licenseCode, email, otp))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("PasswordResetRepository", "OTP validation successful: ${it.data.valid}")
                    return@withContext ValidateOtpResult.Success(it.data.valid)
                }
                Log.e("PasswordResetRepository", "OTP validation response body is null")
                return@withContext ValidateOtpResult.Error("Unknown error occurred")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) { null }

                val errorMessage = errorResponse?.message ?: "Failed to validate OTP"
                Log.e("PasswordResetRepository", "OTP validation failed: $errorMessage")
                return@withContext ValidateOtpResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("PasswordResetRepository", "OTP validation exception", e)
            return@withContext ValidateOtpResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    /**
     * Mengirim data reset password ke backend setelah OTP terverifikasi.
     * Kata sandi akan diperbarui jika berhasil.
     */
    override suspend fun resetPassword(email: String, otp: String, newPassword: String, licenseCode: String): ResetPasswordResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.resetPassword(ResetPasswordRequest(licenseCode, email, otp, newPassword))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("PasswordResetRepository", "Password reset successful")
                    return@withContext ResetPasswordResult.Success
                }
                Log.e("PasswordResetRepository", "Password reset response body is null")
                return@withContext ResetPasswordResult.Error("Unknown error occurred")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) { null }

                val errorMessage = errorResponse?.message ?: "Failed to reset password"
                Log.e("PasswordResetRepository", "Password reset failed: $errorMessage")
                return@withContext ResetPasswordResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("PasswordResetRepository", "Password reset exception", e)
            return@withContext ResetPasswordResult.Error(e.message ?: "Unknown error occurred")
        }
    }
}