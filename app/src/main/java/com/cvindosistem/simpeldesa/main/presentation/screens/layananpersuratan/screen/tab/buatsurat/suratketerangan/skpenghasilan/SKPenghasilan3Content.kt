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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.penghasilan.SKPenghasilanViewModel

@Composable
internal fun SKPenghasilan3Content(
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
            InformasiAnak(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiAnak(
    viewModel: SKPenghasilanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Anak")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikAnakValue,
            onValueChange = viewModel::updateNikAnak,
            isError = viewModel.hasFieldError("nik_anak"),
            errorMessage = viewModel.getFieldError("nik_anak"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaAnakValue,
            onValueChange = viewModel::updateNamaAnak,
            isError = viewModel.hasFieldError("nama_anak"),
            errorMessage = viewModel.getFieldError("nama_anak")
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
                    value = viewModel.tempatLahirAnakValue,
                    onValueChange = viewModel::updateTempatLahirAnak,
                    isError = viewModel.hasFieldError("tempat_lahir_anak"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_anak")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirAnakValue,
                    onValueChange = viewModel::updateTanggalLahirAnak,
                    isError = viewModel.hasFieldError("tanggal_lahir_anak"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_anak")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelaminAnakValue,
            onGenderSelected = viewModel::updateJenisKelaminAnak,
            isError = viewModel.hasFieldError("jenis_kelamin_anak"),
            errorMessage = viewModel.getFieldError("jenis_kelamin_anak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Sekolah/Universitas",
            placeholder = "Masukkan nama institusi pendidikan",
            value = viewModel.namaSekolahAnakValue,
            onValueChange = viewModel::updateNamaSekolahAnak,
            isError = viewModel.hasFieldError("sekolah_anak"),
            errorMessage = viewModel.getFieldError("sekolah_anak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kelas/Semester",
            placeholder = "Masukkan kelas/semester",
            value = viewModel.kelasAnakValue,
            onValueChange = viewModel::updateKelasAnak,
            isError = viewModel.hasFieldError("kelas_anak"),
            errorMessage = viewModel.getFieldError("kelas_anak"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}
