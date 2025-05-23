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
import com.cvindosistem.simpeldesa.auth.domain.repository.PasswordResetRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PasswordResetRepositoryImpl(private val authApi: AuthApi) : PasswordResetRepository {

    override suspend fun requestOtp(email: String, licenseCode: String): RequestOtpResult = withContext(
        Dispatchers.IO) {
        try {
            val response = authApi.requestOtp(RequestOtpRequest(licenseCode, email))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("PasswordResetRepository", "OTP request successful")
                    return@withContext RequestOtpResult.Success
                } ?: run {
                    Log.e("PasswordResetRepository", "OTP request response body is null")
                    return@withContext RequestOtpResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to send OTP"
                Log.e("PasswordResetRepository", "OTP request failed: $errorMessage")
                return@withContext RequestOtpResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("PasswordResetRepository", "OTP request exception", e)
            return@withContext RequestOtpResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun validateOtp(email: String, otp: String, licenseCode: String): ValidateOtpResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.validateOtp(ValidateOtpRequest(licenseCode, email, otp))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("PasswordResetRepository", "OTP validation successful: ${it.data.valid}")
                    return@withContext ValidateOtpResult.Success(it.data.valid)
                } ?: run {
                    Log.e("PasswordResetRepository", "OTP validation response body is null")
                    return@withContext ValidateOtpResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to validate OTP"
                Log.e("PasswordResetRepository", "OTP validation failed: $errorMessage")
                return@withContext ValidateOtpResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("PasswordResetRepository", "OTP validation exception", e)
            return@withContext ValidateOtpResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun resetPassword(email: String, otp: String, newPassword: String, licenseCode: String): ResetPasswordResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.resetPassword(ResetPasswordRequest(licenseCode, email, otp, newPassword))

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("PasswordResetRepository", "Password reset successful")
                    return@withContext ResetPasswordResult.Success
                } ?: run {
                    Log.e("PasswordResetRepository", "Password reset response body is null")
                    return@withContext ResetPasswordResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

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