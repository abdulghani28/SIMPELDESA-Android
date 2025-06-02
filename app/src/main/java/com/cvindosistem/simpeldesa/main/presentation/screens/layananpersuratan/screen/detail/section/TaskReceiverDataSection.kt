package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.TitleSmallText
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun TaskReceiverDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        suratDetail.dataPenerimaTugas.forEachIndexed { index, penerima ->
            if (listOf(penerima.nama, penerima.nik, penerima.jabatan).any { !it.isNullOrBlank() }) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (suratDetail.dataPenerimaTugas.size > 1) {
                        TitleSmallText("Penerima Tugas ${index + 1}")
                        Spacer(Modifier.height(4.dp))
                    }

                    penerima.nama?.takeIf { it.isNotBlank() }?.let {
                        DataRow("Nama", it)
                    }
                    penerima.nik?.takeIf { it.isNotBlank() }?.let {
                        DataRow("NIK", it)
                    }
                    penerima.jabatan?.takeIf { it.isNotBlank() }?.let {
                        DataRow("Jabatan", it)
                    }
                }
            }
        }
    }
}