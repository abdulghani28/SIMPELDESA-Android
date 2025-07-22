package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpernyataan.spnpenguasaanfisikbidangtanah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah.SPNPenguasaanFisikBidangTanahViewModel

@Composable
internal fun SPNPenguasaanFisikBidangTanah5Content(
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
            Keperluan(viewModel = viewModel)
        }
    }
}

@Composable
private fun Keperluan(
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
) {
    Column {
        SectionTitle("Keperluan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Keperluan Surat Pernyataan",
            placeholder = "Masukkan keperluan surat pernyataan",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan")
        )
    }
}