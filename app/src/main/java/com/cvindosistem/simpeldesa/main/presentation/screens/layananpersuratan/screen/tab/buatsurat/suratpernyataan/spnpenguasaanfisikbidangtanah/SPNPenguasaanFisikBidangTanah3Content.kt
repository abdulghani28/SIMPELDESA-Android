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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah.SPNPenguasaanFisikBidangTanahViewModel

@Composable
internal fun SPNPenguasaanFisikBidangTanah3Content(
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
            PerolehanBatasTanah(viewModel = viewModel)
        }
    }
}

@Composable
private fun PerolehanBatasTanah(
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
) {
    Column {
        SectionTitle("Perolehan & Batas Tanah")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Diperoleh dari",
            placeholder = "Masukkan dari siapa diperoleh",
            value = viewModel.diperolehDariValue,
            onValueChange = viewModel::updateDiperolehDari,
            isError = viewModel.hasFieldError("diperoleh_dari"),
            errorMessage = viewModel.getFieldError("diperoleh_dari")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Diperoleh dengan",
                    placeholder = "Cara memperoleh",
                    value = viewModel.diperolehDenganValue,
                    onValueChange = viewModel::updateDiperolehDengan,
                    isError = viewModel.hasFieldError("diperoleh_dengan"),
                    errorMessage = viewModel.getFieldError("diperoleh_dengan")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Diperoleh sejak",
                    placeholder = "Tahun diperoleh",
                    value = viewModel.diperolehSejakValue,
                    onValueChange = viewModel::updateDiperolehSejak,
                    isError = viewModel.hasFieldError("diperoleh_sejak"),
                    errorMessage = viewModel.getFieldError("diperoleh_sejak"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Batas-batas Tanah",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Batas Utara",
                    placeholder = "Masukkan batas utara",
                    value = viewModel.batasUtaraValue,
                    onValueChange = viewModel::updateBatasUtara,
                    isError = viewModel.hasFieldError("batas_utara"),
                    errorMessage = viewModel.getFieldError("batas_utara")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Batas Timur",
                    placeholder = "Masukkan batas timur",
                    value = viewModel.batasTimurValue,
                    onValueChange = viewModel::updateBatasTimur,
                    isError = viewModel.hasFieldError("batas_timur"),
                    errorMessage = viewModel.getFieldError("batas_timur")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Batas Selatan",
                    placeholder = "Masukkan batas selatan",
                    value = viewModel.batasSelatanValue,
                    onValueChange = viewModel::updateBatasSelatan,
                    isError = viewModel.hasFieldError("batas_selatan"),
                    errorMessage = viewModel.getFieldError("batas_selatan")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Batas Barat",
                    placeholder = "Masukkan batas barat",
                    value = viewModel.batasBaratValue,
                    onValueChange = viewModel::updateBatasBarat,
                    isError = viewModel.hasFieldError("batas_barat"),
                    errorMessage = viewModel.getFieldError("batas_barat")
                )
            }
        }
    }
}
