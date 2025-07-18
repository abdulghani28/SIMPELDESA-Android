package com.cvindosistem.simpeldesa.main.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.main.data.remote.api.SuratApi
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBelumMemilikiPBBRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBerpergianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBiodataWargaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKGhaibRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinOrangTuaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJamkesosRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJandaDudaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJualBeliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKTPDalamProsesRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKelahiranRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKematianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKepemilikanKendaraanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKepemilikanTanahRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKLahirMatiRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKNikahWargaNonMuslimRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPenghasilanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPergiKawinRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKResiKTPSementaraRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKStatusPerkawinanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKTidakMampuRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKUsahaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKWaliHakimRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratKuasaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratTugasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPCatatanKepolisianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPermohonanPenerbitanBukuPasLintasBatasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPernikahanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPindahDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMAktaLahirRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMBelumMemilikiAktaLahirRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMDuplikatKelahiranRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMDuplikatSuratNikahRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMKartuKeluargaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMPerubahanKKRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpernyataan.SPNPenguasaanFisikBidangTanahRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBiodataWargaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKIzinOrangTuaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKepemilikanTanahResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKNikahNonMuslimResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPPermohonanPenerbitanBukuPasLintasBatasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMKartuKeluargaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMPerubahanKKResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpernyataan.SPNPenguasaanFisikBidangTanahResponse
import com.cvindosistem.simpeldesa.main.domain.model.SKBiodataWargaResult
import com.cvindosistem.simpeldesa.main.domain.model.SKIzinOrangTuaResult
import com.cvindosistem.simpeldesa.main.domain.model.SKKepemilikanTanahResult
import com.cvindosistem.simpeldesa.main.domain.model.SKNikahNonMuslimResult
import com.cvindosistem.simpeldesa.main.domain.model.SKPengantarCeraiRujukResult
import com.cvindosistem.simpeldesa.main.domain.model.SPMCeraiResult
import com.cvindosistem.simpeldesa.main.domain.model.SPMKartuKeluargaResult
import com.cvindosistem.simpeldesa.main.domain.model.SPMPerubahanKKResult
import com.cvindosistem.simpeldesa.main.domain.model.SPNPenguasaanFisikBidangTanahResult
import com.cvindosistem.simpeldesa.main.domain.model.SPPermohonanPenerbitanBukuPasLintasBatasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratAktaLahirResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBedaIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBelumMemilikiAktaLahirResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBelumMemilikiPBBResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBerpergianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDetailResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliPerusahaanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDuplikatKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDuplikatSuratNikahResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratGhaibResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratIzinTidakKerjaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJamkesosResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJandaDudaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJualBeliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKTPDalamProsesResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKehilanganResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKematianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKepemilikanKendaraanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKeramaianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKuasaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratLahirMatiResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratListResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPenghasilanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPergiKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPernikahanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPindahDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratResiKTPSementaraResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratSKCKResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratStatusPerkawinanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMampuResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMasukKerjaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTugasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratWaliHakimResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SuratRepository {
    suspend fun getSuratList(
        page: Int? = null,
        limit: Int? = null,
        search: String? = null,
        jenisSurat: String? = null,
        status: String? = null,
        startDate: String? = null,
        endDate: String? = null
    ): SuratListResult

    suspend fun getSuratDetail(id: String): SuratDetailResult

    suspend fun createSuratBedaIdentitas(
        request: SKBedaIdentitasRequest
    ): SuratBedaIdentitasResult

    suspend fun createSuratBepergian(
        request: SKBerpergianRequest
    ): SuratBerpergianResult

    suspend fun createSuratDomisili(
        request: SKDomisiliRequest
    ): SuratDomisiliResult

    suspend fun createSuratDomisiliPerusahaan(
        request: SKDomisiliPerusahaanRequest
    ): SuratDomisiliPerusahaanResult

    suspend fun createSuratTidakMasukKerja(
        request: SKIzinTidakMasukKerjaRequest
    ): SuratTidakMasukKerjaResult

    suspend fun createSuratJandaDuda(
        request: SKJandaDudaRequest
    ): SuratJandaDudaResult

    suspend fun createSuratGhaib(
        request: SKGhaibRequest
    ): SuratGhaibResult

    suspend fun createSuratKehilangan(
        request: SPKehilanganRequest
    ): SuratKehilanganResult

    suspend fun createSuratKelahiran(
        request: SKKelahiranRequest
    ): SuratKelahiranResult

    suspend fun createSuratKematian(
        request: SKKematianRequest
    ): SuratKematianResult

    suspend fun createSuratKeramaian(
        request: SRKeramaianRequest
    ): SuratKeramaianResult

    suspend fun createSuratKuasa(
        request: SuratKuasaRequest
    ): SuratKuasaResult

    suspend fun createSuratPenghasilan(
        request: SKPenghasilanRequest
    ): SuratPenghasilanResult

    suspend fun createSuratPernikahan(
        request: SPPernikahanRequest
    ): SuratPernikahanResult

    suspend fun createSuratPindahDomisili(
        request: SPPindahDomisiliRequest
    ): SuratPindahDomisiliResult

    suspend fun createResiKTPSementara(
        request: SKResiKTPSementaraRequest
    ): SuratResiKTPSementaraResult

    suspend fun createSuratSKCK(
        request: SPCatatanKepolisianRequest
    ): SuratSKCKResult

    suspend fun createSuratStatusPerkawinan(
        request: SKStatusPerkawinanRequest
    ): SuratStatusPerkawinanResult

    suspend fun createSuratTidakMampu(
        request: SKTidakMampuRequest
    ): SuratTidakMampuResult

    suspend fun createSuratUsaha(
        request: SKUsahaRequest
    ): SuratUsahaResult

    suspend fun createSuratTugas(
        request: SuratTugasRequest
    ): SuratTugasResult

    suspend fun createSuratIzinTidakKerja(
        request: SKIzinTidakMasukKerjaRequest
    ): SuratIzinTidakKerjaResult

    suspend fun createSuratBelumMemilikiPBB(
        request: SKBelumMemilikiPBBRequest
    ): SuratBelumMemilikiPBBResult

    suspend fun createSuratJamkesos(
        request: SKJamkesosRequest
    ): SuratJamkesosResult

    suspend fun createSuratJualBeli(
        request: SKJualBeliRequest
    ): SuratJualBeliResult

    suspend fun createSuratKTPDalamProses(
        request: SKKTPDalamProsesRequest
    ): SuratKTPDalamProsesResult

    suspend fun createSuratLahirMati(
        request: SKLahirMatiRequest
    ): SuratLahirMatiResult

    suspend fun createSuratPergiKawin(
        request: SKPergiKawinRequest
    ): SuratPergiKawinResult

    suspend fun createSuratWaliHakim(
        request: SKWaliHakimRequest
    ): SuratWaliHakimResult

    suspend fun createSuratKepemilikanKendaraan(
        request: SKKepemilikanKendaraanRequest
    ): SuratKepemilikanKendaraanResult

    suspend fun createSuratAktaLahir(
        request: SPMAktaLahirRequest
    ): SuratAktaLahirResult

    suspend fun createSuratBelumMemilikiAktaLahir(
        request: SPMBelumMemilikiAktaLahirRequest
    ): SuratBelumMemilikiAktaLahirResult

    suspend fun createSuratDuplikatKelahiran(
        request: SPMDuplikatKelahiranRequest
    ): SuratDuplikatKelahiranResult

    suspend fun createSuratDuplikatSuratNikah(
        request: SPMDuplikatSuratNikahRequest
    ): SuratDuplikatSuratNikahResult

    suspend fun createSuratPermohonanCerai(
        request: SPMCeraiRequest
    ): SPMCeraiResult

    suspend fun createSuratPengantarCeraiRujuk(
        request: SKPengantarCeraiRujukRequest
    ): SKPengantarCeraiRujukResult

    suspend fun createSuratPermohonanKartuKeluarga(
        request: SPMKartuKeluargaRequest
    ): SPMKartuKeluargaResult

    suspend fun createSuratKeteranganIzinOrangTua(
        request: SKIzinOrangTuaRequest
    ): SKIzinOrangTuaResult

    suspend fun createSuratPernyataanSporadik(
        request: SPNPenguasaanFisikBidangTanahRequest
    ): SPNPenguasaanFisikBidangTanahResult

    suspend fun createSuratPermohonanPerubahanKK(
        request: SPMPerubahanKKRequest
    ): SPMPerubahanKKResult

    suspend fun createSuratKeteranganKepemilikanTanah(
        request: SKKepemilikanTanahRequest
    ): SKKepemilikanTanahResult

    suspend fun createSuratKeteranganBiodataWarga(
        request: SKBiodataWargaRequest
    ): SKBiodataWargaResult

    suspend fun createSuratPengantarPasLintasBatas(
        request: SPPermohonanPenerbitanBukuPasLintasBatasRequest
    ): SPPermohonanPenerbitanBukuPasLintasBatasResult

    suspend fun createSuratKeteranganNikahNonMuslim(
        request: SKNikahWargaNonMuslimRequest
    ): SKNikahNonMuslimResult
}

