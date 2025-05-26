package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab.buatsurat.suratpengantar.spkehilangan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

@Composable
internal fun SPKehilangan2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Barang Hilang"),
                currentStep = 2
            )
        }

        item {
            InformasiBarangHilang()
        }
    }
}

@Composable
private fun InformasiBarangHilang() {
    Column {
        SectionTitle("Informasi Barang Hilang")

        Spacer(modifier = Modifier.height(16.dp))

        var jenisBarangValue by remember { mutableStateOf("") }
        var ciriCiriValue by remember { mutableStateOf("") }
        var tempatKehilanganValue by remember { mutableStateOf("") }
        var waktuKehilangan by remember { mutableStateOf("") }

        AppTextField(
            label = "Jenis Barang",
            placeholder = "Masukkan jenis barang",
            value = jenisBarangValue,
            onValueChange = { jenisBarangValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Ciri-ciri",
            placeholder = "Masukkan ciri-ciri",
            value = ciriCiriValue,
            onValueChange = { ciriCiriValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Tempat Kehilangan",
            placeholder = "Masukkan tempat kehilangan",
            value = tempatKehilanganValue,
            onValueChange = { tempatKehilanganValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Waktu Kehilangan",
            value = waktuKehilangan,
            onValueChange = { waktuKehilangan = it },
            isError = false,
            errorMessage = null,
        )
    }
}