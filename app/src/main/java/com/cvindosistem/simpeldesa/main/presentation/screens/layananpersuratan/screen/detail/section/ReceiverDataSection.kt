package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun ReceiverDataSection(suratDetail: SuratDetail) {
    val dataPenerimaKuasa = suratDetail.dataPenerimaKuasa ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPenerimaKuasa.nikPenerima?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Penerima Kuasa", it)
        }
        dataPenerimaKuasa.namaPenerima?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Penerima Kuasa", it)
        }
        dataPenerimaKuasa.jabatanPenerima?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan Penerima Kuasa", it)
        }
    }
}