package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmperubahankk

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.perubahankk.SPMPerubahanKKViewModel

@Composable
internal fun SPMPerubahanKK3Content(
    viewModel: SPMPerubahanKKViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pribadi", "Data Keluarga", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            Keperluan(
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun Keperluan(
    viewModel: SPMPerubahanKKViewModel,
) {
    Column {
        SectionTitle("Keperluan")

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan Perubahan KK",
            placeholder = "Jelaskan untuk keperluan apa perubahan KK ini dibutuhkan",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan"),
        )

        Spacer(modifier = Modifier.height(24.dp))

    }
}