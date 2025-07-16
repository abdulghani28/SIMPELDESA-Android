package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ktpdalamproses

data class SKKTPDalamProsesUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)