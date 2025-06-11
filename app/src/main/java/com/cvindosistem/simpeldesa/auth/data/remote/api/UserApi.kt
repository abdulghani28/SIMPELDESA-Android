package com.cvindosistem.simpeldesa.auth.data.remote.api

import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * API untuk mengambil data pengguna yang sedang login.
 *
 * ⚠️ Harus digunakan dengan token otentikasi (Authorization: Bearer ...)
 */
interface UserApi {

    /**
     * Mengambil informasi pengguna yang sedang login.
     *
     * Endpoint: GET /warga-desa/auth/user-info
     *
     * @return detail pengguna seperti nama, NIK, alamat, dsb.
     */
    @GET("/warga-desa/auth/user-info")
    suspend fun getUserInfo(): Response<UserInfoResponse>

    // Fungsi-fungsi di bawah bisa diaktifkan bila fitur edit profil dan organisasi dibuka kembali.

    /*
    @GET("portal-desa/auth/organisasi-info")
    suspend fun getOrganizationInfo(): Response<OrganizationInfoResponse>

    @POST("portal-desa/auth/edit-profile")
    suspend fun editProfile(
        @Body request: EditProfileRequest
    ): Response<EditProfileResponse>
    */
}
