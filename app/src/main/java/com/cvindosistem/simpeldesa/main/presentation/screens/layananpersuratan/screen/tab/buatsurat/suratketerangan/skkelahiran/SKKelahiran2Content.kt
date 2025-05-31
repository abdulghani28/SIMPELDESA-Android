package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkelahiran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKKelahiranViewModel

@Composable
internal fun SKKelahiran2Content(
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
            InformasiAyah(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiAyah(
    viewModel: SKKelahiranViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Ayah")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "NIK",
            value = viewModel.nikAyahValue,
            onValueChange = viewModel::updateNikAyah,
            isError = validationErrors.containsKey("nik_ayah"),
            errorMessage = validationErrors["nik_ayah"],
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaAyahValue,
            onValueChange = viewModel::updateNamaAyah,
            isError = validationErrors.containsKey("nama_ayah"),
            errorMessage = validationErrors["nama_ayah"]
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirAyahValue,
                    onValueChange = viewModel::updateTempatLahirAyah,
                    isError = validationErrors.containsKey("tempat_lahir_ayah"),
                    errorMessage = validationErrors["tempat_lahir_ayah"]
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirAyahValue,
                    onValueChange = viewModel::updateTanggalLahirAyah,
                    isError = validationErrors.containsKey("tanggal_lahir_ayah"),
                    errorMessage = validationErrors["tanggal_lahir_ayah"]
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan Alamat Lengkap",
            value = viewModel.alamatAyahValue,
            onValueChange = viewModel::updateAlamatAyah,
            isError = validationErrors.containsKey("alamat_ayah"),
            errorMessage = validationErrors["alamat_ayah"]
        )
    }
}
