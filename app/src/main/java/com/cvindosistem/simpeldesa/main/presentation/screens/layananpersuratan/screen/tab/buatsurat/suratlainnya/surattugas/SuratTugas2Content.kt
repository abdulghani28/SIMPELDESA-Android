package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratlainnya.surattugas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.SuratTugasViewModel

@Composable
internal fun SuratTugas2Content(
    viewModel: SuratTugasViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Penerima Tugas", "Informasi Tugas"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiTugas(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiTugas(
    viewModel: SuratTugasViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Tugas")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Ditugaskan Untuk",
            placeholder = "Masukkan tujuan penugasan",
            value = viewModel.ditugaskanUntukValue,
            onValueChange = viewModel::updateDitugaskanUntuk,
            isError = viewModel.hasFieldError("ditugaskan_untuk"),
            errorMessage = viewModel.getFieldError("ditugaskan_untuk")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Deskripsi/Detail Tugas",
            placeholder = "Masukkan deskripsi, detail, dan penjelasan yang akan dilaksanakan oleh penerima tugas",
            value = viewModel.deskripsiValue,
            onValueChange = viewModel::updateDeskripsi,
            isError = viewModel.hasFieldError("deskripsi"),
            errorMessage = viewModel.getFieldError("deskripsi")
        )
    }
}