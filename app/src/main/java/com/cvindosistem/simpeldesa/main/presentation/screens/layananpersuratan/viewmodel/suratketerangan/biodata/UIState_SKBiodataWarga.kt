package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
//import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.DisabilitasResponse

data class SKBiodataWargaUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val pendidikanList: List<PendidikanResponse.Data> = emptyList(),
//    val disabilitasList: List<DisabilitasResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 5
)