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
internal fun SKJualBeli2Content(
    viewModel: SKJualBeliViewModel,
    modifier: Modifier = Modifier
) {

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
            InformasiPembeli(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPembeli(
    viewModel: SKJualBeliViewModel
) {
    Column {
        SectionTitle("Informasi Pembeli")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nik2Value,
            onValueChange = viewModel::updateNik2,
            isError = viewModel.hasFieldError("nik_2"),
            errorMessage = viewModel.getFieldError("nik_2"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.nama2Value,
            onValueChange = viewModel::updateNama2,
            isError = viewModel.hasFieldError("nama_2"),
            errorMessage = viewModel.getFieldError("nama_2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelamin2Value,
            onGenderSelected = viewModel::updateJenisKelamin2,
            isError = viewModel.hasFieldError("jenis_kelamin_2"),
            errorMessage = viewModel.getFieldError("jenis_kelamin_2")
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
                    value = viewModel.tempatLahir2Value,
                    onValueChange = viewModel::updateTempatLahir2,
                    isError = viewModel.hasFieldError("tempat_lahir_2"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_2")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahir2Value,
                    onValueChange = viewModel::updateTanggalLahir2,
                    isError = viewModel.hasFieldError("tanggal_lahir_2"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_2")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaan2Value,
            onValueChange = viewModel::updatePekerjaan2,
            isError = viewModel.hasFieldError("pekerjaan_2"),
            errorMessage = viewModel.getFieldError("pekerjaan_2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamat2Value,
            onValueChange = viewModel::updateAlamat2,
            isError = viewModel.hasFieldError("alamat_2"),
            errorMessage = viewModel.getFieldError("alamat_2")
        )
    }
}
