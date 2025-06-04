package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.tugas

data class SuratTugasUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)