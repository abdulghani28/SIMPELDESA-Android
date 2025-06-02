package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun RelocationDataSection(suratDetail: SuratDetail) {
    val dataPindah = suratDetail.dataPindah ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPindah.alamat?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Asal", it)
        }
        dataPindah.alamatPindah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Pindah", it)
        }
        dataPindah.alasanPindah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alasan Pindah", it)
        }
        dataPindah.jumlahAnggota?.let {
            DataRow("Jumlah Anggota", it.toString())
        }
    }
}