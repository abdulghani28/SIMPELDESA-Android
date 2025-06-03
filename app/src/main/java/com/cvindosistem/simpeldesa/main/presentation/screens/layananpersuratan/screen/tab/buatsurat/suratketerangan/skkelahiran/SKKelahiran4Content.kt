package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkelahiran

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran.SKKelahiranViewModel

@Composable
internal fun SKKelahiran4Content(
    viewModel: SKKelahiranViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Anak", "Informasi Ayah", "Informasi Ibu", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPelengkap(
                keperluan = viewModel.keperluanValue,
                onKeperluanChange = viewModel::updateKeperluan,
                error = validationErrors["keperluan"]
            )
        }
    }
}

@Composable
private fun InformasiPelengkap(
    keperluan: String,
    onKeperluanChange: (String) -> Unit,
    error: String?
) {
    Column {
        SectionTitle("Informasi Pelengkap")

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = keperluan,
            onValueChange = onKeperluanChange,
            isError = error != null,
            errorMessage = error
        )
    }
}
