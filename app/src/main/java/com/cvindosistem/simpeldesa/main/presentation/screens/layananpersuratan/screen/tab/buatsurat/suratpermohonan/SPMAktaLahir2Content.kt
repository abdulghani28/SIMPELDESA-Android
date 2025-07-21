package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan

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
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir.SPMAktaLahirViewModel

@Composable
internal fun SPMAktaLahir2Content(
    viewModel: SPMAktaLahirViewModel,
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Anak", "Informasi Orang Tua", "Informasi Lainnya"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiAnak(viewModel = viewModel)
        }
    }
}

@Composable
private fun InformasiAnak(
    viewModel: SPMAktaLahirViewModel
) {
    Column {
        SectionTitle("Informasi Anak")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Anak",
            placeholder = "Masukkan nama anak",
            value = viewModel.namaAnakValue,
            onValueChange = viewModel::updateNamaAnak,
            isError = viewModel.hasFieldError("nama_anak"),
            errorMessage = viewModel.getFieldError("nama_anak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Anak",
            placeholder = "Masukkan alamat anak",
            value = viewModel.alamatAnakValue,
            onValueChange = viewModel::updateAlamatAnak,
            isError = viewModel.hasFieldError("alamat_anak"),
            errorMessage = viewModel.getFieldError("alamat_anak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Anak",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirAnakValue,
                    onValueChange = viewModel::updateTempatLahirAnak,
                    isError = viewModel.hasFieldError("tempat_lahir_anak"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_anak")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Anak",
                    value = viewModel.tanggalLahirAnakValue,
                    onValueChange = viewModel::updateTanggalLahirAnak,
                    isError = viewModel.hasFieldError("tanggal_lahir_anak"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_anak")
                )
            }
        }
    }
}
