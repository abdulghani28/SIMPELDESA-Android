package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun FatherDataSection(suratDetail: SuratDetail) {
    val dataAyah = suratDetail.dataAyah ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAyah.nikAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ayah", it)
        }
        dataAyah.namaAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ayah", it)
        }
        dataAyah.tanggalLahirAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ayah", dateFormatterToApiFormat(it))
        }
        dataAyah.tempatLahirAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ayah", it)
        }
        dataAyah.alamatAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ayah", it)
        }
    }
}