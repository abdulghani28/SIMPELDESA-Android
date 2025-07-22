package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpernyataan.spnpenguasaanfisikbidangtanah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.CheckBoxField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah.SPNPenguasaanFisikBidangTanahViewModel

@Composable
internal fun SPNPenguasaanFisikBidangTanah4Content(
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf(
                    "Data Pemohon",
                    "Lokasi & Identitas Tanah",
                    "Perolehan & Batas Tanah",
                    "Data Saksi",
                    "Keperluan"
                ),
                currentStep = viewModel.currentStep
            )
        }

        item {
            DataSaksi(viewModel = viewModel)
        }
    }
}

@Composable
private fun DataSaksi(
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
) {
    Column {
        SectionTitle("Data Saksi")

        Spacer(modifier = Modifier.height(16.dp))

        // Saksi 1
        Text(
            text = "Saksi 1",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        AppTextField(
            label = "NIK Saksi 1",
            placeholder = "Masukkan NIK saksi 1",
            value = viewModel.nik1Value,
            onValueChange = viewModel::updateNik1,
            isError = viewModel.hasFieldError("nik1"),
            errorMessage = viewModel.getFieldError("nik1"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Nama Saksi 1",
                    placeholder = "Masukkan nama saksi 1",
                    value = viewModel.nama1Value,
                    onValueChange = viewModel::updateNama1,
                    isError = viewModel.hasFieldError("nama1"),
                    errorMessage = viewModel.getFieldError("nama1")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Pekerjaan Saksi 1",
                    placeholder = "Masukkan pekerjaan",
                    value = viewModel.pekerjaan1Value,
                    onValueChange = viewModel::updatePekerjaan1,
                    isError = viewModel.hasFieldError("pekerjaan1"),
                    errorMessage = viewModel.getFieldError("pekerjaan1")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CheckBoxField(
            label = "Saksi 1 adalah warga desa setempat",
            checked = viewModel.isSaksi1WargaDesaValue,
            onCheckedChange = viewModel::updateIsSaksi1WargaDesa
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Saksi 2
        Text(
            text = "Saksi 2",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        AppTextField(
            label = "NIK Saksi 2",
            placeholder = "Masukkan NIK saksi 2",
            value = viewModel.nik2Value,
            onValueChange = viewModel::updateNik2,
            isError = viewModel.hasFieldError("nik2"),
            errorMessage = viewModel.getFieldError("nik2"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Nama Saksi 2",
                    placeholder = "Masukkan nama saksi 2",
                    value = viewModel.nama2Value,
                    onValueChange = viewModel::updateNama2,
                    isError = viewModel.hasFieldError("nama2"),
                    errorMessage = viewModel.getFieldError("nama2")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Pekerjaan Saksi 2",
                    placeholder = "Masukkan pekerjaan",
                    value = viewModel.pekerjaan2Value,
                    onValueChange = viewModel::updatePekerjaan2,
                    isError = viewModel.hasFieldError("pekerjaan2"),
                    errorMessage = viewModel.getFieldError("pekerjaan2")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CheckBoxField(
            label = "Saksi 2 adalah warga desa setempat",
            checked = viewModel.isSaksi2WargaDesaValue,
            onCheckedChange = viewModel::updateIsSaksi2WargaDesa
        )
    }
}
