package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmampu

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse

data class SKTidakMampuUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val statusKawinList: List<StatusKawinResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)