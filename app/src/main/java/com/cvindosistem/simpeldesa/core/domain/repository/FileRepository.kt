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

interface FileRepository {
    suspend fun getFile(fileId: String): Result<ByteArray>
    suspend fun uploadFile(file: File, mimeType: String): FileUploadResult
}

class FileRepositoryImpl(private val fileApi: FileApi) : FileRepository {
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

    override suspend fun uploadFile(file: File, mimeType: String): FileUploadResult =
        withContext(Dispatchers.IO) {
            try {
                Log.d(
                    "FileRepository",
                    "Preparing file for upload: ${file.name} with mimeType: $mimeType"
                )

                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val response = fileApi.uploadFile(filePart)

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(
                            "FileRepository",
                            "Upload successful. File ID: ${it.data.id}, createdAt: ${it.data.created_at}"
                        )
                        return@withContext FileUploadResult.Success(it)
                    } ?: run {
                        Log.e("FileRepository", "Upload response body is null")
                        return@withContext FileUploadResult.Error("Unknown error: response body null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = try {
                        Gson().fromJson(errorBody, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        Log.e("FileRepository", "Failed to parse error response", e)
                        null
                    }

                    val errorMessage = errorResponse?.message ?: "Failed to upload file"
                    Log.e(
                        "FileRepository",
                        "Upload failed. Code: ${response.code()}, Message: $errorMessage, Raw: $errorBody"
                    )
                    return@withContext FileUploadResult.Error(errorMessage)
                }
            } catch (e: Exception) {
                Log.e("FileRepository", "Upload exception: ${e.localizedMessage}", e)
                return@withContext FileUploadResult.Error(e.message ?: "Unknown error occurred")
            }
        }
}