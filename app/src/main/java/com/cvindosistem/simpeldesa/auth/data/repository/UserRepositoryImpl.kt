package com.cvindosistem.simpeldesa.auth.data.repository

import android.util.Log
import com.cvindosistem.simpeldesa.auth.data.remote.api.UserApi
import com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login.ErrorResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun getUserInfo(): UserInfoResult
//    suspend fun getOrganizationInfo(): OrganizationInfoResult
//    suspend fun editProfile(request: EditProfileRequest): EditProfileResult
}

class UserRepositoryImpl(
    private val authApi: UserApi
) : UserRepository {

    override suspend fun getUserInfo(): UserInfoResult = withContext(Dispatchers.IO) {
        try {
            val response = authApi.getUserInfo()

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("AuthRepository", "Fetched user info")
                    return@withContext UserInfoResult.Success(it)
                } ?: run {
                    Log.e("AuthRepository", "User info response body is null")
                    return@withContext UserInfoResult.Error("Unknown error occurred")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
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

//    override suspend fun getOrganizationInfo(): OrganizationInfoResult = withContext(Dispatchers.IO) {
//        try {
//            val response = authApi.getOrganizationInfo()
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Log.d("AuthRepository", "Fetched organization info")
//                    return@withContext OrganizationInfoResult.Success(it)
//                } ?: run {
//                    Log.e("AuthRepository", "Organization info response body is null")
//                    return@withContext OrganizationInfoResult.Error("Unknown error occurred")
//                }
//            } else {
//                val errorBody = response.errorBody()?.string()
//                val errorResponse = try {
//                    Gson().fromJson(errorBody, ErrorResponse::class.java)
//                } catch (e: Exception) {
//                    null
//                }
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
//    override suspend fun editProfile(request: EditProfileRequest): EditProfileResult = withContext(Dispatchers.IO) {
//        try {
//            val response = authApi.editProfile(request)
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Log.d("AuthRepository", "Successfully edited profile")
//                    return@withContext EditProfileResult.Success(it)
//                } ?: run {
//                    Log.e("AuthRepository", "Edit profile response body is null")
//                    return@withContext EditProfileResult.Error("Unknown error occurred")
//                }
//            } else {
//                val errorBody = response.errorBody()?.string()
//                val errorResponse = try {
//                    Gson().fromJson(errorBody, ErrorResponse::class.java)
//                } catch (e: Exception) {
//                    null
//                }
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