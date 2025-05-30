package com.cvindosistem.simpeldesa.main.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.main.data.remote.api.SuratApi
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
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratKuasaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratTugasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPCatatanKepolisianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPernikahanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPindahDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratBedaIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBerpergianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDetailResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliPerusahaanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratGhaibResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratIzinTidakKerjaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJandaDudaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKehilanganResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKematianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKeramaianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKuasaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratListResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPenghasilanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPernikahanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPindahDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratResiKTPSementaraResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratSKCKResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratStatusPerkawinanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMampuResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMasukKerjaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTugasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratUsahaResult
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
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
}