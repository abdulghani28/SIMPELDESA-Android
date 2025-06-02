package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun StatusDataSection(suratDetail: SuratDetail) {
    val dataStatus = suratDetail.dataStatus ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataStatus.dasarPengajuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Dasar Pengajuan", it)
        }
        dataStatus.penyebab?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penyebab", it)
        }
        dataStatus.nomorPengajuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor Pengajuan", it)
        }
    }
}