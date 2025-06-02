package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun WifeFatherDataSection(suratDetail: SuratDetail) {
    val dataAyahIstri = suratDetail.dataAyahIstri ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAyahIstri.nikAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ayah Istri", it)
        }
        dataAyahIstri.namaAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ayah Istri", it)
        }
        dataAyahIstri.tanggalLahirAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ayah Istri", dateFormatterToApiFormat(it))
        }
        dataAyahIstri.tempatLahirAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ayah Istri", it)
        }
        dataAyahIstri.alamatAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ayah Istri", it)
        }
        dataAyahIstri.agamaAyahIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ayah Istri", it)
        }
        dataAyahIstri.kewarganegaraanAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ayah Istri", it)
        }
        dataAyahIstri.pekerjaanAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ayah Istri", it)
        }
    }
}