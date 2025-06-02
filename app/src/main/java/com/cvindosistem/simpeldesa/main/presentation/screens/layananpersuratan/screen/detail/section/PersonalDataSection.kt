package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.presentation.components.DataRow
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
internal fun PersonalDataSection(suratDetail: SuratDetail) {
    val dataPersonal = suratDetail.dataPribadi

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPersonal.nik?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK", it)
        }
        dataPersonal.nama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Lengkap", it)
        }
        dataPersonal.jabatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan", it)
        }
        dataPersonal.pekerjaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan", it)
        }
        dataPersonal.jenisKelamin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataPersonal.tempatLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataPersonal.tanggalLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataPersonal.kewarganegaraan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan", it)
        }
        dataPersonal.alamatIdentitas?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Sesuai Identitas", it)
        }
        dataPersonal.alamat?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Sekarang", it)
        }
        dataPersonal.agama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama", it)
        }
        dataPersonal.statusKawin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kawin", it)
        }
        dataPersonal.jumlahPengikut?.takeIf { it != 0 }?.let {
            DataRow("Jumlah Pengikut", it.toString())
        }
        dataPersonal.kontak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kontak", it)
        }
        dataPersonal.wargaDesa?.let {
            DataRow("Status", if(it) "Warga Desa" else "Pendatang")
        }
    }
}