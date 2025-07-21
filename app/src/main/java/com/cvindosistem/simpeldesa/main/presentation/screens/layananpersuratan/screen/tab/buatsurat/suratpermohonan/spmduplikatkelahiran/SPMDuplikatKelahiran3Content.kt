package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmduplikatkelahiran

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatkelahiran.SPMDuplikatKelahiranViewModel

@Composable
internal fun SPMDuplikatKelahiran3Content(
    viewModel: SPMDuplikatKelahiranViewModel,
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Anak", "Informasi Orang Tua", "Informasi Pelengkap"),
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
    viewModel: SPMDuplikatKelahiranViewModel
) {
    Column {
        SectionTitle("Informasi Ayah")

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
            label = "Alamat Ayah",
            placeholder = "Masukkan alamat ayah",
            value = viewModel.alamatAyahValue,
            onValueChange = viewModel::updateAlamatAyah,
            isError = viewModel.hasFieldError("alamat_ayah"),
            errorMessage = viewModel.getFieldError("alamat_ayah")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Ayah",
            placeholder = "Masukkan pekerjaan ayah",
            value = viewModel.pekerjaanAyahValue,
            onValueChange = viewModel::updatePekerjaanAyah,
            isError = viewModel.hasFieldError("pekerjaan_ayah"),
            errorMessage = viewModel.getFieldError("pekerjaan_ayah")
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle("Informasi Ibu")

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
            label = "Alamat Ibu",
            placeholder = "Masukkan alamat ibu",
            value = viewModel.alamatIbuValue,
            onValueChange = viewModel::updateAlamatIbu,
            isError = viewModel.hasFieldError("alamat_ibu"),
            errorMessage = viewModel.getFieldError("alamat_ibu")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Ibu",
            placeholder = "Masukkan pekerjaan ibu",
            value = viewModel.pekerjaanIbuValue,
            onValueChange = viewModel::updatePekerjaanIbu,
            isError = viewModel.hasFieldError("pekerjaan_ibu"),
            errorMessage = viewModel.getFieldError("pekerjaan_ibu")
        )
    }
}