package com.cvindosistem.simpeldesa.auth.data.remote.api

import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("/warga-desa/auth/user-info")
    suspend fun getUserInfo(): Response<UserInfoResponse>

//    @GET("portal-desa/auth/organisasi-info")
//    suspend fun getOrganizationInfo(): Response<OrganizationInfoResponse>
//
//    @POST("portal-desa/auth/edit-profile")
//    suspend fun editProfile(
//        @Body request: EditProfileRequest
//    ): Response<EditProfileResponse>
}