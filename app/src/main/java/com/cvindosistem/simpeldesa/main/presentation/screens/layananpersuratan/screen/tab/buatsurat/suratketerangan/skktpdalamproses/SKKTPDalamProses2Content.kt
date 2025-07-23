package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skktpdalamproses

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ktpdalamproses.SKKTPDalamProsesViewModel

@Composable
internal fun SKKTPDalamProses2Content(
    viewModel: SKKTPDalamProsesViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            KeperluanPelapor(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun KeperluanPelapor(
    viewModel: SKKTPDalamProsesViewModel
) {
    Column {
        SectionTitle("Informasi Pelengkap")

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan")
        )
    }
}