package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.statusperkawinan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase

class SKStatusPerkawinanDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) {
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
    var isLoadingAgama by mutableStateOf(false)
    var agamaErrorMessage by mutableStateOf<String?>(null)

    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
    var isLoadingStatusKawin by mutableStateOf(false)
    var statusKawinErrorMessage by mutableStateOf<String?>(null)

    suspend fun loadUserData(
        stateManager: SKStatusPerkawinanStateManager,
        validator: SKStatusPerkawinanValidator,
        onSuccess: () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        stateManager.setUserDataLoading(true)
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    stateManager.populateUserData(userData)

                    // Clear validation errors for populated fields
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "pekerjaan", "alamat", "status_kawin_id",
                        "agama_id"
                    ))
                    onSuccess()
                }
                is UserInfoResult.Error -> {
                    stateManager.setUseMyData(false)
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data pengguna"
            stateManager.setUseMyData(false)
            onError(message)
        } finally {
            stateManager.setUserDataLoading(false)
        }
    }

    suspend fun loadAgama(onError: suspend (String) -> Unit) {
        isLoadingAgama = true
        agamaErrorMessage = null
        try {
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    agamaList = result.data.data
                }
                is AgamaResult.Error -> {
                    agamaErrorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data agama"
            agamaErrorMessage = message
            onError(message)
        } finally {
            isLoadingAgama = false
        }
    }

    suspend fun loadStatusKawin(onError: suspend (String) -> Unit) {
        isLoadingStatusKawin = true
        statusKawinErrorMessage = null
        try {
            when (val result = getStatusKawinUseCase()) {
                is StatusKawinResult.Success -> {
                    statusKawinList = result.data.data
                }
                is StatusKawinResult.Error -> {
                    statusKawinErrorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data status kawin"
            statusKawinErrorMessage = message
            onError(message)
        } finally {
            isLoadingStatusKawin = false
        }
    }
}