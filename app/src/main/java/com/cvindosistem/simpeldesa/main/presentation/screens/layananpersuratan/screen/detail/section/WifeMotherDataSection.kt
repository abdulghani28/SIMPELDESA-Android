package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun WifeMotherDataSection(suratDetail: SuratDetail) {
    val dataIbuIstri = suratDetail.dataIbuIstri ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIbuIstri.nikIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ibu Istri", it)
        }
        dataIbuIstri.namaIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ibu Istri", it)
        }
        dataIbuIstri.tanggalLahirIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ibu Istri", dateFormatterToApiFormat(it))
        }
        dataIbuIstri.tempatLahirIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ibu Istri", it)
        }
        dataIbuIstri.alamatIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ibu Istri", it)
        }
        dataIbuIstri.agamaIbuIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ibu Istri", it)
        }
        dataIbuIstri.kewarganegaraanIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ibu Istri", it)
        }
        dataIbuIstri.pekerjaanIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ibu Istri", it)
        }
    }
}