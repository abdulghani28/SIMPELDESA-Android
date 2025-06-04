package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.spkehilangan

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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.kehilangan.SPKehilanganViewModel

@Composable
internal fun SPKehilangan2Content(
    viewModel: SPKehilanganViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Barang Hilang"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiBarangHilang(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiBarangHilang(
    viewModel: SPKehilanganViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Barang Hilang")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jenis Barang",
            placeholder = "Masukkan jenis barang",
            value = viewModel.jenisBarangValue,
            onValueChange = viewModel::updateJenisBarang,
            isError = viewModel.hasFieldError("jenis_barang"),
            errorMessage = viewModel.getFieldError("jenis_barang")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Ciri-ciri",
            placeholder = "Masukkan ciri-ciri",
            value = viewModel.ciriCiriBarangValue,
            onValueChange = viewModel::updateCiriCiriBarang,
            isError = viewModel.hasFieldError("ciri_barang"),
            errorMessage = viewModel.getFieldError("ciri_barang")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Tempat Kehilangan",
            placeholder = "Masukkan tempat kehilangan",
            value = viewModel.tempatKehilanganValue,
            onValueChange = viewModel::updateTempatKehilangan,
            isError = viewModel.hasFieldError("tempat_kehilangan"),
            errorMessage = viewModel.getFieldError("tempat_kehilangan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Waktu Kehilangan",
            value = viewModel.tanggalKehilanganValue,
            onValueChange = viewModel::updateTanggalKehilangan,
            isError = viewModel.hasFieldError("tanggal_kehilangan"),
            errorMessage = viewModel.getFieldError("tanggal_kehilangan")
        )
    }
}