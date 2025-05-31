package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skberpergian

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppNumberField
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKBerpergianViewModel

@Composable
internal fun SKBerpergian2Content(
    viewModel: SKBerpergianViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Kepergian", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiKepergian(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiKepergian(
    viewModel: SKBerpergianViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Kepergian")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Tujuan",
            placeholder = "Masukkan tempat tujuan",
            value = viewModel.tempatTujuanValue,
            onValueChange = viewModel::updateTempatTujuan,
            isError = viewModel.hasFieldError("tempat_tujuan"),
            errorMessage = viewModel.getFieldError("tempat_tujuan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Maksud Tujuan",
            placeholder = "Masukkan maksud tujuan",
            value = viewModel.maksudTujuanValue,
            onValueChange = viewModel::updateMaksudTujuan,
            isError = viewModel.hasFieldError("maksud_tujuan"),
            errorMessage = viewModel.getFieldError("maksud_tujuan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppNumberField(
                    label = "Lamanya",
                    placeholder = "0",
                    value = viewModel.lamaValue,
                    onValueChange = viewModel::updateLama,
                    isError = viewModel.hasFieldError("lama"),
                    errorMessage = viewModel.getFieldError("lama"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DropdownField(
                    label = "",
                    value = viewModel.satuanLamaValue,
                    onValueChange = viewModel::updateSatuanLama,
                    options = listOf("hari", "bulan", "tahun"),
                    isError = viewModel.hasFieldError("satuan_lama"),
                    errorMessage = viewModel.getFieldError("satuan_lama")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Keberangkatan",
            value = viewModel.tanggalKeberangkatanValue,
            onValueChange = viewModel::updateTanggalKeberangkatan,
            isError = viewModel.hasFieldError("tanggal_keberangkatan"),
            errorMessage = viewModel.getFieldError("tanggal_keberangkatan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jumlah Pengikut (Orang)",
            placeholder = "0",
            value = viewModel.jumlahPengikutValue,
            onValueChange = viewModel::updateJumlahPengikut,
            isError = viewModel.hasFieldError("jumlah_pengikut"),
            errorMessage = viewModel.getFieldError("jumlah_pengikut"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}
