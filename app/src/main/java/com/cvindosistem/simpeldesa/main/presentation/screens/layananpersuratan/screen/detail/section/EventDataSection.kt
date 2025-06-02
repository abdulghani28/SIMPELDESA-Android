package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun EventDataSection(suratDetail: SuratDetail) {
    val dataAcara = suratDetail.dataAcara ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAcara.namaAcara?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Acara", it)
        }
        dataAcara.tempatAcara?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Acara", it)
        }
        dataAcara.hari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari", it)
        }
        dataAcara.tanggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal", dateFormatterToApiFormat(it))
        }
        dataAcara.dimulai?.takeIf { it.isNotBlank() }?.let {
            DataRow("Dimulai", it)
        }
        dataAcara.selesai?.takeIf { it.isNotBlank() }?.let {
            DataRow("Selesai", it)
        }
        dataAcara.penanggungJawab?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penanggung Jawab", it)
        }
    }
}