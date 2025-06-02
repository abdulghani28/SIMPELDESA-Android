package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun MarriageDataSection(suratDetail: SuratDetail) {
    val dataPernikahan = suratDetail.dataPernikahan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPernikahan.hari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari", it)
        }
        dataPernikahan.tanggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal", dateFormatterToApiFormat(it))
        }
        dataPernikahan.jam?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jam", it)
        }
        dataPernikahan.tempat?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat", it)
        }
        dataPernikahan.jumlahIstri?.let {
            DataRow("Jumlah Istri", it.toString())
        }
    }
}