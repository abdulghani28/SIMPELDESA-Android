package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SKKematianDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SKKematianStateManager,
    private val validator: SKKematianValidator,
    private val coroutineScope: CoroutineScope
) {
    var useMyDataChecked by mutableStateOf(false)
        private set

    var isLoadingUserData by mutableStateOf(false)
        private set

    private val _events = MutableSharedFlow<DataLoaderEvent>()
    val events = _events.asSharedFlow()

    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
        if (checked) {
            loadUserData()
        } else {
            clearUserData()
        }
    }

    private fun loadUserData() {
        coroutineScope.launch {
            isLoadingUserData = true
            try {
                when (val result = getUserInfoUseCase()) {
                    is UserInfoResult.Success -> {
                        val userData = result.data.data
                        stateManager.updateNama(userData.nama_warga)
                        stateManager.updateAlamat(userData.alamat)

                        // Clear validation errors for filled fields
                        validator.clearMultipleFieldErrors(listOf("nama", "alamat"))

                        _events.emit(DataLoaderEvent.UserDataLoaded)
                    }
                    is UserInfoResult.Error -> {
                        useMyDataChecked = false
                        _events.emit(DataLoaderEvent.LoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                useMyDataChecked = false
                val message = e.message ?: "Gagal memuat data pengguna"
                _events.emit(DataLoaderEvent.LoadError(message))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        stateManager.updateNama("")
        stateManager.updateAlamat("")
    }

    sealed class DataLoaderEvent {
        data object UserDataLoaded : DataLoaderEvent()
        data class LoadError(val message: String) : DataLoaderEvent()
    }
}