package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun HusbandFatherDataSection(suratDetail: SuratDetail) {
    val dataAyahSuami = suratDetail.dataAyahSuami ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAyahSuami.nikAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ayah Suami", it)
        }
        dataAyahSuami.namaAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ayah Suami", it)
        }
        dataAyahSuami.tanggalLahirAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ayah Suami", dateFormatterToApiFormat(it))
        }
        dataAyahSuami.tempatLahirAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ayah Suami", it)
        }
        dataAyahSuami.alamatAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ayah Suami", it)
        }
        dataAyahSuami.agamaAyahSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ayah Suami", it)
        }
        dataAyahSuami.kewarganegaraanAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ayah Suami", it)
        }
        dataAyahSuami.pekerjaanAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ayah Suami", it)
        }
    }
}
