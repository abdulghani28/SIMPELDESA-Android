package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratlainnya.suratkuasa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.kuasa.SuratKuasaViewModel

@Composable
internal fun SuratKuasa1Content(
    viewModel: SuratKuasaViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pemberi Kuasa", "Informasi Penerima Kuasa"),
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
            InformasiPemberiKuasa(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPemberiKuasa(
    viewModel: SuratKuasaViewModel
) {
    Column {
        SectionTitle("Informasi Pemberi Kuasa")

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
            label = "Jabatan",
            placeholder = "Masukkan jabatan",
            value = viewModel.jabatanValue,
            onValueChange = viewModel::updateJabatan,
            isError = viewModel.hasFieldError("jabatan"),
            errorMessage = viewModel.getFieldError("jabatan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Disposisi Kuasa Sebagai",
            placeholder = "Masukkan peran pemberian kuasa",
            value = viewModel.kuasaSebagaiValue,
            onValueChange = viewModel::updateKuasaSebagai,
            isError = viewModel.hasFieldError("kuasa_sebagai"),
            errorMessage = viewModel.getFieldError("kuasa_sebagai")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Disposisi Kuasa Untuk",
            placeholder = "Masukkan tujuan pemberian kuasa",
            value = viewModel.kuasaUntukValue,
            onValueChange = viewModel::updateKuasaUntuk,
            isError = viewModel.hasFieldError("kuasa_untuk"),
            errorMessage = viewModel.getFieldError("kuasa_untuk")
        )
    }
}