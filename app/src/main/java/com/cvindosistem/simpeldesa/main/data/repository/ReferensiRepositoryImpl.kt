package com.cvindosistem.simpeldesa.main.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.main.data.remote.api.ReferensiApi
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.BidangUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.DisahkanOlehResult
import com.cvindosistem.simpeldesa.main.domain.model.JenisUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.PerbedaanIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.TercantumIdentitasResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ReferensiRepository {
    suspend fun getTercantumIdentitas(): TercantumIdentitasResult
    suspend fun getPerbedaanIdentitas(): PerbedaanIdentitasResult
    suspend fun getDisahkanOleh(): DisahkanOlehResult
    suspend fun getAgama(): AgamaResult
    suspend fun getJenisUsaha(): JenisUsahaResult
    suspend fun getBidangUsaha(): BidangUsahaResult
    suspend fun getStatusKawin(): StatusKawinResult
}

class ReferensiRepositoryImpl(
    private val referensiApi: ReferensiApi
) : ReferensiRepository {

    override suspend fun getTercantumIdentitas(): TercantumIdentitasResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getTercantumIdentitas()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched tercantum identitas")
                    return@withContext TercantumIdentitasResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "Tercantum identitas response body is null")
                    return@withContext TercantumIdentitasResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch tercantum identitas"
                Log.e("ReferensiRepository", "Tercantum identitas failed: $errorMessage")
                return@withContext TercantumIdentitasResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "Tercantum identitas exception", e)
            return@withContext TercantumIdentitasResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getPerbedaanIdentitas(): PerbedaanIdentitasResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getPerbedaanIdentitas()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched perbedaan identitas")
                    return@withContext PerbedaanIdentitasResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "Perbedaan identitas response body is null")
                    return@withContext PerbedaanIdentitasResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch perbedaan identitas"
                Log.e("ReferensiRepository", "Perbedaan identitas failed: $errorMessage")
                return@withContext PerbedaanIdentitasResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "Perbedaan identitas exception", e)
            return@withContext PerbedaanIdentitasResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getDisahkanOleh(): DisahkanOlehResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getDisahkanOleh()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched disahkan oleh")
                    return@withContext DisahkanOlehResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "Disahkan oleh response body is null")
                    return@withContext DisahkanOlehResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch disahkan oleh"
                Log.e("ReferensiRepository", "Disahkan oleh failed: $errorMessage")
                return@withContext DisahkanOlehResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "Disahkan oleh exception", e)
            return@withContext DisahkanOlehResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getAgama(): AgamaResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getAgama()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched agama")
                    return@withContext AgamaResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "Agama response body is null")
                    return@withContext AgamaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch agama"
                Log.e("ReferensiRepository", "Agama failed: $errorMessage")
                return@withContext AgamaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "Agama exception", e)
            return@withContext AgamaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getJenisUsaha(): JenisUsahaResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getJenisUsaha()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched jenisUsaha")
                    return@withContext JenisUsahaResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "JenisUsaha response body is null")
                    return@withContext JenisUsahaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch jenisUsaha"
                Log.e("ReferensiRepository", "JenisUsaha failed: $errorMessage")
                return@withContext JenisUsahaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "JenisUsaha exception", e)
            return@withContext JenisUsahaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getBidangUsaha(): BidangUsahaResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getBidangUsaha()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched bidangUsaha")
                    return@withContext BidangUsahaResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "BidangUsaha response body is null")
                    return@withContext BidangUsahaResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch bidangUsaha"
                Log.e("ReferensiRepository", "BidangUsaha failed: $errorMessage")
                return@withContext BidangUsahaResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "BidangUsaha exception", e)
            return@withContext BidangUsahaResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getStatusKawin(): StatusKawinResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getStatusKawin()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched statusKawin")
                    return@withContext StatusKawinResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "StatusKawin response body is null")
                    return@withContext StatusKawinResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch statusKawin"
                Log.e("ReferensiRepository", "StatusKawin failed: $errorMessage")
                return@withContext StatusKawinResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "StatusKawin exception", e)
            return@withContext StatusKawinResult.Error(e.message ?: "Unknown error occurred")
        }
    }
}