package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skpenghasilan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppNumberField
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKPenghasilanViewModel

@Composable
internal fun SKPenghasilan2Content(
    viewModel: SKPenghasilanViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Orang Tua", "Informasi Anak"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiOrangTua(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiOrangTua(
    viewModel: SKPenghasilanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Orang Tua")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikOrtuValue,
            onValueChange = viewModel::updateNikOrtu,
            isError = viewModel.hasFieldError("nik_ortu"),
            errorMessage = viewModel.getFieldError("nik_ortu"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaOrtuValue,
            onValueChange = viewModel::updateNamaOrtu,
            isError = viewModel.hasFieldError("nama_ortu"),
            errorMessage = viewModel.getFieldError("nama_ortu"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Tempat lahir",
                    value = viewModel.tempatLahirOrtuValue,
                    onValueChange = viewModel::updateTempatLahirOrtu,
                    isError = viewModel.hasFieldError("tempat_lahir_ortu"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ortu"),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirOrtuValue,
                    onValueChange = viewModel::updateTanggalLahirOrtu,
                    isError = viewModel.hasFieldError("tanggal_lahir_ortu"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ortu"),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelaminOrtuValue,
            onGenderSelected = viewModel::updateJenisKelaminOrtu,
            isError = viewModel.hasFieldError("jenis_kelamin_ortu"),
            errorMessage = viewModel.getFieldError("jenis_kelamin_ortu"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanOrtuValue,
            onValueChange = viewModel::updatePekerjaanOrtu,
            isError = viewModel.hasFieldError("pekerjaan_ortu"),
            errorMessage = viewModel.getFieldError("pekerjaan_ortu"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatOrtuValue,
            onValueChange = viewModel::updateAlamatOrtu,
            isError = viewModel.hasFieldError("alamat_ortu"),
            errorMessage = viewModel.getFieldError("alamat_ortu"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppNumberField(
            label = "Penghasilan Rata-rata per Bulan (Rp)",
            placeholder = "0",
            value = viewModel.penghasilanOrtuValue,
            onValueChange = viewModel::updatePenghasilanOrtu,
            isError = viewModel.hasFieldError("penghasilan_ortu"),
            errorMessage = viewModel.getFieldError("penghasilan_ortu"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppNumberField(
            label = "Jumlah Tanggungan Anggota Keluarga",
            placeholder = "0",
            value = viewModel.tanggunganOrtuValue,
            onValueChange = viewModel::updateTanggunganOrtu,
            isError = viewModel.hasFieldError("tanggungan_ortu"),
            errorMessage = viewModel.getFieldError("tanggungan_ortu"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}