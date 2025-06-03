package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkematian

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian.SKKematianViewModel

@Composable
internal fun SKKematian3Content(
    viewModel: SKKematianViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Mendiang", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPelengkap(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPelengkap(
    viewModel: SKKematianViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Pelengkap")

        Spacer(modifier = Modifier.height(16.dp))

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
