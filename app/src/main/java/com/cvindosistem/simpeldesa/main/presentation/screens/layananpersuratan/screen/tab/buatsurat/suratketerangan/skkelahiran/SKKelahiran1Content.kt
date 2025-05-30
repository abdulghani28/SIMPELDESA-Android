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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.TimePickerField

@Composable
internal fun SKKelahiran1Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Anak", "Informasi Ayah", "Informasi Ibu", "Informasi Pelengkap"),
                currentStep = 1
            )
        }

        item {
            InformasiAnak()
        }
    }
}

@Composable
private fun InformasiAnak() {
    Column {
        SectionTitle("Informasi Anak")

        Spacer(modifier = Modifier.height(16.dp))

        var anakKeValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var tempatLahirValue by remember { mutableStateOf("") }
        var tanggalLahirValue by remember { mutableStateOf("") }
        var jamLahirValue by remember { mutableStateOf("") }
        var jenisKelaminValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Anak Ke-",
            placeholder = "Anak ke-",
            value = anakKeValue,
            onValueChange = { anakKeValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = namaValue,
            onValueChange = { namaValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Lahir",
            placeholder = "Masukkan tempat lahir",
            value = tempatLahirValue,
            onValueChange = { tempatLahirValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = tanggalLahirValue,
                    onValueChange = { tanggalLahirValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                TimePickerField(
                    label = "Jam Lahir",
                    value = jamLahirValue,
                    onValueChange = { jamLahirValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = jenisKelaminValue,
            onGenderSelected = { jenisKelaminValue = it },
            isError = false,
            errorMessage = null
        )
    }
}
