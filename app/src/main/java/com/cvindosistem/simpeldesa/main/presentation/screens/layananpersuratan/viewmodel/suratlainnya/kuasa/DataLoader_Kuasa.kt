package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.kuasa

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SuratKuasaDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {

    suspend fun loadUserData(): UserDataResult {
        return try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    UserDataResult.Success(
                        nik = userData.nik ?: "",
                        nama = userData.nama_warga ?: ""
                    )
                }
                is UserInfoResult.Error -> {
                    UserDataResult.Error(result.message)
                }
            }
        } catch (e: Exception) {
            UserDataResult.Error(e.message ?: "Gagal memuat data pengguna")
        }
    }

    sealed class UserDataResult {
        data class Success(
            val nik: String,
            val nama: String
        ) : UserDataResult()

        data class Error(val message: String) : UserDataResult()
    }
}