package com.cvindosistem.simpeldesa.core.data.remote.api

import com.cvindosistem.simpeldesa.core.domain.model.FileUploadResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FileApi {
    @GET("client-file/{id}")
    suspend fun getFile(@Path("id") id: String): Response<ResponseBody>

    @Multipart
    @POST("/client-file")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<FileUploadResponse>
}