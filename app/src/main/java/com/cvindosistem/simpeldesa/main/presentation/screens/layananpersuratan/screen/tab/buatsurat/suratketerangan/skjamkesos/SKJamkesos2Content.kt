package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skjamkesos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jamkesos.SKJamkesosViewModel

@Composable
internal fun SKJamkesos2Content(
    viewModel: SKJamkesosViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Kartu", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiKartu(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiKartu(
    viewModel: SKJamkesosViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Kartu")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Kartu",
            placeholder = "Masukkan nomor kartu",
            value = viewModel.noKartuValue,
            onValueChange = viewModel::updateNoKartu,
            isError = viewModel.hasFieldError("no_kartu"),
            errorMessage = viewModel.getFieldError("no_kartu"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Berlaku Dari",
            value = viewModel.berlakuDariValue,
            onValueChange = viewModel::updateBerlakuDari,
            isError = viewModel.hasFieldError("berlaku_dari"),
            errorMessage = viewModel.getFieldError("berlaku_dari")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Berlaku Sampai",
            value = viewModel.berlakuSampaiValue,
            onValueChange = viewModel::updateBerlakuSampai,
            isError = viewModel.hasFieldError("berlaku_sampai"),
            errorMessage = viewModel.getFieldError("berlaku_sampai")
        )
    }
}
