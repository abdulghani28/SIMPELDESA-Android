package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.catatankepolisian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPCatatanKepolisianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratSKCKResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratSKCKUseCase

class SPCatatanKepolisianSubmitter(
    private val createSuratCatatanKepolisianUseCase: CreateSuratSKCKUseCase
) {
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    suspend fun submitForm(
        stateManager: SPCatatanKepolisianStateManager,
        onSuccess: suspend () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        isLoading = true
        errorMessage = null

        try {
            val request = SPCatatanKepolisianRequest(
                alamat = stateManager.alamatValue,
                disahkan_oleh = "", // This might be set on the backend
                jenis_kelamin = stateManager.selectedGender,
                nama = stateManager.namaValue,
                nik = stateManager.nikValue,
                pekerjaan = stateManager.pekerjaanValue,
                tanggal_lahir = dateFormatterToApiFormat(stateManager.tanggalLahirValue),
                tempat_lahir = stateManager.tempatLahirValue,
                keperluan = stateManager.keperluanValue
            )

            when (val result = createSuratCatatanKepolisianUseCase(request)) {
                is SuratSKCKResult.Success -> {
                    onSuccess()
                }
                is SuratSKCKResult.Error -> {
                    errorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val error = e.message ?: "Terjadi kesalahan"
            errorMessage = error
            onError(error)
        } finally {
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }
}