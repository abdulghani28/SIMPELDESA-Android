package com.cvindosistem.simpeldesa.core.data.remote.api

import com.cvindosistem.simpeldesa.core.domain.model.notification.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET

interface NotificationApi {
    @GET("/warga-desa/notifikasi")
    suspend fun getNotification(): Response<NotificationResponse>
}