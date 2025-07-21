package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmaktalahir

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir.SPMAktaLahirViewModel

@Composable
internal fun SPMAktaLahir1Content(
    viewModel: SPMAktaLahirViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Anak", "Informasi Orang Tua", "Informasi Lainnya"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            UseMyDataCheckbox(
                checked = viewModel.useMyDataChecked,
                onCheckedChange = viewModel::updateUseMyData,
                isLoading = viewModel.isLoadingUserData
            )
        }

        item {
            InformasiPelapor(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPelapor(
    viewModel: SPMAktaLahirViewModel
) {
    Column {
        SectionTitle("Informasi Pelapor")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikValue,
            onValueChange = viewModel::updateNik,
            isError = viewModel.hasFieldError("nik"),
            errorMessage = viewModel.getFieldError("nik"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaValue,
            onValueChange = viewModel::updateNama,
            isError = viewModel.hasFieldError("nama"),
            errorMessage = viewModel.getFieldError("nama")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat",
            placeholder = "Masukkan alamat",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanValue,
            onValueChange = viewModel::updatePekerjaan,
            isError = viewModel.hasFieldError("pekerjaan"),
            errorMessage = viewModel.getFieldError("pekerjaan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirValue,
                    onValueChange = viewModel::updateTempatLahir,
                    isError = viewModel.hasFieldError("tempat_lahir"),
                    errorMessage = viewModel.getFieldError("tempat_lahir")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirValue,
                    onValueChange = viewModel::updateTanggalLahir,
                    isError = viewModel.hasFieldError("tanggal_lahir"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir")
                )
            }
        }
    }
}
