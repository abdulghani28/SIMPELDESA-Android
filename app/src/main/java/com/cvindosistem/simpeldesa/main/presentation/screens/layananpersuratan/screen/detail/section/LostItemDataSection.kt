package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.formatDateTime
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun LostItemDataSection(suratDetail: SuratDetail) {
    val dataBarangHilang = suratDetail.dataBarangHilang ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataBarangHilang.jenisBarang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Barang", it)
        }
        dataBarangHilang.ciri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Ciri-ciri", it)
        }
        dataBarangHilang.tempatKehilangan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Kehilangan", it)
        }
        dataBarangHilang.waktuKehilangan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Waktu Kehilangan", formatDateTime(it))
        }
    }
}