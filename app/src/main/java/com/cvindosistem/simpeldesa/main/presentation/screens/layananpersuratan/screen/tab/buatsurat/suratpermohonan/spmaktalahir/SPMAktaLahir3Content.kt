package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmaktalahir

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir.SPMAktaLahirViewModel

@Composable
internal fun SPMAktaLahir3Content(
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
            InformasiOrangTua(viewModel = viewModel)
        }
    }
}

@Composable
private fun InformasiOrangTua(
    viewModel: SPMAktaLahirViewModel
) {
    Column {
        SectionTitle("Informasi Orang Tua")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ayah",
            placeholder = "Masukkan nama ayah",
            value = viewModel.namaAyahValue,
            onValueChange = viewModel::updateNamaAyah,
            isError = viewModel.hasFieldError("nama_ayah"),
            errorMessage = viewModel.getFieldError("nama_ayah")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Ayah",
            placeholder = "Masukkan NIK ayah",
            value = viewModel.nikAyahValue,
            onValueChange = viewModel::updateNikAyah,
            isError = viewModel.hasFieldError("nik_ayah"),
            errorMessage = viewModel.getFieldError("nik_ayah"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ibu",
            placeholder = "Masukkan nama ibu",
            value = viewModel.namaIbuValue,
            onValueChange = viewModel::updateNamaIbu,
            isError = viewModel.hasFieldError("nama_ibu"),
            errorMessage = viewModel.getFieldError("nama_ibu")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Ibu",
            placeholder = "Masukkan NIK ibu",
            value = viewModel.nikIbuValue,
            onValueChange = viewModel::updateNikIbu,
            isError = viewModel.hasFieldError("nik_ibu"),
            errorMessage = viewModel.getFieldError("nik_ibu"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Orang Tua",
            placeholder = "Masukkan alamat tempat tinggal orang tua",
            value = viewModel.alamatOrangTuaValue,
            onValueChange = viewModel::updateAlamatOrangTua,
            isError = viewModel.hasFieldError("alamat_orang_tua"),
            errorMessage = viewModel.getFieldError("alamat_orang_tua")
        )
    }
}