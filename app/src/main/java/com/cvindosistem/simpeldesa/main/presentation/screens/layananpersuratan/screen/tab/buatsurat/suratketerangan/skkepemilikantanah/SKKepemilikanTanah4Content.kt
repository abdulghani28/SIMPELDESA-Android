package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkepemilikantanah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah.SKKepemilikanTanahViewModel

@Composable
internal fun SKKepemilikanTanah4Content(
    viewModel: SKKepemilikanTanahViewModel,
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemohon", "Informasi Tanah", "Bukti Kepemilikan", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            KeperluanSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun KeperluanSection(
    viewModel: SKKepemilikanTanahViewModel
) {
    Column {
        SectionTitle("Keperluan Surat")
        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan surat keterangan kepemilikan tanah ini",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan"),
        )
    }
}