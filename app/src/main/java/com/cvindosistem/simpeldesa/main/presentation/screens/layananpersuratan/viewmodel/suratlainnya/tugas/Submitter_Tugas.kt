package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratTugasRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratTugasResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTugasUseCase

class SuratTugasSubmitter(
    private val createSuratTugasUseCase: CreateSuratTugasUseCase
) {
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    suspend fun submitForm(
        stateManager: SuratTugasStateManager,
        onSuccess: suspend () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        isLoading = true
        errorMessage = null

        try {
            // Create list of all recipients (primary + additional)
            val allRecipients = mutableListOf<SuratTugasRequest.Penerima>()

            // Add primary recipient
            allRecipients.add(
                SuratTugasRequest.Penerima(
                    jabatan = stateManager.jabatanValue,
                    nama = stateManager.namaValue,
                    nik = stateManager.nikValue
                )
            )

            // Add additional recipients
            stateManager.additionalRecipients.forEach { recipient ->
                allRecipients.add(
                    SuratTugasRequest.Penerima(
                        jabatan = recipient.jabatan,
                        nama = recipient.nama,
                        nik = recipient.nik
                    )
                )
            }

            val request = SuratTugasRequest(
                deskripsi = stateManager.deskripsiValue,
                disahkan_oleh = stateManager.disahkanOlehValue,
                ditugaskan_untuk = stateManager.ditugaskanUntukValue,
                penerima = allRecipients
            )

            when (val result = createSuratTugasUseCase(request)) {
                is SuratTugasResult.Success -> {
                    onSuccess()
                }
                is SuratTugasResult.Error -> {
                    errorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Terjadi kesalahan"
            errorMessage = errorMsg
            onError(errorMsg)
        } finally {
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }
}
