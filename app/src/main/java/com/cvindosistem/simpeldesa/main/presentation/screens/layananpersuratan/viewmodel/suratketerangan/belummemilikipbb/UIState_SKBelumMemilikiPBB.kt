package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.belummemilikipbb

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse

data class SKBelumMemilikiPBBUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)