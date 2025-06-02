package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun DeceasedDataSection(suratDetail: SuratDetail) {
    val dataAlmarhum = suratDetail.dataAlmarhum ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAlmarhum.namaMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Almarhum", it)
        }
        dataAlmarhum.nikMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Almarhum", it)
        }
        dataAlmarhum.jenisKelaminMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataAlmarhum.tanggalLahirMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataAlmarhum.tempatLahirMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataAlmarhum.alamatMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataAlmarhum.tanggalMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Meninggal", dateFormatterToApiFormat(it))
        }
        dataAlmarhum.hariMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari Meninggal", it)
        }
        dataAlmarhum.tempatMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Meninggal", it)
        }
        dataAlmarhum.sebabMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Sebab Meninggal", it)
        }
    }
}