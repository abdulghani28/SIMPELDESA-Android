package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skdomisiliperusahaan.warga

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DomisiliWargaDesa2Content(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(
                onPreviewClick = { },
                onBackClick = onBackClick,
                onSubmitClick = onSubmitClick
            )
        }
    ) {
        FormSectionList(
            modifier = modifier,
            background = MaterialTheme.colorScheme.background
        ) {
            item {
                StepIndicator(
                    steps = listOf("Informasi Pelapor", "Informasi Perusahaan"),
                    currentStep = 2
                )
            }

            item {
                InformasiPerusahaan()
            }
        }
    }
}

@Composable
private fun InformasiPerusahaan() {
    Column {
        SectionTitle("Informasi Perusahaan")

        Spacer(modifier = Modifier.height(16.dp))

        var namaPerusahaanValue by remember { mutableStateOf("") }
        var jenisPerusahaanValue by remember { mutableStateOf("") }
        var bidangUsahaValue by remember { mutableStateOf("") }
        var notarisValue by remember { mutableStateOf("") }
        var nibValue by remember { mutableStateOf("") }
        var statusKepemilikanValue by remember { mutableStateOf("") }
        var jumlahKaryawanValue by remember { mutableStateOf("") }
        var alamatPerusahaanValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nama Perusahaan",
            placeholder = "Masukkan nama perusahaan",
            value = namaPerusahaanValue,
            onValueChange = { namaPerusahaanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Jenis Perusahaan",
            value = jenisPerusahaanValue,
            onValueChange = { jenisPerusahaanValue = it },
            options = listOf(
                "Perseroan Terbatas (PT)",
                "Commanditaire Vennootschap (CV)",
                "Firma",
                "Koperasi",
                "Yayasan",
                "Perorangan"
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Bidang Usaha",
            value = bidangUsahaValue,
            onValueChange = { bidangUsahaValue = it },
            options = listOf(
                "Manufaktur",
                "Perdagangan",
                "Jasa",
                "Pertanian",
                "Perikanan",
                "Teknologi Informasi",
                "Konstruksi",
                "Transportasi",
                "Pendidikan",
                "Kesehatan"
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Notaris / Nomor Akta Pendirian",
            placeholder = "Masukkan nomor akta pendirian",
            value = notarisValue,
            onValueChange = { notarisValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Berusaha (NIB)",
            placeholder = "Masukkan NIB",
            value = nibValue,
            onValueChange = { nibValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Status Kepemilikan Tanah/Bangunan",
            value = statusKepemilikanValue,
            onValueChange = { statusKepemilikanValue = it },
            options = listOf(
                "Sertifikat Hak Milik (SHM)",
                "Hak Guna Bangunan (HGB)",
                "Hak Pakai",
                "Sewa",
                "Pinjam Pakai"
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Jumlah Karyawan",
            value = jumlahKaryawanValue,
            onValueChange = { jumlahKaryawanValue = it },
            options = listOf(
                "1 - 10",
                "11 - 50",
                "51 - 100",
                "101 - 500",
                "Lebih dari 500"
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Perusahaan",
            placeholder = "Masukkan alamat lengkap perusahaan",
            value = alamatPerusahaanValue,
            onValueChange = { alamatPerusahaanValue = it },
            isError = false,
            errorMessage = null
        )
    }
}
