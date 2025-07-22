package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbiodatawarga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata.SKBiodataWargaViewModel

@Composable
internal fun SKBiodataWarga3Content(
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
            InformasiPerkawinanSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPerkawinanSection(
    viewModel: SKBiodataWargaViewModel
) {
    Column {
        SectionTitle("Informasi Perkawinan")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Akta Kawin",
            placeholder = "Masukkan nomor akta kawin",
            value = viewModel.aktaKawinValue,
            onValueChange = viewModel::updateAktaKawin,
            isError = viewModel.hasFieldError("akta_kawin"),
            errorMessage = viewModel.getFieldError("akta_kawin")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Kawin",
            value = viewModel.tanggalKawinValue,
            onValueChange = viewModel::updateTanggalKawin,
            isError = viewModel.hasFieldError("tanggal_kawin"),
            errorMessage = viewModel.getFieldError("tanggal_kawin")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ambil label status kawin yang dipilih
        val statusKawinNama = viewModel.statusKawinList.find { it.id == viewModel.statusValue }?.nama

        // Jika status = "Cerai", tampilkan field cerai
        if (statusKawinNama?.contains("cerai", ignoreCase = true) == true) {
            AppTextField(
                label = "Nomor Akta Cerai",
                placeholder = "Masukkan nomor akta cerai",
                value = viewModel.aktaCeraiValue,
                onValueChange = viewModel::updateAktaCerai,
                isError = viewModel.hasFieldError("akta_cerai"),
                errorMessage = viewModel.getFieldError("akta_cerai")
            )

            Spacer(modifier = Modifier.height(16.dp))

            DatePickerField(
                label = "Tanggal Cerai",
                value = viewModel.tanggalCeraiValue,
                onValueChange = viewModel::updateTanggalCerai,
                isError = viewModel.hasFieldError("tanggal_cerai"),
                errorMessage = viewModel.getFieldError("tanggal_cerai")
            )
        }
    }
}
