package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun TravelDataSection(suratDetail: SuratDetail) {
    val dataPerjalanan = suratDetail.dataPerjalanan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPerjalanan.tempatTujuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Tujuan", it)
        }
        dataPerjalanan.tanggalKeberangkatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Keberangkatan", dateFormatterToApiFormat(it))
        }
        dataPerjalanan.lama?.let {
            DataRow("Lama Perjalanan", it.toString())
        }
        dataPerjalanan.satuanLama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Satuan Lama", it)
        }
        dataPerjalanan.maksudTujuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Maksud Tujuan", it)
        }
        dataPerjalanan.jumlahPengikut?.let {
            DataRow("Jumlah Pengikut", it.toString())
        }
    }
}
