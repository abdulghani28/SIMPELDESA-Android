package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPermohonanPenerbitanBukuPasLintasBatasRequest

data class SPPermohonanPenerbitanBukuPasLintasBatasUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val statusKawinList: List<StatusKawinResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 4,
    val anggotaKeluargaList: List<SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga> = emptyList()
)