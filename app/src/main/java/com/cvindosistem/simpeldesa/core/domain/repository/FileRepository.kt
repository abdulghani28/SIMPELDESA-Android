package com.cvindosistem.simpeldesa.core.domain.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.core.data.remote.api.FileApi
import com.cvindosistem.simpeldesa.core.domain.model.FileUploadResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Interface untuk manajemen file (upload dan download).
 */
interface FileRepository {

    /**
     * Mengunduh file dari server berdasarkan ID file.
     * @param fileId ID file yang akan diambil dari server.
     * @return [Result] yang berisi ByteArray jika sukses, atau exception jika gagal.
     */
    suspend fun getFile(fileId: String): Result<ByteArray>

    /**
     * Mengunggah file ke server.
     * @param file File yang akan diunggah.
     * @param mimeType Jenis MIME dari file (contoh: "image/png", "video/mp4").
     * @return [FileUploadResult] yang berisi status sukses atau error detail.
     */
    suspend fun uploadFile(file: File, mimeType: String): FileUploadResult
}

/**
 * Implementasi [FileRepository] yang berinteraksi langsung dengan [FileApi].
 */
class FileRepositoryImpl(private val fileApi: FileApi) : FileRepository {

    /**
     * Mengambil file dari server berdasarkan ID.
     * @return ByteArray jika berhasil, atau exception dalam Result.failure jika gagal.
     */
    override suspend fun getFile(fileId: String): Result<ByteArray> = withContext(Dispatchers.IO) {
        try {
            val response = fileApi.getFile(fileId)

            if (response.isSuccessful) {
                val responseBody = response.body()
                    ?: return@withContext Result.failure(Exception("Empty response"))
                return@withContext Result.success(responseBody.bytes())
            } else {
                return@withContext Result.failure(Exception("Failed to load file: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("FileRepository", "Error loading file", e)
            return@withContext Result.failure(e)
        }
    }

    /**
     * Upload file ke server dengan multipart.
     * - Memproses file menjadi [MultipartBody.Part]
     * - Menggunakan [FileApi.uploadFile] untuk upload
     * - Menangani respons sukses dan gagal dengan logging dan parsing pesan error
     */
    override suspend fun uploadFile(file: File, mimeType: String): FileUploadResult =
        withContext(Dispatchers.IO) {
            try {
                Log.d("FileRepository", "Preparing file for upload: ${file.name} with mimeType: $mimeType")

                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val response = fileApi.uploadFile(filePart)

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("FileRepository", "Upload successful. File ID: ${it.data.id}, createdAt: ${it.data.created_at}")
                        return@withContext FileUploadResult.Success(it)
                    } ?: run {
                        Log.e("FileRepository", "Upload response body is null")
                        return@withContext FileUploadResult.Error("Unknown error: response body null")
                    }
                } else {
                    // Parsing body error jika mungkin
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = try {
                        Gson().fromJson(errorBody, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        Log.e("FileRepository", "Failed to parse error response", e)
                        null
                    }

                    val errorMessage = errorResponse?.message ?: "Failed to upload file"
                    Log.e("FileRepository", "Upload failed. Code: ${response.code()}, Message: $errorMessage, Raw: $errorBody")
                    return@withContext FileUploadResult.Error(errorMessage)
                }
            } catch (e: Exception) {
                Log.e("FileRepository", "Upload exception: ${e.localizedMessage}", e)
                return@withContext FileUploadResult.Error(e.message ?: "Unknown error occurred")
            }
        }
}