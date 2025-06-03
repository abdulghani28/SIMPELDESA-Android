package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PerbedaanIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.TercantumIdentitasResponse

data class SKBedaIdentitasUiState(
    val isFormDirty: Boolean = false,
    val tercantumIdentitasList: List<TercantumIdentitasResponse.Data> = emptyList(),
    val perbedaanIdentitasList: List<PerbedaanIdentitasResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)