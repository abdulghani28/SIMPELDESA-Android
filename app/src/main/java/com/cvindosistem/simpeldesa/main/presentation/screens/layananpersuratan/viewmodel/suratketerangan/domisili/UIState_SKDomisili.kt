package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse

data class SKDomisiliUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val currentTab: Int = 0,
    val totalTabs: Int = 2
)
