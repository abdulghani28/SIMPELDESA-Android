package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.runtime.Composable
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun PurposeDataSection(suratDetail: SuratDetail) {
    suratDetail.keperluan?.takeIf { it.isNotBlank() }?.let {
        DataRow("Keperluan", it)
    }
}

@Composable
internal fun RelationshipDataSection(suratDetail: SuratDetail) {
    suratDetail.hubunganId?.takeIf { it.isNotBlank() }?.let {
        DataRow("Hubungan", it)
    }
}
