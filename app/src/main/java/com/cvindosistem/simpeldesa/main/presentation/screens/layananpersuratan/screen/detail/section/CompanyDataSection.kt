package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun CompanyDataSection(suratDetail: SuratDetail) {
    val dataUsaha = suratDetail.dataPerusahaan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataUsaha.namaPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Perusahaan", it)
        }
        dataUsaha.jenisUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Perusahaan", it)
        }
        dataUsaha.bidangUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Bidang Usaha", it)
        }
        dataUsaha.noAkta?.takeIf { it.isNotBlank() }?.let {
            DataRow("No Notaris/Akta Pendirian", it)
        }
        dataUsaha.nib?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor Induk Berusaha (NIB)", it)
        }
        dataUsaha.peruntukanBangunan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Peruntukan Bangunan", it)
        }
        dataUsaha.luasTanah?.takeIf { it != 0 }?.let {
            DataRow("Luas Tanah", it.toString())
        }
        dataUsaha.luasBangunan?.takeIf { it != 0 }?.let {
            DataRow("Luas Bangunan", it.toString())
        }
        dataUsaha.statusKepemilikan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kepemilikan", it)
        }
        dataUsaha.alamatPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Usaha", it)
        }
        dataUsaha.npwp?.takeIf { it.isNotBlank() }?.let {
            DataRow("NPWP", it)
        }
    }
}
