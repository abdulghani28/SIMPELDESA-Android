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

/**
 * API interface for handling file/media operations, such as uploading and downloading files.
 *
 * This API supports:
 * - Downloading a file by its ID (e.g., image, video, document)
 * - Uploading a file via multipart/form-data
 *
 * Integration:
 * - Image downloads can be displayed directly using RemoteImage or Coil's ImageLoader
 * - Uploaded files are handled via a standard backend that returns a file response
 */
interface FileApi {

    /**
     * Downloads a file by its unique ID.
     *
     * @param id The unique identifier of the file stored on the server.
     * @return A [Response] containing the raw [ResponseBody], which can be processed
     *         as a stream, saved to disk, or displayed using image loaders.
     *
     * Use case:
     * - Display image: use this URL with Coil's ImageLoader or RemoteImage
     * - Download file: write [ResponseBody] to local storage for offline access
     */
    @GET("client-file/{id}")
    suspend fun getFile(@Path("id") id: String): Response<ResponseBody>

    /**
     * Uploads a file to the server using multipart/form-data.
     *
     * @param file The [MultipartBody.Part] representing the file to upload.
     *             Typically created with content type such as "image/jpeg", "video/mp4", etc.
     * @return A [Response] containing [FileUploadResponse] which includes metadata like file URL or ID.
     *
     * Use case:
     * - Upload media from camera/gallery to server
     * - Used for profile picture, attachments, documents, etc.
     */
    @Multipart
    @POST("/client-file")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<FileUploadResponse>
}