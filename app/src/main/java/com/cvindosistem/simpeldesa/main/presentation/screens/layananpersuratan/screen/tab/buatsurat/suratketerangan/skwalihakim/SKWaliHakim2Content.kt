package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skwalihakim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.walihakim.SKWaliHakimViewModel

@Composable
internal fun SKWaliHakim2Content(
    viewModel: SKWaliHakimViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Pengajuan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPengajuan(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPengajuan(
    viewModel: SKWaliHakimViewModel
) {
    Column {
        SectionTitle("Informasi Pengajuan")

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tujuan",
            placeholder = "Contoh: Dinas Kependudukan",
            value = viewModel.tujuanValue,
            onValueChange = viewModel::updateTujuan,
            isError = viewModel.hasFieldError("tujuan"),
            errorMessage = viewModel.getFieldError("tujuan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Berlaku Mulai",
                    value = viewModel.berlakuMulaiValue,
                    onValueChange = viewModel::updateBerlakuMulai,
                    isError = viewModel.hasFieldError("berlaku_mulai"),
                    errorMessage = viewModel.getFieldError("berlaku_mulai")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Berlaku Sampai",
                    value = viewModel.berlakuSampaiValue,
                    onValueChange = viewModel::updateBerlakuSampai,
                    isError = viewModel.hasFieldError("berlaku_sampai"),
                    errorMessage = viewModel.getFieldError("berlaku_sampai")
                )
            }
        }
    }
}
