package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun HusbandMotherDataSection(suratDetail: SuratDetail) {
    val dataIbuSuami = suratDetail.dataIbuSuami ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIbuSuami.nikIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ibu Suami", it)
        }
        dataIbuSuami.namaIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ibu Suami", it)
        }
        dataIbuSuami.tanggalLahirIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ibu Suami", dateFormatterToApiFormat(it))
        }
        dataIbuSuami.tempatLahirIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ibu Suami", it)
        }
        dataIbuSuami.alamatIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ibu Suami", it)
        }
        dataIbuSuami.agamaIbuSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ibu Suami", it)
        }
        dataIbuSuami.kewarganegaraanIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ibu Suami", it)
        }
        dataIbuSuami.pekerjaanIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ibu Suami", it)
        }
    }
}
