package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppernikahan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.core.components.TimePickerField
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SPPernikahanViewModel

@Composable
internal fun SPPernikahan5Content(
    viewModel: SPPernikahanViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf("Informasi Calon Suami", "Informasi Orang Tua Calon Suami", "Informasi Calon Istri", "Informasi Orang Tua Calon Istri", "Informasi Rencana Pernikahan"),
                currentStep = 5
            )
        }

        item {
            InformasiRencanaPernikahan(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiRencanaPernikahan(
    viewModel: SPPernikahanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Rencana Pernikahan")

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Hari",
            value = viewModel.hariPernikahanValue.replaceFirstChar { it.uppercase() },
            onValueChange = { viewModel.updateHariPernikahan(it.lowercase()) },
            options = listOf("senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu")
                .map { it.replaceFirstChar { c -> c.uppercase() } },
            isError = viewModel.hasFieldError("hari_pernikahan"),
            errorMessage = viewModel.getFieldError("hari_pernikahan"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Pernikahan",
            value = viewModel.tanggalPernikahanValue,
            onValueChange = viewModel::updateTanggalPernikahan,
            isError = viewModel.hasFieldError("tanggal_pernikahan"),
            errorMessage = viewModel.getFieldError("tanggal_pernikahan"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TimePickerField(
            label = "Jam",
            value = viewModel.jamPernikahanValue,
            onValueChange = viewModel::updateJamPernikahan,
            isError = viewModel.hasFieldError("jam_pernikahan"),
            errorMessage = viewModel.getFieldError("jam_pernikahan"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Pernikahan",
            placeholder = "Masukkan tempat pernikahan",
            value = viewModel.tempatPernikahanValue,
            onValueChange = viewModel::updateTempatPernikahan,
            isError = viewModel.hasFieldError("tempat_pernikahan"),
            errorMessage = viewModel.getFieldError("tempat_pernikahan"),
        )
    }
}
