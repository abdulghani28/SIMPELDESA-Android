package com.cvindosistem.simpeldesa.main.data.remote.api

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBerpergianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKGhaibRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJandaDudaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKelahiranRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKematianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPenghasilanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKResiKTPSementaraRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKStatusPerkawinanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKTidakMampuRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKUsahaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPCatatanKepolisianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPernikahanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPindahDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratKuasaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratTugasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratDetailResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKGhaibResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBedaIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBerpergianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliPerusahaanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKIzinTidakMasukKerjaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKJandaDudaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKelahiranResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKematianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKPenghasilanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKResiKTPSementaraResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKStatusPerkawinanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPCatatanKepolisianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPKehilanganResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPPernikahanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPPindahDomisiliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratrekomendasi.SRKeramaianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratlainnya.SuratKuasaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratListResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKTidakMampuResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratlainnya.SuratTugasResponse
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
        @Body request: SKBedaIdentitasRequest
    ): Response<SKBedaIdentitasResponse>

    @POST("/warga-desa/surat/bepergian")
    suspend fun createSuratBepergian(
        @Body request: SKBerpergianRequest
    ): Response<SKBerpergianResponse>

    @POST("/warga-desa/surat/ghaib")
    suspend fun createSuratGhaib(
        @Body request: SKGhaibRequest
    ): Response<SKGhaibResponse>

    @POST("/warga-desa/surat/domisili")
    suspend fun createSuratDomisili(
        @Body request: SKDomisiliRequest
    ): Response<SKDomisiliResponse>

    @POST("/warga-desa/surat/domisili-perusahaan")
    suspend fun createSuratDomisiliPerusahaan(
        @Body request: SKDomisiliPerusahaanRequest
    ): Response<SKDomisiliPerusahaanResponse>

    @POST("/warga-desa/surat/izin-tidak-kerja")
    suspend fun createSuratIzinTidakKerja(
        @Body request: SKIzinTidakMasukKerjaRequest
    ): Response<SKIzinTidakMasukKerjaResponse>

    @POST("/warga-desa/surat/janda-duda")
    suspend fun createSuratJandaDuda(
        @Body request: SKJandaDudaRequest
    ): Response<SKJandaDudaResponse>

    @POST("/warga-desa/surat/kehilangan")
    suspend fun createSuratKehilangan(
        @Body request: SPKehilanganRequest
    ): Response<SPKehilanganResponse>

    @POST("/warga-desa/surat/kelahiran")
    suspend fun createSuratKelahiran(
        @Body request: SKKelahiranRequest
    ): Response<SKKelahiranResponse>

    @POST("/warga-desa/surat/kematian")
    suspend fun createSuratKematian(
        @Body request: SKKematianRequest
    ): Response<SKKematianResponse>

    @POST("/warga-desa/surat/keramaian")
    suspend fun createSuratKeramaian(
        @Body request: SRKeramaianRequest
    ): Response<SRKeramaianResponse>

    @POST("/warga-desa/surat/kuasa")
    suspend fun createSuratKuasa(
        @Body request: SuratKuasaRequest
    ): Response<SuratKuasaResponse>

    @POST("/warga-desa/surat/penghasilan")
    suspend fun createSuratPenghasilan(
        @Body request: SKPenghasilanRequest
    ): Response<SKPenghasilanResponse>

    @POST("/warga-desa/surat/pernikahan")
    suspend fun createSuratPernikahan(
        @Body request: SPPernikahanRequest
    ): Response<SPPernikahanResponse>

    @POST("/warga-desa/surat/pindah-domisili")
    suspend fun createSuratPindahDomisili(
        @Body request: SPPindahDomisiliRequest
    ): Response<SPPindahDomisiliResponse>

    @POST("/warga-desa/surat/resi-ktp-sementara")
    suspend fun createResiKTPSementara(
        @Body request: SKResiKTPSementaraRequest
    ): Response<SKResiKTPSementaraResponse>

    @POST("/warga-desa/surat/skck")
    suspend fun createSuratSKCK(
        @Body request: SPCatatanKepolisianRequest
    ): Response<SPCatatanKepolisianResponse>

    @POST("/warga-desa/surat/status-perkawinan")
    suspend fun createSuratStatusPerkawinan(
        @Body request: SKStatusPerkawinanRequest
    ): Response<SKStatusPerkawinanResponse>

    @POST("/warga-desa/surat/tidak-mampu")
    suspend fun createSuratTidakMampu(
        @Body request: SKTidakMampuRequest
    ): Response<SKTidakMampuResponse>

    @POST("/warga-desa/surat/usaha")
    suspend fun createSuratUsaha(
        @Body request: SKUsahaRequest
    ): Response<SKUsahaResponse>

    @POST("/warga-desa/surat/tugas")
    suspend fun createSuratTugas(
        @Body request: SuratTugasRequest
    ): Response<SuratTugasResponse>
}