package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun JobDataSection(suratDetail: SuratDetail) {
    val dataPekerjaan = suratDetail.dataPekerjaan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPekerjaan.namaPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Perusahaan", it)
        }
        dataPekerjaan.jabatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan", it)
        }
    }
}