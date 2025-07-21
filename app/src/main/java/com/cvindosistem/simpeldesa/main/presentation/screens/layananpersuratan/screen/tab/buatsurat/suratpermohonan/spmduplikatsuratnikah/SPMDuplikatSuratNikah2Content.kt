package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmduplikatsuratnikah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatsuratnikah.SPMDuplikatSuratNikahViewModel

@Composable
internal fun SPMDuplikatSuratNikah2Content(
    viewModel: SPMDuplikatSuratNikahViewModel,
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Pernikahan", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPernikahan(viewModel = viewModel)
        }
    }
}

@Composable
private fun InformasiPernikahan(
    viewModel: SPMDuplikatSuratNikahViewModel
) {
    Column {
        SectionTitle("Informasi Pernikahan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Pasangan",
            placeholder = "Masukkan nama pasangan",
            value = viewModel.namaPasanganValue,
            onValueChange = viewModel::updateNamaPasangan,
            isError = viewModel.hasFieldError("nama_pasangan"),
            errorMessage = viewModel.getFieldError("nama_pasangan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Nikah",
            value = viewModel.tanggalNikahValue,
            onValueChange = viewModel::updateTanggalNikah,
            isError = viewModel.hasFieldError("tanggal_nikah"),
            errorMessage = viewModel.getFieldError("tanggal_nikah")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kepala Keluarga",
            placeholder = "Masukkan nama kepala keluarga",
            value = viewModel.kepalaKeluargaValue,
            onValueChange = viewModel::updateKepalaKeluarga,
            isError = viewModel.hasFieldError("kepala_keluarga"),
            errorMessage = viewModel.getFieldError("kepala_keluarga")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kecamatan KUA",
            placeholder = "Masukkan kecamatan tempat menikah",
            value = viewModel.kecamatanKuaValue,
            onValueChange = viewModel::updateKecamatanKua,
            isError = viewModel.hasFieldError("kecamatan_kua"),
            errorMessage = viewModel.getFieldError("kecamatan_kua")
        )
    }
}
