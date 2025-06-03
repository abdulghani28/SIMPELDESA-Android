package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan

data class SKDomisiliPerusahaanUiState(
    val isFormDirty: Boolean = false,
    val currentTab: Int = 0,
    val totalTabs: Int = 2,
    val currentStepWargaDesa: Int = 1,
    val currentStepPendatang: Int = 1,
    val totalStepsWargaDesa: Int = 3,
    val totalStepsPendatang: Int = 3
)