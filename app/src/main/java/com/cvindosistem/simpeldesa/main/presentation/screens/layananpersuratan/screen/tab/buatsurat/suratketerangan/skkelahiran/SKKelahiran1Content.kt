package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkelahiran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppNumberField
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.TimePickerField
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran.SKKelahiranViewModel

@Composable
internal fun SKKelahiran1Content(
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
            InformasiAnak(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiAnak(
    viewModel: SKKelahiranViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Anak")

        Spacer(modifier = Modifier.height(16.dp))

        AppNumberField(
            label = "Anak Ke-",
            placeholder = "Anak ke-",
            value = viewModel.anakKeValue,
            onValueChange = viewModel::updateAnakKe,
            isError = validationErrors.containsKey("anak_ke"),
            errorMessage = validationErrors["anak_ke"],
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaValue,
            onValueChange = viewModel::updateNama,
            isError = validationErrors.containsKey("nama"),
            errorMessage = validationErrors["nama"]
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Lahir",
            placeholder = "Masukkan tempat lahir",
            value = viewModel.tempatLahirValue,
            onValueChange = viewModel::updateTempatLahir,
            isError = validationErrors.containsKey("tempat_lahir"),
            errorMessage = validationErrors["tempat_lahir"]
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirValue,
                    onValueChange = viewModel::updateTanggalLahir,
                    isError = validationErrors.containsKey("tanggal_lahir"),
                    errorMessage = validationErrors["tanggal_lahir"]
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                TimePickerField(
                    label = "Jam Lahir",
                    value = viewModel.jamLahirValue,
                    onValueChange = viewModel::updateJamLahir,
                    isError = validationErrors.containsKey("jam_lahir"),
                    errorMessage = validationErrors["jam_lahir"]
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelaminValue,
            onGenderSelected = viewModel::updateJenisKelamin,
            isError = validationErrors.containsKey("jenis_kelamin"),
            errorMessage = validationErrors["jenis_kelamin"]
        )
    }
}
