package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun ChildDataSection(suratDetail: SuratDetail) {
    val dataAnak = suratDetail.dataAnak ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAnak.nikAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Anak", it)
        }
        dataAnak.namaAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Anak", it)
        }
        dataAnak.jenisKelaminAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataAnak.tanggalLahirAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataAnak.tempatLahirAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataAnak.namaSekolahAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Sekolah", it)
        }
        dataAnak.kelasAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kelas", it)
        }
    }
}