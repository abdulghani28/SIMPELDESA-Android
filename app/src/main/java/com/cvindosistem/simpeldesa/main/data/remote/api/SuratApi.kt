package com.cvindosistem.simpeldesa.main.data.remote.api

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganBerpergianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganJandaDudaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratDetailResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganBedaIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganBerpergianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganDomisiliPerusahaanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganDomisiliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganIzinTidakMasukKerjaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganJandaDudaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SuratApi {
    @GET("/warga-desa/surat")
    suspend fun getSuratList(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("search") search: String? = null,
        @Query("jenis_surat") jenisSurat: String? = null,
        @Query("status") status: String? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null
    ): Response<SuratListResponse>

    @GET("/warga-desa/surat/{id}")
    suspend fun getSuratDetail(@Path("id") id: String): Response<SuratDetailResponse>

    @POST("/warga-desa/surat/beda-identitas")
    suspend fun createSuratBedaIdentitas(
        @Body request: SuratKeteranganBedaIdentitasRequest
    ): Response<SuratKeteranganBedaIdentitasResponse>

    @POST("/warga-desa/surat/bepergian")
    suspend fun createSuratBepergian(
        @Body request: SuratKeteranganBerpergianRequest
    ): Response<SuratKeteranganBerpergianResponse>

    @POST("/warga-desa/surat/domisili")
    suspend fun createSuratDomisili(
        @Body request: SuratKeteranganDomisiliRequest
    ): Response<SuratKeteranganDomisiliResponse>

    @POST("/warga-desa/surat/domisili-perusahaan")
    suspend fun createSuratDomisiliPerusahaan(
        @Body request: SuratKeteranganDomisiliPerusahaanRequest
    ): Response<SuratKeteranganDomisiliPerusahaanResponse>

    @POST("/warga-desa/surat/izin-tidak-kerja")
    suspend fun createSuratIzinTidakKerja(
        @Body request: SuratIzinTidakMasukKerjaRequest
    ): Response<SuratKeteranganIzinTidakMasukKerjaResponse>

    @POST("/warga-desa/surat/janda-duda")
    suspend fun createSuratJandaDuda(
        @Body request: SuratKeteranganJandaDudaRequest
    ): Response<SuratKeteranganJandaDudaResponse>
}