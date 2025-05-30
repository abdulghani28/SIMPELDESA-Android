package com.cvindosistem.simpeldesa.main.data.remote.api

import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface SuratApi {
    @GET("/warga-desa/surat")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}