package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun HusbandDataSection(suratDetail: SuratDetail) {
    val dataSuami = suratDetail.dataSuami ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataSuami.nikSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Suami", it)
        }
        dataSuami.namaSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Suami", it)
        }
        dataSuami.namaSuamiSebelumnya?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Suami Sebelumnya", it)
        }
        dataSuami.tanggalLahirSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Suami", dateFormatterToApiFormat(it))
        }
        dataSuami.tempatLahirSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Suami", it)
        }
        dataSuami.alamatSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Suami", it)
        }
        dataSuami.agamaSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Suami", it)
        }
        dataSuami.kewarganegaraanSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Suami", it)
        }
        dataSuami.pekerjaanSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Suami", it)
        }
        dataSuami.statusKawinSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kawin Suami", it)
        }
    }
}