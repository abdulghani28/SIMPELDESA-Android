package com.cvindosistem.simpeldesa.main.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.main.data.remote.api.SuratApi
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBerpergianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJandaDudaRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratBedaIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBerpergianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDetailResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliPerusahaanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJandaDudaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratListResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMasukKerjaResult
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
}