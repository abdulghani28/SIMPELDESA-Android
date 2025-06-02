package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun PermitDataSection(suratDetail: SuratDetail) {
    val dataIzin = suratDetail.dataIzin ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIzin.alasanIzin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alasan Izin", it)
        }
        dataIzin.terhitungDari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Terhitung Dari", dateFormatterToApiFormat(it))
        }
        dataIzin.lama?.let {
            DataRow("Lama Izin", "${it} Hari")
        }
    }
}