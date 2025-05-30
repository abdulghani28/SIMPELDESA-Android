package com.cvindosistem.simpeldesa.main.data.remote.api

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.DisahkanOlehResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PerbedaanIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.TercantumIdentitasResponse
import retrofit2.Response
import retrofit2.http.GET

interface ReferensiApi {
    @GET("/warga-desa/ref/tercantum-identitas")
    suspend fun getTercantumIdentitas(): Response<TercantumIdentitasResponse>

    @GET("/warga-desa/ref/perbedaan-identitas")
    suspend fun getPerbedaanIdentitas(): Response<PerbedaanIdentitasResponse>

    @GET("/warga-desa/ref/disahkan-oleh")
    suspend fun getDisahkanOleh(): Response<DisahkanOlehResponse>

    @GET("/warga-desa/ref/agama")
    suspend fun getAgama(): Response<AgamaResponse>
}