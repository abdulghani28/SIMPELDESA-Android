package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skdomisiliperusahaan.warga

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan.SKDomisiliPerusahaanViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DomisiliPerusahaanWargaDesa2Content(
    viewModel: SKDomisiliPerusahaanViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Perusahaan", "Informasi Pelengkap"),
                currentStep = viewModel.getCurrentStepForUI()
            )
        }

        item {
            InformasiPerusahaan(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPerusahaan(
    viewModel: SKDomisiliPerusahaanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Perusahaan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Perusahaan",
            placeholder = "Masukkan nama perusahaan",
            value = viewModel.wargaNamaPerusahaanValue,
            onValueChange = { viewModel.updateWargaNamaPerusahaan(it) },
            isError = validationErrors.containsKey("nama_perusahaan"),
            errorMessage = validationErrors["nama_perusahaan"],
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Jenis Perusahaan",
            value = viewModel.jenisUsahaList.find { it.id == viewModel.wargaJenisUsahaValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.jenisUsahaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateWargaJenisUsaha(it.id) }
            },
            options = viewModel.jenisUsahaList.map { it.nama },
            isError = viewModel.hasFieldError("jenis_usaha_id"),
            errorMessage = viewModel.getFieldError("jenis_usaha_id"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Bidang Usaha",
            value = viewModel.bidangUsahaList.find { it.id == viewModel.wargaBidangUsahaValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.bidangUsahaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateWargaBidangUsaha(it.id) }
            },
            options = viewModel.bidangUsahaList.map { it.nama },
            isError = viewModel.hasFieldError("bidang_usaha_id"),
            errorMessage = viewModel.getFieldError("bidang_usaha_id"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Notaris / Nomor Akta Pendirian",
            placeholder = "Masukkan nomor akta pendirian",
            value = viewModel.wargaNomorAktaValue,
            onValueChange = { viewModel.updateWargaNomorAkta(it) },
            isError = validationErrors.containsKey("nomor_akta_pendirian"),
            errorMessage = validationErrors["nomor_akta_pendirian"],
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Berusaha (NIB)",
            placeholder = "Masukkan NIB",
            value = viewModel.wargaNibValue,
            onValueChange = { viewModel.updateWargaNib(it) },
            isError = validationErrors.containsKey("nib"),
            errorMessage = validationErrors["nib"],
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Status Kepemilikan Tanah/Bangunan",
            value = viewModel.wargaStatusKepemilikanBangunanValue,
            onValueChange = { viewModel.updateWargaStatusKepemilikanBangunan(it) },
            options = listOf(
                "Sertifikat Hak Milik (SHM)",
                "Sertifikat Hak Guna Bangunan (HGB)",
                "Sertifikat Hak Guna Usaha (HGU)",
                "Sertifikat Hak Pakai (HP)",
                "Sertifikat Hak Asasi Satuan Rumah Susun (SHSRS)",
                "Tanah Girik"
            ),
            isError = validationErrors.containsKey("status_kepemilikan_bangunan"),
            errorMessage = validationErrors["status_kepemilikan_bangunan"],
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jumlah Karyawan",
            placeholder = "Masukkan jumlah karyawan",
            value = viewModel.wargaJumlahKaryawanValue,
            onValueChange = { viewModel.updateWargaJumlahKaryawan(it) },
            isError = validationErrors.containsKey("jumlah_karyawan"),
            errorMessage = validationErrors["jumlah_karyawan"],
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Perusahaan",
            placeholder = "Masukkan alamat lengkap perusahaan",
            value = viewModel.wargaAlamatPerusahaanValue,
            onValueChange = { viewModel.updateWargaAlamatPerusahaan(it) },
            isError = validationErrors.containsKey("alamat_perusahaan"),
            errorMessage = validationErrors["alamat_perusahaan"],
        )
    }
}
