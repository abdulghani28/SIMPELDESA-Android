package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun GrantorDataSection(suratDetail: SuratDetail) {
    val dataPemberiKuasa = suratDetail.dataPemberiKuasa ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPemberiKuasa.nikPemberi?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Pemberi Kuasa", it)
        }
        dataPemberiKuasa.namaPemberi?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Pemberi Kuasa", it)
        }
        dataPemberiKuasa.jabatanPemberi?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan Pemberi Kuasa", it)
        }
    }
}