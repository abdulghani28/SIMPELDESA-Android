package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratlainnya.suratkuasa

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
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
internal fun SuratKuasa2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pemberi Kuasa", "Informasi Penerima Kuasa"),
                currentStep = 2
            )
        }

        item {
            InformasiPenerimaKuasa()
        }
    }
}

@Composable
private fun InformasiPenerimaKuasa() {
    Column {
        SectionTitle("Informasi Penerima Kuasa")

        Spacer(modifier = Modifier.height(16.dp))

        var nikValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var jabatanValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK penerima kuasa",
            value = nikValue,
            onValueChange = { nikValue = it },
            isError = false,
            errorMessage = "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap penerima kuasa",
            value = namaValue,
            onValueChange = { namaValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jabatan",
            placeholder = "Masukkan jabatan penerima kuasa",
            value = jabatanValue,
            onValueChange = { jabatanValue = it },
            isError = false,
            errorMessage = ""
        )
    }
}