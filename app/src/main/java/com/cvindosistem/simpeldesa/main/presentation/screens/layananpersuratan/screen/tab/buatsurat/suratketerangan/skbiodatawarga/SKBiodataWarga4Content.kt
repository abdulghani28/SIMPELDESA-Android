package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbiodatawarga

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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata.SKBiodataWargaViewModel

@Composable
internal fun SKBiodataWarga4Content(
    viewModel: SKBiodataWargaViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Diri", "Alamat & Status", "Perkawinan", "Data Orang Tua", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            DataOrangTuaSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun DataOrangTuaSection(
    viewModel: SKBiodataWargaViewModel
) {
    Column {
        SectionTitle("Data Orang Tua")
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
            label = "Nomor Akta Lahir",
            placeholder = "Masukkan nomor akta lahir",
            value = viewModel.aktaLahirValue,
            onValueChange = viewModel::updateAktaLahir,
            isError = viewModel.hasFieldError("akta_lahir"),
            errorMessage = viewModel.getFieldError("akta_lahir")
        )
    }
}
