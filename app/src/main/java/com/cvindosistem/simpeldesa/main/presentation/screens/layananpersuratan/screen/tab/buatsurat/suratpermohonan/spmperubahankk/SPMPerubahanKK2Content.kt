package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmperubahankk

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.perubahankk.SPMPerubahanKKViewModel

@Composable
internal fun SPMPerubahanKK2Content(
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
            DataKeluarga(
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun DataKeluarga(
    viewModel: SPMPerubahanKKViewModel,
) {
    Column {
        SectionTitle("Data Keluarga")

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap keluarga",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Kartu Keluarga (KK)",
            placeholder = "Masukkan nomor KK yang akan diubah",
            value = viewModel.noKkValue,
            onValueChange = viewModel::updateNoKk,
            isError = viewModel.hasFieldError("no_kk"),
            errorMessage = viewModel.getFieldError("no_kk"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alasan Permohonan",
            placeholder = "Jelaskan alasan mengapa perlu melakukan perubahan KK",
            value = viewModel.alasanPermohonanValue,
            onValueChange = viewModel::updateAlasanPermohonan,
            isError = viewModel.hasFieldError("alasan_permohonan"),
            errorMessage = viewModel.getFieldError("alasan_permohonan"),
        )
    }
}
