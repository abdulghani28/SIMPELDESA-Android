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
import com.cvindosistem.simpeldesa.auth.data.remote.dto.fcm.FcmTokenRequest
import com.cvindosistem.simpeldesa.auth.data.remote.dto.fcm.FcmTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

/**
 * API untuk autentikasi pengguna dan manajemen sesi.
 *
 * ⚠️ Pastikan base URL Retrofit telah mengarah ke host backend yang benar.
 */
interface AuthApi {

    /**
     * Login pengguna dengan kredensial (email/NIK dan password).
     *
     * Endpoint: POST /warga-desa/auth/login
     *
     * @param loginRequest data login yang dikirim (NIK/email dan password)
     * @return respons login, berisi token dan informasi pengguna
     */
    @POST("/warga-desa/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    /**
     * Logout pengguna dan menghapus sesi aktif.
     *
     * Endpoint: DELETE /warga-desa/auth/logout
     *
     * @return respons logout
     */
    @DELETE("/warga-desa/auth/logout")
    suspend fun logout(): Response<LogoutResponse>

    /**
     * Meminta OTP (One-Time Password) untuk verifikasi email/nomor HP.
     *
     * Endpoint: POST /portal-desa/auth/request-otp
     *
     * @param requestOtpRequest data email/nomor untuk kirim OTP
     * @return respons yang berisi informasi apakah OTP berhasil dikirim
     */
    @POST("portal-desa/auth/request-otp")
    suspend fun requestOtp(@Body requestOtpRequest: RequestOtpRequest): Response<RequestOtpResponse>

    /**
     * Memvalidasi OTP yang sudah dikirim sebelumnya.
     *
     * Endpoint: POST /portal-desa/auth/is-otp-valid
     *
     * @param validateOtpRequest data OTP untuk diverifikasi
     * @return hasil validasi OTP
     */
    @POST("portal-desa/auth/is-otp-valid")
    suspend fun validateOtp(@Body validateOtpRequest: ValidateOtpRequest): Response<ValidateOtpResponse>

    /**
     * Reset password pengguna setelah verifikasi OTP.
     *
     * Endpoint: POST /portal-desa/auth/reset-password
     *
     * @param resetPasswordRequest data email + password baru + OTP
     * @return status reset password
     */
    @POST("portal-desa/auth/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<ResetPasswordResponse>

    /**
     * Mengirim token FCM ke backend untuk notifikasi push.
     *
     * Endpoint: POST /warga-desa/auth/fcm-token
     *
     * @param fcmTokenRequest token FCM dari perangkat
     * @return status update token FCM
     */
    @POST("/warga-desa/auth/fcm-token")
    suspend fun updateFcmToken(@Body fcmTokenRequest: FcmTokenRequest): Response<FcmTokenResponse>
}
