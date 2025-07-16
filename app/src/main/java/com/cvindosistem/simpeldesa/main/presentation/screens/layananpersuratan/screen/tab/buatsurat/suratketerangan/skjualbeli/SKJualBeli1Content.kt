package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skjualbeli

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
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli.SKJualBeliViewModel

@Composable
internal fun SKJualBeli1Content(
    viewModel: SKJualBeliViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Penjual", "Informasi Pembeli", "Informasi Barang"),
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
            InformasiPenjual(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPenjual(
    viewModel: SKJualBeliViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Penjual")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nik1Value,
            onValueChange = viewModel::updateNik1,
            isError = viewModel.hasFieldError("nik_1"),
            errorMessage = viewModel.getFieldError("nik_1"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.nama1Value,
            onValueChange = viewModel::updateNama1,
            isError = viewModel.hasFieldError("nama_1"),
            errorMessage = viewModel.getFieldError("nama_1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelamin1Value,
            onGenderSelected = viewModel::updateJenisKelamin1,
            isError = viewModel.hasFieldError("jenis_kelamin_1"),
            errorMessage = viewModel.getFieldError("jenis_kelamin_1")
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
                    value = viewModel.tempatLahir1Value,
                    onValueChange = viewModel::updateTempatLahir1,
                    isError = viewModel.hasFieldError("tempat_lahir_1"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_1")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahir1Value,
                    onValueChange = viewModel::updateTanggalLahir1,
                    isError = viewModel.hasFieldError("tanggal_lahir_1"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_1")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaan1Value,
            onValueChange = viewModel::updatePekerjaan1,
            isError = viewModel.hasFieldError("pekerjaan_1"),
            errorMessage = viewModel.getFieldError("pekerjaan_1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamat1Value,
            onValueChange = viewModel::updateAlamat1,
            isError = viewModel.hasFieldError("alamat_1"),
            errorMessage = viewModel.getFieldError("alamat_1")
        )
    }
}
