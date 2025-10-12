package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.berpergian

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat

class SKBerpergianDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {
    suspend fun loadUserData(): Result<UserDataInfo> {
        return try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    Result.success(
                        UserDataInfo(
                            nik = userData.nik ?: "",
                            nama = userData.nama_warga ?: "",
                            tempatLahir = userData.tempat_lahir ?: "",
                            tanggalLahir = dateFormatterToApiFormat(userData.tanggal_lahir ?: ""),
                            jenisKelamin = userData.jenis_kelamin ?: "",
                            pekerjaan = userData.pekerjaan ?: "",
                            alamat = userData.alamat ?: ""
                        )
                    )
                }
                is UserInfoResult.Error -> {
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    data class UserDataInfo(
        val nik: String,
        val nama: String,
        val tempatLahir: String,
        val tanggalLahir: String,
        val jenisKelamin: String,
        val pekerjaan: String,
        val alamat: String
    )
}