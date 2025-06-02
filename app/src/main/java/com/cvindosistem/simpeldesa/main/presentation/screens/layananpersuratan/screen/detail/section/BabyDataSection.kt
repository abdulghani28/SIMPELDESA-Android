package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun BabyDataSection(suratDetail: SuratDetail) {
    val dataBayi = suratDetail.dataBayi ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataBayi.nama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Bayi", it)
        }
        dataBayi.jenisKelamin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataBayi.tanggalLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataBayi.jamLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jam Lahir", it)
        }
        dataBayi.tempatLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataBayi.anakKe?.let {
            DataRow("Anak Ke", it.toString())
        }
    }
}
