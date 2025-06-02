package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun MissingPersonDataSection(suratDetail: SuratDetail) {
    val dataOrangHilang = suratDetail.dataOrangHilang ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataOrangHilang.namaOrangHilang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Orang Hilang", it)
        }
        dataOrangHilang.jenisKelamin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataOrangHilang.usia?.let {
            DataRow("Usia", it.toString())
        }
        dataOrangHilang.hilangSejak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hilang Sejak", it)
        }
    }
}