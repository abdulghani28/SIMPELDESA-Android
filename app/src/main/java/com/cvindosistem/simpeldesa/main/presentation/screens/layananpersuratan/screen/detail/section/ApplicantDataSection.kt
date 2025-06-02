package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun ApplicantDataSection(suratDetail: SuratDetail) {
    val dataPemohon = suratDetail.dataPemohon ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPemohon.diajukanOlehNik?.takeIf { it.isNotBlank() }?.let {
            DataRow("Diajukan Oleh (NIK)", it)
        }
    }
}
