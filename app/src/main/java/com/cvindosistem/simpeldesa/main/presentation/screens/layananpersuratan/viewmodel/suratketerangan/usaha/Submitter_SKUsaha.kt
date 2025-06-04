package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.usaha

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKUsahaRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratUsahaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratUsahaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SKUsahaFormSubmitter(
    private val createSuratUsahaUseCase: CreateSuratUsahaUseCase,
    private val stateManager: SKUsahaStateManager
) {
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _events = MutableSharedFlow<SubmitEvent>()
    val events = _events.asSharedFlow()

    suspend fun submitForm() {
        isLoading = true
        errorMessage = null

        try {
            val request = SKUsahaRequest(
                alamat = stateManager.getCurrentAlamat(),
                alamat_usaha = stateManager.getCurrentAlamatUsaha(),
                bidang_usaha_id = stateManager.getCurrentBidangUsaha(),
                disahkan_oleh = "", // Set by backend
                jenis_kelamin = stateManager.getCurrentGender(),
                jenis_usaha_id = stateManager.getCurrentJenisUsaha(),
                keperluan = stateManager.getCurrentKeperluan(),
                nama = stateManager.getCurrentNama(),
                nama_usaha = stateManager.getCurrentNamaUsaha(),
                nik = stateManager.getCurrentNik(),
                npwp = stateManager.getCurrentNpwp(),
                pekerjaan = stateManager.getCurrentPekerjaan(),
                tanggal_lahir = stateManager.getCurrentTanggalLahir(),
                tempat_lahir = stateManager.getCurrentTempatLahir()
            )

            when (val result = createSuratUsahaUseCase(request)) {
                is SuratUsahaResult.Success -> {
                    _events.emit(SubmitEvent.Success)
                    stateManager.resetForm()
                }
                is SuratUsahaResult.Error -> {
                    errorMessage = result.message
                    _events.emit(SubmitEvent.Error(result.message))
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Terjadi kesalahan"
            _events.emit(SubmitEvent.Error(errorMessage!!))
        } finally {
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }

    sealed class SubmitEvent {
        data object Success : SubmitEvent()
        data class Error(val message: String) : SubmitEvent()
    }
}