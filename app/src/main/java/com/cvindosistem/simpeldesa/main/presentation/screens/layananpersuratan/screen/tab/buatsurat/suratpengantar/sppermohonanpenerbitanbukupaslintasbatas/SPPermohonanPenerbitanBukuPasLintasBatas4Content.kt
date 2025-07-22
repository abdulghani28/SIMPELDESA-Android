package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppermohonanpenerbitanbukupaslintasbatas

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
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku.SPPermohonanPenerbitanBukuPasLintasBatasViewModel

@Composable
internal fun SPPermohonanPenerbitanBukuPasLintasBatas4Content(
    viewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemohon", "Alamat & Keluarga", "Anggota Keluarga", "Keperluan"),
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
    viewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
) {
    Column {
        SectionTitle("Keperluan")

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan Permohonan",
            placeholder = "Jelaskan keperluan permohonan Anda",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan")
        )
    }
}