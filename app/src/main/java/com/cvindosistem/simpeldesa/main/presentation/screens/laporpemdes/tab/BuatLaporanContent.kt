package com.cvindosistem.simpeldesa.main.presentation.screens.laporpemdes.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.PhotoUploadField

@Composable
fun BuatLaporanContent(
    modifier: Modifier = Modifier
) {
    // State untuk form
    var jenisLaporanValue by remember { mutableStateOf("") }
    var perihalValue by remember { mutableStateOf("") }
    var lokasiValue by remember { mutableStateOf("") }
    var deskripsiMasalahValue by remember { mutableStateOf("") }
    var dampakValue by remember { mutableStateOf("") }
    var tindakanDiharapkanValue by remember { mutableStateOf("") }

    // Data dummy untuk dropdown
    val jenisLaporanOptions = listOf(
        "Infrastruktur Jalan",
        "Fasilitas Umum",
        "Lingkungan",
        "Pelayanan Publik",
        "Keamanan"
    )

    Scaffold(
        bottomBar = {
            AppBottomBar(
                submitText = "Buat Laporan",
                onSubmitClick = {}
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            FormSectionList(
                modifier = modifier,
                background = MaterialTheme.colorScheme.surfaceBright
            ) {
                item {
                    LaporanFormContent(
                        jenisLaporanValue = jenisLaporanValue,
                        onJenisLaporanChanged = { jenisLaporanValue = it },
                        jenisLaporanOptions = jenisLaporanOptions,
                        perihalValue = perihalValue,
                        onPerihalChanged = { perihalValue = it },
                        lokasiValue = lokasiValue,
                        onLokasiChanged = { lokasiValue = it },
                        deskripsiMasalahValue = deskripsiMasalahValue,
                        onDeskripsiMasalahChanged = { deskripsiMasalahValue = it },
                        dampakValue = dampakValue,
                        onDampakChanged = { dampakValue = it },
                        tindakanDiharapkanValue = tindakanDiharapkanValue,
                        onTindakanDiharapkanChanged = { tindakanDiharapkanValue = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun LaporanFormContent(
    jenisLaporanValue: String,
    onJenisLaporanChanged: (String) -> Unit,
    jenisLaporanOptions: List<String>,
    perihalValue: String,
    onPerihalChanged: (String) -> Unit,
    lokasiValue: String,
    onLokasiChanged: (String) -> Unit,
    deskripsiMasalahValue: String,
    onDeskripsiMasalahChanged: (String) -> Unit,
    dampakValue: String,
    onDampakChanged: (String) -> Unit,
    tindakanDiharapkanValue: String,
    onTindakanDiharapkanChanged: (String) -> Unit
) {
    Column {
        // Jenis Laporan
        DropdownField(
            label = "Jenis Laporan",
            value = jenisLaporanValue,
            onValueChange = onJenisLaporanChanged,
            options = jenisLaporanOptions,
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Perihal
        AppTextField(
            label = "Perihal",
            placeholder = "Jalan Rusak",
            value = perihalValue,
            onValueChange = onPerihalChanged,
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Lokasi
        MultilineTextField(
            label = "Lokasi",
            placeholder = "Jalan Raya Desa, di depan gedung Balai Desa",
            value = lokasiValue,
            onValueChange = onLokasiChanged,
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Deskripsi Masalah
        MultilineTextField(
            label = "Deskripsi Masalah",
            placeholder = "Terdapat lubang besar di tengah jalan yang membahayakan pengguna.",
            value = deskripsiMasalahValue,
            onValueChange = onDeskripsiMasalahChanged,
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Dampak
        MultilineTextField(
            label = "Dampak",
            placeholder = "Terdapat lubang besar di tengah jalan yang membahayakan pengguna.",
            value = dampakValue,
            onValueChange = onDampakChanged,
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tindakan yang Diharapkan
        MultilineTextField(
            label = "Tindakan yang Diharapkan",
            placeholder = "Terdapat lubang besar di tengah jalan yang membahayakan pengguna.",
            value = tindakanDiharapkanValue,
            onValueChange = onTindakanDiharapkanChanged,
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Upload Foto Bukti
        PhotoUploadField(
            label = "Upload Foto Bukti",
            onPhotoSelected = { /* Handle photo upload */ },
            isError = false,
            errorMessage = null
        )
    }
}