package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skgaib

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
import com.cvindosistem.simpeldesa.core.components.AppNumberField
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ghaib.SKGhaibViewModel

@Composable
internal fun SKGhaib2Content(
    viewModel: SKGhaibViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Orang Hilang"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiOrangYangHilang(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiOrangYangHilang(
    viewModel: SKGhaibViewModel
) {
    Column {
        SectionTitle("Informasi Orang Hilang")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaOrangHilangValue,
            onValueChange = viewModel::updateNamaOrangHilang,
            isError = viewModel.hasFieldError("nama_orang_hilang"),
            errorMessage = viewModel.getFieldError("nama_orang_hilang")
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelaminValue,
            onGenderSelected = viewModel::updateJenisKelamin,
            isError = viewModel.hasFieldError("jenis_kelamin"),
            errorMessage = viewModel.getFieldError("jenis_kelamin")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppNumberField(
            label = "Usia (Tahun)",
            placeholder = "Masukkan usia",
            value = viewModel.usiaValue,
            onValueChange = viewModel::updateUsia,
            isError = viewModel.hasFieldError("usia"),
            errorMessage = viewModel.getFieldError("usia"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Hilang Sejak",
            value = viewModel.hilangSejakValue,
            onValueChange = viewModel::updateHilangSejak,
            isError = viewModel.hasFieldError("hilang_sejak"),
            errorMessage = viewModel.getFieldError("hilang_sejak")
        )
    }
}
