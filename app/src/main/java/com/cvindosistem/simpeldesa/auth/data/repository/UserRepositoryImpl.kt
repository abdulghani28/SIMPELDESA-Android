package com.cvindosistem.simpeldesa.auth.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.api.UserApi
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Abstraksi untuk operasi terkait data pengguna (user).
 * Biasanya digunakan untuk mengambil informasi pengguna, organisasi, atau melakukan update profil.
 */
interface UserRepository {

    /**
     * Mengambil informasi pengguna yang sedang login.
     *
     * @return [UserInfoResult] hasil permintaan data user (berhasil/gagal)
     */
    suspend fun getUserInfo(): UserInfoResult

//    /**
//     * Mengambil informasi organisasi tempat pengguna terdaftar.
//     *
//     * @return [OrganizationInfoResult] hasil permintaan data organisasi
//     */
//    suspend fun getOrganizationInfo(): OrganizationInfoResult
//
//    /**
//     * Mengirim permintaan untuk mengubah data profil pengguna.
//     *
//     * @param request data baru profil pengguna
//     * @return [EditProfileResult] hasil update profil
//     */
//    suspend fun editProfile(request: EditProfileRequest): EditProfileResult
}

/**
 * Implementasi dari [UserRepository] menggunakan [UserApi] (biasanya Retrofit) untuk
 * komunikasi dengan server.
 */
class UserRepositoryImpl(
    private val authApi: UserApi
) : UserRepository {

    /**
     * Mengambil informasi pengguna dari endpoint `getUserInfo()`.
     * Biasanya digunakan saat login atau saat membuka halaman profil.
     */
    override suspend fun getUserInfo(): UserInfoResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.getUserInfo()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("AuthRepository", "Fetched user info")
                    return@withContext UserInfoResult.Success(it)
                }
                Log.e("AuthRepository", "User info response body is null")
                return@withContext UserInfoResult.Error("Unknown error occurred")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (_: Exception) {
                    null
                }

                val errorMessage = errorResponse?.message ?: "Failed to fetch user info"
                Log.e("AuthRepository", "User info failed: $errorMessage")
                return@withContext UserInfoResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "User info exception", e)
            return@withContext UserInfoResult.Error(e.message ?: "Unknown error occurred")
        }
    }

//    /**
//     * Mengambil informasi organisasi pengguna, misalnya nama instansi atau peran pengguna.
//     */
//    override suspend fun getOrganizationInfo(): OrganizationInfoResult = withContext(Dispatchers.IO) {
//        try {
//            val response = authApi.getOrganizationInfo()
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Log.d("AuthRepository", "Fetched organization info")
//                    return@withContext OrganizationInfoResult.Success(it)
//                }
//                Log.e("AuthRepository", "Organization info response body is null")
//                return@withContext OrganizationInfoResult.Error("Unknown error occurred")
//            } else {
//                val errorBody = response.errorBody()?.string()
//                val errorResponse = try {
//                    Gson().fromJson(errorBody, ErrorResponse::class.java)
//                } catch (_: Exception) { null }
//
//                val errorMessage = errorResponse?.message ?: "Failed to fetch organization info"
//                Log.e("AuthRepository", "Organization info failed: $errorMessage")
//                return@withContext OrganizationInfoResult.Error(errorMessage)
//            }
//        } catch (e: Exception) {
//            Log.e("AuthRepository", "Organization info exception", e)
//            return@withContext OrganizationInfoResult.Error(e.message ?: "Unknown error occurred")
//        }
//    }
//
//    /**
//     * Mengedit profil pengguna, biasanya setelah pengguna mengubah data dirinya.
//     */
//    override suspend fun editProfile(request: EditProfileRequest): EditProfileResult = withContext(Dispatchers.IO) {
//        try {
//            val response = authApi.editProfile(request)
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Log.d("AuthRepository", "Successfully edited profile")
//                    return@withContext EditProfileResult.Success(it)
//                }
//                Log.e("AuthRepository", "Edit profile response body is null")
//                return@withContext EditProfileResult.Error("Unknown error occurred")
//            } else {
//                val errorBody = response.errorBody()?.string()
//                val errorResponse = try {
//                    Gson().fromJson(errorBody, ErrorResponse::class.java)
//                } catch (_: Exception) { null }
//
//                val errorMessage = errorResponse?.message ?: "Failed to edit profile"
//                Log.e("AuthRepository", "Edit profile failed: $errorMessage")
//                return@withContext EditProfileResult.Error(errorMessage)
//            }
//        } catch (e: Exception) {
//            Log.e("AuthRepository", "Edit profile exception", e)
//            return@withContext EditProfileResult.Error(e.message ?: "Unknown error occurred")
//        }
//    }
}