package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jandaduda

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse

// UI State - copy langsung dari kode asli
data class SKJandaDudaUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)