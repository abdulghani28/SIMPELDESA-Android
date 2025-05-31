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
internal fun SKKelahiran3Content(
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
            InformasiIbu(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiIbu(
    viewModel: SKKelahiranViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Ibu")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "NIK",
            value = viewModel.nikIbuValue,
            onValueChange = viewModel::updateNikIbu,
            isError = validationErrors.containsKey("nik_ibu"),
            errorMessage = validationErrors["nik_ibu"]
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaIbuValue,
            onValueChange = viewModel::updateNamaIbu,
            isError = validationErrors.containsKey("nama_ibu"),
            errorMessage = validationErrors["nama_ibu"]
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
                    value = viewModel.tempatLahirIbuValue,
                    onValueChange = viewModel::updateTempatLahirIbu,
                    isError = validationErrors.containsKey("tempat_lahir_ibu"),
                    errorMessage = validationErrors["tempat_lahir_ibu"]
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirIbuValue,
                    onValueChange = viewModel::updateTanggalLahirIbu,
                    isError = validationErrors.containsKey("tanggal_lahir_ibu"),
                    errorMessage = validationErrors["tanggal_lahir_ibu"]
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan Alamat Lengkap",
            value = viewModel.alamatIbuValue,
            onValueChange = viewModel::updateAlamatIbu,
            isError = validationErrors.containsKey("alamat_ibu"),
            errorMessage = validationErrors["alamat_ibu"]
        )
    }
}
