package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun TaskAssignerDataSection(suratDetail: SuratDetail) {
    val dataPemberiTugas = suratDetail.dataPemberiTugas ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPemberiTugas.nama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Pemberi Tugas", it)
        }
        dataPemberiTugas.nik?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Pemberi Tugas", it)
        }
    }
}