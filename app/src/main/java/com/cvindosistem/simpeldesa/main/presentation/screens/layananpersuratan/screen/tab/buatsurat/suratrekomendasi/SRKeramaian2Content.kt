package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratrekomendasi

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
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.TimePickerField
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SRKeramaianViewModel

@Composable
internal fun SRKeramaian2Content(
    viewModel: SRKeramaianViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Kegiatan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiKegiatan(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiKegiatan(
    viewModel: SRKeramaianViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Kegiatan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Acara",
            placeholder = "Masukkan nama acara",
            value = viewModel.namaAcaraValue,
            onValueChange = viewModel::updateNamaAcara,
            isError = viewModel.hasFieldError("nama_acara"),
            errorMessage = viewModel.getFieldError("nama_acara")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Acara",
            placeholder = "Masukkan tempat acara",
            value = viewModel.tempatAcaraValue,
            onValueChange = viewModel::updateTempatAcara,
            isError = viewModel.hasFieldError("tempat_acara"),
            errorMessage = viewModel.getFieldError("tempat_acara")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DropdownField(
                    label = "Hari",
                    value = viewModel.hariValue,
                    onValueChange = viewModel::updateHari,
                    options = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"),
                    isError = viewModel.hasFieldError("hari"),
                    errorMessage = viewModel.getFieldError("hari"),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Acara",
                    value = viewModel.tanggalValue,
                    onValueChange = viewModel::updateTanggal,
                    isError = viewModel.hasFieldError("tanggal"),
                    errorMessage = viewModel.getFieldError("tanggal"),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TimePickerField(
                    label = "Dimulai Dari Pukul (WIB)",
                    value = viewModel.jamMulaiValue,
                    onValueChange = viewModel::updateJamMulai,
                    isError = viewModel.hasFieldError("dimulai"),
                    errorMessage = viewModel.getFieldError("dimulai"),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                TimePickerField(
                    label = "Selesai Pada (WIB)",
                    value = viewModel.jamSelesaiValue,
                    onValueChange = viewModel::updateJamSelesai,
                    isError = viewModel.hasFieldError("selesai"),
                    errorMessage = viewModel.getFieldError("selesai"),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Penanggung Jawab",
            placeholder = "Masukkan nama penanggung jawab",
            value = viewModel.penanggungJawabValue,
            onValueChange = viewModel::updatePenanggungJawab,
            isError = viewModel.hasFieldError("penanggung_jawab"),
            errorMessage = viewModel.getFieldError("penanggung_jawab")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kontak",
            placeholder = "Masukkan nomor kontak penanggungjawab",
            value = viewModel.kontakValue,
            onValueChange = viewModel::updateKontak,
            isError = viewModel.hasFieldError("kontak"),
            errorMessage = viewModel.getFieldError("kontak"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}