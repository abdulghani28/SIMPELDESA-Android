package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun MotherDataSection(suratDetail: SuratDetail) {
    val dataIbu = suratDetail.dataIbu ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIbu.nikIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ibu", it)
        }
        dataIbu.namaIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ibu", it)
        }
        dataIbu.tanggalLahirIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ibu", dateFormatterToApiFormat(it))
        }
        dataIbu.tempatLahirIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ibu", it)
        }
        dataIbu.alamatIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ibu", it)
        }
    }
}
