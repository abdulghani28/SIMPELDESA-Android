package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun WifeDataSection(suratDetail: SuratDetail) {
    val dataIstri = suratDetail.dataIstri ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIstri.nikIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Istri", it)
        }
        dataIstri.namaIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Istri", it)
        }
        dataIstri.namaIstriSebelumnya?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Istri Sebelumnya", it)
        }
        dataIstri.tanggalLahirIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Istri", dateFormatterToApiFormat(it))
        }
        dataIstri.tempatLahirIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Istri", it)
        }
        dataIstri.alamatIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Istri", it)
        }
        dataIstri.agamaIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Istri", it)
        }
        dataIstri.kewarganegaraanIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Istri", it)
        }
        dataIstri.pekerjaanIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Istri", it)
        }
        dataIstri.statusKawinIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kawin Istri", it)
        }
    }
}