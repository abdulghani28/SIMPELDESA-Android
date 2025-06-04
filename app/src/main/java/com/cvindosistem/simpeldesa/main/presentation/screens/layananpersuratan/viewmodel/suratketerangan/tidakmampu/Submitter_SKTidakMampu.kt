package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmampu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKTidakMampuRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMampuResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMampuUseCase

class SKTidakMampuFormSubmitter(
    private val createSuratCatatanTidakMampuUseCase: CreateSuratTidakMampuUseCase
) {
    var isLoading by mutableStateOf(false)
        private set

    suspend fun submitForm(request: SKTidakMampuRequest): SuratTidakMampuResult {
        isLoading = true
        return try {
            val result = createSuratCatatanTidakMampuUseCase(request)
            result
        } catch (e: Exception) {
            SuratTidakMampuResult.Error(e.message ?: "Terjadi kesalahan")
        } finally {
            isLoading = false
        }
    }
}