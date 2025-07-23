package com.cvindosistem.simpeldesa.main.data.remote.api

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.BidangUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.DisabilitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.DisahkanOlehResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.HubunganResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.JenisUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PerbedaanIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
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

    @GET("/warga-desa/ref/jenis-usaha")
    suspend fun getJenisUsaha(): Response<JenisUsahaResponse>

    @GET("/warga-desa/ref/bidang-usaha")
    suspend fun getBidangUsaha(): Response<BidangUsahaResponse>

    @GET("/warga-desa/ref/status-kawin")
    suspend fun getStatusKawin(): Response<StatusKawinResponse>

    @GET("/warga-desa/ref/pendidikan")
    suspend fun getPendidikan(): Response<PendidikanResponse>

    @GET("/warga-desa/ref/Hubungan")
    suspend fun getHubungan(): Response<HubunganResponse>

    @GET("/warga-desa/ref/disabilitas")
    suspend fun getDisabilitas(): Response<DisabilitasResponse>
}