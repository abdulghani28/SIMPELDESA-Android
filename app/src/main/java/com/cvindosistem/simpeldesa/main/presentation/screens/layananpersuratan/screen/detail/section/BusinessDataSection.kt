package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun BusinessDataSection(suratDetail: SuratDetail) {
    val dataUsaha = suratDetail.dataUsaha ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataUsaha.namaUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Usaha", it)
        }
        dataUsaha.jenisUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Usaha", it)
        }
        dataUsaha.bidangUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Bidang Usaha", it)
        }
        dataUsaha.npwp?.takeIf { it.isNotBlank() }?.let {
            DataRow("NPWP", it)
        }
        dataUsaha.alamatUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Usaha", it)
        }
        dataUsaha.wargaDesa?.let {
            DataRow("Status", if(it) "Warga Desa" else "Pendatang")
        }
    }
}