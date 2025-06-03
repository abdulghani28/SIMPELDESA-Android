package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase

class SKDomisiliDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val stateManager: SKDomisiliStateManager,
    private val validator: SKDomisiliValidator
) {
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
    var isLoadingAgama by mutableStateOf(false)
    var agamaErrorMessage by mutableStateOf<String?>(null)
    var isLoadingUserData by mutableStateOf(false)

    suspend fun loadUserData(): Result<Unit> {
        return try {
            isLoadingUserData = true
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "pekerjaan", "alamat", "agama_id"
                    ))
                    Result.success(Unit)
                }
                is UserInfoResult.Error -> {
                    stateManager.updateUseMyData(false)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            stateManager.updateUseMyData(false)
            Result.failure(e)
        } finally {
            isLoadingUserData = false
        }
    }

    suspend fun loadAgama(): Result<Unit> {
        return try {
            isLoadingAgama = true
            agamaErrorMessage = null
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    agamaList = result.data.data
                    Result.success(Unit)
                }
                is AgamaResult.Error -> {
                    agamaErrorMessage = result.message
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            agamaErrorMessage = e.message ?: "Gagal memuat data agama"
            Result.failure(e)
        } finally {
            isLoadingAgama = false
        }
    }
}