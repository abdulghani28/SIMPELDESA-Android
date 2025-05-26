package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skgaib

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
internal fun SKGhaib2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Orang Hilang"),
                currentStep = 3
            )
        }

        item {
            InformasiOrangYangHilang()
        }
    }
}

@Composable
private fun InformasiOrangYangHilang() {
    Column {
        SectionTitle("Informasi Orang Hilang")

        Spacer(modifier = Modifier.height(16.dp))

        var namaValue by remember { mutableStateOf("Joko Subianto") }
        var selectedGender by remember { mutableStateOf("Laki-laki") }
        var usiaValue by remember { mutableStateOf("27 Tahun") }
        var alamatValue by remember { mutableStateOf("Jl. Kangkung Lemas, Kec. Terong Belanda, Kab. Kebun Subur") }
        var hilangSejakValue by remember { mutableStateOf("06/05/1973") }

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = namaValue,
            onValueChange = { namaValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = selectedGender,
            onGenderSelected = { selectedGender = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Usia (Tahun)",
            placeholder = "Masukkan usia",
            value = usiaValue,
            onValueChange = { usiaValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = alamatValue,
            onValueChange = { alamatValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Hilang Sejak",
            value = hilangSejakValue,
            onValueChange = { hilangSejakValue = it }
        )
    }
}
