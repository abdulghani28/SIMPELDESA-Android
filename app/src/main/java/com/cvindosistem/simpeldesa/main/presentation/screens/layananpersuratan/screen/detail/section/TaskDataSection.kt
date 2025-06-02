package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun TaskDataSection(suratDetail: SuratDetail) {
    val dataTugas = suratDetail.dataTugas ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataTugas.deskripsi?.takeIf { it.isNotBlank() }?.let {
            DataRow("Deskripsi Tugas", it)
        }
        dataTugas.ditugaskanUntuk?.takeIf { it.isNotBlank() }?.let {
            DataRow("Ditugaskan Untuk", it)
        }
    }
}