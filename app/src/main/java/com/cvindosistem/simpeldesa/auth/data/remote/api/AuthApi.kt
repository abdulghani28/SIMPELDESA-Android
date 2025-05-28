package com.cvindosistem.simpeldesa.auth.data.remote.api

import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.LogoutResponse
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.LoginRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.LoginResponse
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request.RequestOtpRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request.ResetPasswordRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.request.ValidateOtpRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.response.RequestOtpResponse
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.response.ResetPasswordResponse
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.resetpassword.response.ValidateOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AuthApi {
    @POST("/warga-desa/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @DELETE("/warga-desa/auth/logout")
    suspend fun logout(): Response<LogoutResponse>

    @POST("portal-desa/auth/request-otp")
    suspend fun requestOtp(@Body requestOtpRequest: RequestOtpRequest): Response<RequestOtpResponse>

    @POST("portal-desa/auth/is-otp-valid")
    suspend fun validateOtp(@Body validateOtpRequest: ValidateOtpRequest): Response<ValidateOtpResponse>

    @POST("portal-desa/auth/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<ResetPasswordResponse>
}