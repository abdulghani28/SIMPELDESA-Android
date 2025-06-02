package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun ParentDataSection(suratDetail: SuratDetail) {
    val dataOrangTua = suratDetail.dataOrangTua ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataOrangTua.nikOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Orang Tua", it)
        }
        dataOrangTua.namaOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Orang Tua", it)
        }
        dataOrangTua.jenisKelaminOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataOrangTua.tanggalLahirOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataOrangTua.tempatLahirOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataOrangTua.alamatOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataOrangTua.pekerjaanOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan", it)
        }
        dataOrangTua.penghasilanOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penghasilan", it)
        }
        dataOrangTua.tanggunganOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggungan", it)
        }
    }
}