class SuratRepositoryImpl(
    private val suratApi: SuratApi
) : SuratRepository {

    override suspend fun getSuratList(
        page: Int?,
        limit: Int?,
        search: String?,
        jenisSurat: String?,
        status: String?,
        startDate: String?,
        endDate: String?
    ): SuratListResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.getSuratList(page, limit, search, jenisSurat, status, startDate, endDate)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Fetched surat list")
                    return@withContext SuratListResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat list response body is null")
                    return@withContext SuratListResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch surat list"
                Log.e("SuratRepository", "Surat list failed: $errorMessage")
                return@withContext SuratListResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat list exception", e)
            return@withContext SuratListResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getSuratDetail(id: String): SuratDetailResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.getSuratDetail(id)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Fetched surat detail for id: $id")
                    return@withContext SuratDetailResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat detail response body is null")
                    return@withContext SuratDetailResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch surat detail"
                Log.e("SuratRepository", "Surat detail failed: $errorMessage")
                return@withContext SuratDetailResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat detail exception", e)
            return@withContext SuratDetailResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratBedaIdentitas(
        request: SKBedaIdentitasRequest
    ): SuratBedaIdentitasResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratBedaIdentitas(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat beda identitas")
                    return@withContext SuratBedaIdentitasResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat beda identitas response body is null")
                    return@withContext SuratBedaIdentitasResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat beda identitas"
                Log.e("SuratRepository", "Surat beda identitas failed: $errorMessage")
                return@withContext SuratBedaIdentitasResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat beda identitas exception", e)
            return@withContext SuratBedaIdentitasResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratBepergian(
        request: SKBerpergianRequest
    ): SuratBerpergianResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratBepergian(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat bepergian")
                    return@withContext SuratBerpergianResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat bepergian response body is null")
                    return@withContext SuratBerpergianResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat bepergian"
                Log.e("SuratRepository", "Surat bepergian failed: $errorMessage")
                return@withContext SuratBerpergianResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat bepergian exception", e)
            return@withContext SuratBerpergianResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratDomisili(
        request: SKDomisiliRequest
    ): SuratDomisiliResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratDomisili(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat domisili")
                    return@withContext SuratDomisiliResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat domisili response body is null")
                    return@withContext SuratDomisiliResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat domisili"
                Log.e("SuratRepository", "Surat domisili failed: $errorMessage")
                return@withContext SuratDomisiliResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat domisili exception", e)
            return@withContext SuratDomisiliResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratDomisiliPerusahaan(
        request: SKDomisiliPerusahaanRequest
    ): SuratDomisiliPerusahaanResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratDomisiliPerusahaan(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat domisili perusahaan")
                    return@withContext SuratDomisiliPerusahaanResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat domisili perusahaan response body is null")
                    return@withContext SuratDomisiliPerusahaanResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat domisili perusahaan"
                Log.e("SuratRepository", "Surat domisili perusahaan failed: $errorMessage")
                return@withContext SuratDomisiliPerusahaanResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat domisili perusahaan exception", e)
            return@withContext SuratDomisiliPerusahaanResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratTidakMasukKerja(
        request: SKIzinTidakMasukKerjaRequest
    ): SuratTidakMasukKerjaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratIzinTidakKerja(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat tidakMasukKerja")
                    return@withContext SuratTidakMasukKerjaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat tidakMasukKerja response body is null")
                    return@withContext SuratTidakMasukKerjaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat tidakMasukKerja"
                Log.e("SuratRepository", "Surat tidakMasukKerja failed: $errorMessage")
                return@withContext SuratTidakMasukKerjaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat tidakMasukKerja exception", e)
            return@withContext SuratTidakMasukKerjaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratJandaDuda(
        request: SKJandaDudaRequest
    ): SuratJandaDudaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratJandaDuda(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat jandaDuda")
                    return@withContext SuratJandaDudaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat jandaDuda response body is null")
                    return@withContext SuratJandaDudaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat jandaDuda"
                Log.e("SuratRepository", "Surat jandaDuda failed: $errorMessage")
                return@withContext SuratJandaDudaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat jandaDuda exception", e)
            return@withContext SuratJandaDudaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratGhaib(
        request: SKGhaibRequest
    ): SuratGhaibResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratGhaib(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat ghaib")
                    return@withContext SuratGhaibResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat ghaib response body is null")
                    return@withContext SuratGhaibResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat ghaib"
                Log.e("SuratRepository", "Surat ghaib failed: $errorMessage")
                return@withContext SuratGhaibResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat ghaib exception", e)
            return@withContext SuratGhaibResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKehilangan(
        request: SPKehilanganRequest
    ): SuratKehilanganResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKehilangan(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat kehilangan")
                    return@withContext SuratKehilanganResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat kehilangan response body is null")
                    return@withContext SuratKehilanganResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat kehilangan"
                Log.e("SuratRepository", "Surat kehilangan failed: $errorMessage")
                return@withContext SuratKehilanganResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat kehilangan exception", e)
            return@withContext SuratKehilanganResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKelahiran(
        request: SKKelahiranRequest
    ): SuratKelahiranResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKelahiran(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat kelahiran")
                    return@withContext SuratKelahiranResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat kelahiran response body is null")
                    return@withContext SuratKelahiranResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat kelahiran"
                Log.e("SuratRepository", "Surat kelahiran failed: $errorMessage")
                return@withContext SuratKelahiranResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat kelahiran exception", e)
            return@withContext SuratKelahiranResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKematian(
        request: SKKematianRequest
    ): SuratKematianResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKematian(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat kematian")
                    return@withContext SuratKematianResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat kematian response body is null")
                    return@withContext SuratKematianResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat kematian"
                Log.e("SuratRepository", "Surat kematian failed: $errorMessage")
                return@withContext SuratKematianResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat kematian exception", e)
            return@withContext SuratKematianResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKeramaian(
        request: SRKeramaianRequest
    ): SuratKeramaianResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKeramaian(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat keramaian")
                    return@withContext SuratKeramaianResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat keramaian response body is null")
                    return@withContext SuratKeramaianResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat keramaian"
                Log.e("SuratRepository", "Surat keramaian failed: $errorMessage")
                return@withContext SuratKeramaianResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat keramaian exception", e)
            return@withContext SuratKeramaianResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKuasa(
        request: SuratKuasaRequest
    ): SuratKuasaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKuasa(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat kuasa")
                    return@withContext SuratKuasaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat kuasa response body is null")
                    return@withContext SuratKuasaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat kuasa"
                Log.e("SuratRepository", "Surat kuasa failed: $errorMessage")
                return@withContext SuratKuasaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat kuasa exception", e)
            return@withContext SuratKuasaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPenghasilan(
        request: SKPenghasilanRequest
    ): SuratPenghasilanResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPenghasilan(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat penghasilan")
                    return@withContext SuratPenghasilanResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat penghasilan response body is null")
                    return@withContext SuratPenghasilanResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat penghasilan"
                Log.e("SuratRepository", "Surat penghasilan failed: $errorMessage")
                return@withContext SuratPenghasilanResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat penghasilan exception", e)
            return@withContext SuratPenghasilanResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPernikahan(
        request: SPPernikahanRequest
    ): SuratPernikahanResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPernikahan(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat pernikahan")
                    return@withContext SuratPernikahanResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat pernikahan response body is null")
                    return@withContext SuratPernikahanResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat pernikahan"
                Log.e("SuratRepository", "Surat pernikahan failed: $errorMessage")
                return@withContext SuratPernikahanResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat pernikahan exception", e)
            return@withContext SuratPernikahanResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPindahDomisili(
        request: SPPindahDomisiliRequest
    ): SuratPindahDomisiliResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPindahDomisili(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat pindah domisili")
                    return@withContext SuratPindahDomisiliResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat pindah domisili response body is null")
                    return@withContext SuratPindahDomisiliResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat pindah domisili"
                Log.e("SuratRepository", "Surat pindah domisili failed: $errorMessage")
                return@withContext SuratPindahDomisiliResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat pindah domisili exception", e)
            return@withContext SuratPindahDomisiliResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createResiKTPSementara(
        request: SKResiKTPSementaraRequest
    ): SuratResiKTPSementaraResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createResiKTPSementara(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created resi KTP sementara")
                    return@withContext SuratResiKTPSementaraResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Resi KTP sementara response body is null")
                    return@withContext SuratResiKTPSementaraResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create resi KTP sementara"
                Log.e("SuratRepository", "Resi KTP sementara failed: $errorMessage")
                return@withContext SuratResiKTPSementaraResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Resi KTP sementara exception", e)
            return@withContext SuratResiKTPSementaraResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratSKCK(
        request: SPCatatanKepolisianRequest
    ): SuratSKCKResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratSKCK(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat SKCK")
                    return@withContext SuratSKCKResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat SKCK response body is null")
                    return@withContext SuratSKCKResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat SKCK"
                Log.e("SuratRepository", "Surat SKCK failed: $errorMessage")
                return@withContext SuratSKCKResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat SKCK exception", e)
            return@withContext SuratSKCKResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratStatusPerkawinan(
        request: SKStatusPerkawinanRequest
    ): SuratStatusPerkawinanResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratStatusPerkawinan(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat status perkawinan")
                    return@withContext SuratStatusPerkawinanResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat status perkawinan response body is null")
                    return@withContext SuratStatusPerkawinanResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat status perkawinan"
                Log.e("SuratRepository", "Surat status perkawinan failed: $errorMessage")
                return@withContext SuratStatusPerkawinanResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat status perkawinan exception", e)
            return@withContext SuratStatusPerkawinanResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratTidakMampu(
        request: SKTidakMampuRequest
    ): SuratTidakMampuResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratTidakMampu(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat tidak mampu")
                    return@withContext SuratTidakMampuResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat tidak mampu response body is null")
                    return@withContext SuratTidakMampuResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat tidak mampu"
                Log.e("SuratRepository", "Surat tidak mampu failed: $errorMessage")
                return@withContext SuratTidakMampuResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat tidak mampu exception", e)
            return@withContext SuratTidakMampuResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratUsaha(
        request: SKUsahaRequest
    ): SuratUsahaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratUsaha(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat usaha")
                    return@withContext SuratUsahaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat usaha response body is null")
                    return@withContext SuratUsahaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat usaha"
                Log.e("SuratRepository", "Surat usaha failed: $errorMessage")
                return@withContext SuratUsahaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat usaha exception", e)
            return@withContext SuratUsahaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratTugas(
        request: SuratTugasRequest
    ): SuratTugasResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratTugas(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat tugas")
                    return@withContext SuratTugasResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat tugas response body is null")
                    return@withContext SuratTugasResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat tugas"
                Log.e("SuratRepository", "Surat tugas failed: $errorMessage")
                return@withContext SuratTugasResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat tugas exception", e)
            return@withContext SuratTugasResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratIzinTidakKerja(
        request: SKIzinTidakMasukKerjaRequest
    ): SuratIzinTidakKerjaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratIzinTidakKerja(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat izin tidak kerja")
                    return@withContext SuratIzinTidakKerjaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat izin tidak kerja response body is null")
                    return@withContext SuratIzinTidakKerjaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat izin tidak kerja"
                Log.e("SuratRepository", "Surat izin tidak kerja failed: $errorMessage")
                return@withContext SuratIzinTidakKerjaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat izin tidak kerja exception", e)
            return@withContext SuratIzinTidakKerjaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratBelumMemilikiPBB(
        request: SKBelumMemilikiPBBRequest
    ): SuratBelumMemilikiPBBResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratBelumMemilikiPBB(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat belum memiliki PBB")
                    return@withContext SuratBelumMemilikiPBBResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat belum memiliki PBB response body is null")
                    return@withContext SuratBelumMemilikiPBBResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat belum memiliki PBB"
                Log.e("SuratRepository", "Surat belum memiliki PBB failed: $errorMessage")
                return@withContext SuratBelumMemilikiPBBResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat belum memiliki PBB exception", e)
            return@withContext SuratBelumMemilikiPBBResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratJamkesos(
        request: SKJamkesosRequest
    ): SuratJamkesosResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratJamkesos(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat jamkesos")
                    return@withContext SuratJamkesosResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat jamkesos response body is null")
                    return@withContext SuratJamkesosResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat jamkesos"
                Log.e("SuratRepository", "Surat jamkesos failed: $errorMessage")
                return@withContext SuratJamkesosResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat jamkesos exception", e)
            return@withContext SuratJamkesosResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratJualBeli(
        request: SKJualBeliRequest
    ): SuratJualBeliResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratJualBeli(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat jual beli")
                    return@withContext SuratJualBeliResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat jual beli response body is null")
                    return@withContext SuratJualBeliResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat jual beli"
                Log.e("SuratRepository", "Surat jual beli failed: $errorMessage")
                return@withContext SuratJualBeliResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat jual beli exception", e)
            return@withContext SuratJualBeliResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKTPDalamProses(
        request: SKKTPDalamProsesRequest
    ): SuratKTPDalamProsesResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKTPDalamProses(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat KTP dalam proses")
                    return@withContext SuratKTPDalamProsesResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat KTP dalam proses response body is null")
                    return@withContext SuratKTPDalamProsesResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat KTP dalam proses"
                Log.e("SuratRepository", "Surat KTP dalam proses failed: $errorMessage")
                return@withContext SuratKTPDalamProsesResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat KTP dalam proses exception", e)
            return@withContext SuratKTPDalamProsesResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratLahirMati(
        request: SKLahirMatiRequest
    ): SuratLahirMatiResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratLahirMati(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat LAHIR mati")
                    return@withContext SuratLahirMatiResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat LAHIR mati response body is null")
                    return@withContext SuratLahirMatiResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat LAHIR mati"
                Log.e("SuratRepository", "Surat LAHIR mati failed: $errorMessage")
                return@withContext SuratLahirMatiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat LAHIR mati exception", e)
            return@withContext SuratLahirMatiResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPergiKawin(
        request: SKPergiKawinRequest
    ): SuratPergiKawinResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPergiKawin(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Pergi Kawin")
                    return@withContext SuratPergiKawinResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Pergi Kawin response body is null")
                    return@withContext SuratPergiKawinResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Pergi Kawin"
                Log.e("SuratRepository", "Surat Pergi Kawin failed: $errorMessage")
                return@withContext SuratPergiKawinResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Pergi Kawin exception", e)
            return@withContext SuratPergiKawinResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratWaliHakim(
        request: SKWaliHakimRequest
    ): SuratWaliHakimResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratWaliHakim(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Wali Hakim")
                    return@withContext SuratWaliHakimResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Wali Hakim response body is null")
                    return@withContext SuratWaliHakimResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Wali Hakim"
                Log.e("SuratRepository", "Surat Wali Hakim failed: $errorMessage")
                return@withContext SuratWaliHakimResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Wali Hakim exception", e)
            return@withContext SuratWaliHakimResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKepemilikanKendaraan(
        request: SKKepemilikanKendaraanRequest
    ): SuratKepemilikanKendaraanResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKepemilikanKendaraan(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Kepemilikan Kendaraan")
                    return@withContext SuratKepemilikanKendaraanResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Kepemilikan Kendaraan response body is null")
                    return@withContext SuratKepemilikanKendaraanResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Kepemilikan Kendaraan"
                Log.e("SuratRepository", "Surat Kepemilikan Kendaraan failed: $errorMessage")
                return@withContext SuratKepemilikanKendaraanResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Kepemilikan Kendaraan exception", e)
            return@withContext SuratKepemilikanKendaraanResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratAktaLahir(
        request: SPMAktaLahirRequest
    ): SuratAktaLahirResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratAktaLahir(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Akta Lahir")
                    return@withContext SuratAktaLahirResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Akta Lahir response body is null")
                    return@withContext SuratAktaLahirResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Akta Lahir"
                Log.e("SuratRepository", "Surat Akta Lahir failed: $errorMessage")
                return@withContext SuratAktaLahirResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Akta Lahir exception", e)
            return@withContext SuratAktaLahirResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratBelumMemilikiAktaLahir(
        request: SPMBelumMemilikiAktaLahirRequest
    ): SuratBelumMemilikiAktaLahirResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratBelumMemilikiAktaLahir(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Belum Memiliki Akta Lahir")
                    return@withContext SuratBelumMemilikiAktaLahirResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Belum Memiliki Akta Lahir response body is null")
                    return@withContext SuratBelumMemilikiAktaLahirResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Belum Memiliki Akta Lahir"
                Log.e("SuratRepository", "Surat Belum Memiliki Akta Lahir failed: $errorMessage")
                return@withContext SuratBelumMemilikiAktaLahirResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Belum Memiliki Akta Lahir exception", e)
            return@withContext SuratBelumMemilikiAktaLahirResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratDuplikatKelahiran(
        request: SPMDuplikatKelahiranRequest
    ): SuratDuplikatKelahiranResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratDuplikatKelahiran(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Duplikat Kelahiran")
                    return@withContext SuratDuplikatKelahiranResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Duplikat Kelahiran response body is null")
                    return@withContext SuratDuplikatKelahiranResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Duplikat Kelahiran"
                Log.e("SuratRepository", "Surat Duplikat Kelahiran failed: $errorMessage")
                return@withContext SuratDuplikatKelahiranResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Duplikat Kelahiran exception", e)
            return@withContext SuratDuplikatKelahiranResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratDuplikatSuratNikah(
        request: SPMDuplikatSuratNikahRequest
    ): SuratDuplikatSuratNikahResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratDuplikatSuratNikah(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Duplikat Surat Nikah")
                    return@withContext SuratDuplikatSuratNikahResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Duplikat Surat Nikah response body is null")
                    return@withContext SuratDuplikatSuratNikahResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Duplikat Surat Nikah"
                Log.e("SuratRepository", "Surat Duplikat Surat Nikah failed: $errorMessage")
                return@withContext SuratDuplikatSuratNikahResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Duplikat Surat Nikah exception", e)
            return@withContext SuratDuplikatSuratNikahResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPermohonanCerai(
        request: SPMCeraiRequest
    ): SPMCeraiResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPermohonanCerai(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Permohonan Cerai")
                    return@withContext SPMCeraiResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Permohonan Cerai response body is null")
                    return@withContext SPMCeraiResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Permohonan Cerai"
                Log.e("SuratRepository", "Surat Permohonan Cerai failed: $errorMessage")
                return@withContext SPMCeraiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Permohonan Cerai exception", e)
            return@withContext SPMCeraiResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPengantarCeraiRujuk(
        request: SKPengantarCeraiRujukRequest
    ): SKPengantarCeraiRujukResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPengantarCeraiRujuk(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Pengantar Cerai Rujuk")
                    return@withContext SKPengantarCeraiRujukResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Pengantar Cerai Rujuk response body is null")
                    return@withContext SKPengantarCeraiRujukResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Pengantar Cerai Rujuk"
                Log.e("SuratRepository", "Surat Pengantar Cerai Rujuk failed: $errorMessage")
                return@withContext SKPengantarCeraiRujukResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Pengantar Cerai Rujuk exception", e)
            return@withContext SKPengantarCeraiRujukResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPermohonanKartuKeluarga(
        request: SPMKartuKeluargaRequest
    ): SPMKartuKeluargaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPermohonanKartuKeluarga(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Permohonan Kartu Keluarga")
                    return@withContext SPMKartuKeluargaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Permohonan Kartu Keluarga response body is null")
                    return@withContext SPMKartuKeluargaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Permohonan Kartu Keluarga"
                Log.e("SuratRepository", "Surat Permohonan Kartu Keluarga failed: $errorMessage")
                return@withContext SPMKartuKeluargaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Permohonan Kartu Keluarga exception", e)
            return@withContext SPMKartuKeluargaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKeteranganIzinOrangTua(
        request: SKIzinOrangTuaRequest
    ): SKIzinOrangTuaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKeteranganIzinOrangTua(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Keterangan Izin Orang Tua")
                    return@withContext SKIzinOrangTuaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Keterangan Izin Orang Tua response body is null")
                    return@withContext SKIzinOrangTuaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Keterangan Izin Orang Tua"
                Log.e("SuratRepository", "Surat Keterangan Izin Orang Tua failed: $errorMessage")
                return@withContext SKIzinOrangTuaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Keterangan Izin Orang Tua exception", e)
            return@withContext SKIzinOrangTuaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPernyataanSporadik(
        request: SPNPenguasaanFisikBidangTanahRequest
    ): SPNPenguasaanFisikBidangTanahResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPernyataanSporadik(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Pernyataan Sporadik")
                    return@withContext SPNPenguasaanFisikBidangTanahResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Pernyataan Sporadik response body is null")
                    return@withContext SPNPenguasaanFisikBidangTanahResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Pernyataan Sporadik"
                Log.e("SuratRepository", "Surat Pernyataan Sporadik failed: $errorMessage")
                return@withContext SPNPenguasaanFisikBidangTanahResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Pernyataan Sporadik exception", e)
            return@withContext SPNPenguasaanFisikBidangTanahResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPermohonanPerubahanKK(
        request: SPMPerubahanKKRequest
    ): SPMPerubahanKKResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPermohonanPerubahanKK(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Permohonan Perubahan KK")
                    return@withContext SPMPerubahanKKResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Permohonan Perubahan KK response body is null")
                    return@withContext SPMPerubahanKKResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Permohonan Perubahan KK"
                Log.e("SuratRepository", "Surat Permohonan Perubahan KK failed: $errorMessage")
                return@withContext SPMPerubahanKKResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Permohonan Perubahan KK exception", e)
            return@withContext SPMPerubahanKKResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKeteranganKepemilikanTanah(
        request: SKKepemilikanTanahRequest
    ): SKKepemilikanTanahResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKeteranganKepemilikanTanah(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Keterangan Kepemilikan Tanah")
                    return@withContext SKKepemilikanTanahResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Keterangan Kepemilikan Tanah response body is null")
                    return@withContext SKKepemilikanTanahResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Keterangan Kepemilikan Tanah"
                Log.e("SuratRepository", "Surat Keterangan Kepemilikan Tanah failed: $errorMessage")
                return@withContext SKKepemilikanTanahResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Keterangan Kepemilikan Tanah exception", e)
            return@withContext SKKepemilikanTanahResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKeteranganBiodataWarga(
        request: SKBiodataWargaRequest
    ): SKBiodataWargaResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKeteranganBiodataWarga(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Keterangan Biodata Warga")
                    return@withContext SKBiodataWargaResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Keterangan Biodata Warga response body is null")
                    return@withContext SKBiodataWargaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Keterangan Biodata Warga"
                Log.e("SuratRepository", "Surat Keterangan Biodata Warga failed: $errorMessage")
                return@withContext SKBiodataWargaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Keterangan Biodata Warga exception", e)
            return@withContext SKBiodataWargaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratPengantarPasLintasBatas(
        request: SPPermohonanPenerbitanBukuPasLintasBatasRequest
    ): SPPermohonanPenerbitanBukuPasLintasBatasResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratPengantarPasLintasBatas(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Pengantar Pas Lintas Batas")
                    return@withContext SPPermohonanPenerbitanBukuPasLintasBatasResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Pengantar Pas Lintas Batas response body is null")
                    return@withContext SPPermohonanPenerbitanBukuPasLintasBatasResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Pengantar Pas Lintas Batas"
                Log.e("SuratRepository", "Surat Pengantar Pas Lintas Batas failed: $errorMessage")
                return@withContext SPPermohonanPenerbitanBukuPasLintasBatasResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Pengantar Pas Lintas Batas exception", e)
            return@withContext SPPermohonanPenerbitanBukuPasLintasBatasResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createSuratKeteranganNikahNonMuslim(
        request: SKNikahWargaNonMuslimRequest
    ): SKNikahNonMuslimResult = withContext(Dispatchers.IO) {
        try {
            val response = suratApi.createSuratKeteranganNikahNonMuslim(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("SuratRepository", "Created surat Keterangan Nikah Non Muslim")
                    return@withContext SKNikahNonMuslimResult.Success(it)
                } ?: run {
                    Log.e("SuratRepository", "Surat Keterangan Nikah Non Muslim response body is null")
                    return@withContext SKNikahNonMuslimResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to create surat Keterangan Nikah Non Muslim"
                Log.e("SuratRepository", "Surat Keterangan Nikah Non Muslim failed: $errorMessage")
                return@withContext SKNikahNonMuslimResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("SuratRepository", "Surat Keterangan Nikah Non Muslim exception", e)
            return@withContext SKNikahNonMuslimResult.Error(e.message ?: "Unknown error occurred")
        }
    }
}