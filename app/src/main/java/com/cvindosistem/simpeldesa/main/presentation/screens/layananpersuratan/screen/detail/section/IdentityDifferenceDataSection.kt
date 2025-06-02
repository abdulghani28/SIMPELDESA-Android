package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun Identity1DataSection(suratDetail: SuratDetail) {
    val dataIdentitas1 = suratDetail.dataIdentitas1 ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIdentitas1.nama1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama", it)
        }
        dataIdentitas1.nomor1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor", it)
        }
        dataIdentitas1.tanggalLahir1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataIdentitas1.tempatLahir1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataIdentitas1.alamat1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataIdentitas1.tercantumId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tercantum Dalam", it)
        }
    }
}

@Composable
internal fun Identity2DataSection(suratDetail: SuratDetail) {
    val dataIdentitas2 = suratDetail.dataIdentitas2 ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIdentitas2.nama2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama", it)
        }
        dataIdentitas2.nomor2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor", it)
        }
        dataIdentitas2.tanggalLahir2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataIdentitas2.tempatLahir2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataIdentitas2.alamat2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataIdentitas2.tercantumId2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tercantum Dalam", it)
        }
    }
}

@Composable
internal fun DifferenceDataSection(suratDetail: SuratDetail) {
    val dataPerbedaan = suratDetail.dataPerbedaan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPerbedaan.perbedaanId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Perbedaan", it)
        }
    }
}