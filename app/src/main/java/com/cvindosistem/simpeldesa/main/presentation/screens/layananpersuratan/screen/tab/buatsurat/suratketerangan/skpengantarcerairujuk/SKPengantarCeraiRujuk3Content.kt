package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skpengantarcerairujuk

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pengantarcerairujuk.SKPengantarCeraiRujukViewModel

@Composable
internal fun SKPengantarCeraiRujuk3Content(
    viewModel: SKPengantarCeraiRujukViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemohon", "Data Pasangan", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            KeperluanSection(
                value = viewModel.keperluanValue,
                onValueChange = viewModel::updateKeperluan,
                isError = viewModel.hasFieldError("keperluan"),
                errorMessage = viewModel.getFieldError("keperluan")
            )
        }
    }
}

@Composable
private fun KeperluanSection(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column {
        SectionTitle("Keperluan Pengajuan")

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Tuliskan keperluan pengajuan surat",
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            errorMessage = errorMessage
        )
    }
}
