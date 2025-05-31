package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratlainnya.suratkuasa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.SuratKuasaViewModel

@Composable
internal fun SuratKuasa2Content(
    viewModel: SuratKuasaViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pemberi Kuasa", "Informasi Penerima Kuasa"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPenerimaKuasa(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPenerimaKuasa(
    viewModel: SuratKuasaViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Penerima Kuasa")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK penerima kuasa",
            value = viewModel.nikPenerimaValue,
            onValueChange = viewModel::updateNikPenerima,
            isError = viewModel.hasFieldError("nik_penerima"),
            errorMessage = viewModel.getFieldError("nik_penerima"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap penerima kuasa",
            value = viewModel.namaPenerimaValue,
            onValueChange = viewModel::updateNamaPenerima,
            isError = viewModel.hasFieldError("nama_penerima"),
            errorMessage = viewModel.getFieldError("nama_penerima")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jabatan",
            placeholder = "Masukkan jabatan penerima kuasa",
            value = viewModel.jabatanPenerima,
            onValueChange = viewModel::updateJabatanPenerima,
            isError = viewModel.hasFieldError("jabatan_penerima"),
            errorMessage = viewModel.getFieldError("jabatan_penerima")
        )
    }
}