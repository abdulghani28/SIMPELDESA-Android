package com.cvindosistem.simpeldesa.main.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.main.data.remote.api.ReferensiApi
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.BidangUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.DisahkanOlehResult
import com.cvindosistem.simpeldesa.main.domain.model.HubunganResult
import com.cvindosistem.simpeldesa.main.domain.model.JenisUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.PendidikanResult
import com.cvindosistem.simpeldesa.main.domain.model.PerbedaanIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.TercantumIdentitasResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository untuk mengambil data referensi yang digunakan dalam berbagai form dan modul aplikasi.
 *
 * Setiap fungsi dalam repository ini mewakili permintaan data referensi dari API yang bersifat statis
 * seperti agama, status kawin, jenis usaha, dan lainnya.
 */
interface ReferensiRepository {

    /**
     * Mengambil daftar referensi keterangan identitas tercantum.
     * @return [TercantumIdentitasResult] berisi data atau pesan kesalahan.
     */
    suspend fun getTercantumIdentitas(): TercantumIdentitasResult

    /**
     * Mengambil daftar referensi perbedaan identitas.
     * @return [PerbedaanIdentitasResult] berisi data atau pesan kesalahan.
     */
    suspend fun getPerbedaanIdentitas(): PerbedaanIdentitasResult

    /**
     * Mengambil daftar referensi pihak yang mengesahkan dokumen.
     * @return [DisahkanOlehResult] berisi data atau pesan kesalahan.
     */
    suspend fun getDisahkanOleh(): DisahkanOlehResult

    /**
     * Mengambil daftar referensi agama.
     * @return [AgamaResult] berisi data atau pesan kesalahan.
     */
    suspend fun getAgama(): AgamaResult

    /**
     * Mengambil daftar referensi jenis usaha.
     * @return [JenisUsahaResult] berisi data atau pesan kesalahan.
     */
    suspend fun getJenisUsaha(): JenisUsahaResult

    /**
     * Mengambil daftar referensi bidang usaha.
     * @return [BidangUsahaResult] berisi data atau pesan kesalahan.
     */
    suspend fun getBidangUsaha(): BidangUsahaResult

    /**
     * Mengambil daftar referensi status perkawinan.
     * @return [StatusKawinResult] berisi data atau pesan kesalahan.
     */
    suspend fun getStatusKawin(): StatusKawinResult

    /**
     * Mengambil daftar referensi status perkawinan.
     * @return [PendidikanResult] berisi data atau pesan kesalahan.
     */
    suspend fun getPendidikan(): PendidikanResult

    /**
     * Mengambil daftar referensi status perkawinan.
     * @return [HubunganResult] berisi data atau pesan kesalahan.
     */
    suspend fun getHubungan(): HubunganResult
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
                } catch (_: Exception) {
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

    override suspend fun getPendidikan(): PendidikanResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getPendidikan()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched Pendidikan")
                    return@withContext PendidikanResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "Pendidikan response body is null")
                    return@withContext PendidikanResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch Pendidikan"
                Log.e("ReferensiRepository", "Pendidikan failed: $errorMessage")
                return@withContext PendidikanResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "Pendidikan exception", e)
            return@withContext PendidikanResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getHubungan(): HubunganResult = withContext(Dispatchers.IO) {
        try {
            val response = referensiApi.getHubungan()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("ReferensiRepository", "Fetched Hubungan")
                    return@withContext HubunganResult.Success(it)
                } ?: run {
                    Log.e("ReferensiRepository", "Hubungan response body is null")
                    return@withContext HubunganResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch Hubungan"
                Log.e("ReferensiRepository", "Hubungan failed: $errorMessage")
                return@withContext HubunganResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("ReferensiRepository", "Hubungan exception", e)
            return@withContext HubunganResult.Error(e.message ?: "Unknown error occurred")
        }
    }
}