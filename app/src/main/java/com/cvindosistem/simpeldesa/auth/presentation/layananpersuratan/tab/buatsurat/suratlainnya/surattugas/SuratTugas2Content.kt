package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratlainnya.surattugas

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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
internal fun SuratTugas2Content(
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
            InformasiTugas()
        }
    }
}

@Composable
private fun InformasiTugas() {
    Column {
        SectionTitle("Informasi Tugas")

        Spacer(modifier = Modifier.height(16.dp))

        var ditugaskanUntukValue by remember { mutableStateOf("") }
        var deskripsiTugasValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Ditugaskan Untuk",
            placeholder = "Masukkan tujuan penugasan",
            value = ditugaskanUntukValue,
            onValueChange = { ditugaskanUntukValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Deskripsi/Detail Tugas (opsional)",
            placeholder = "Masukkan deskripsi, detail, dan penjelasan yang akan dilaksanakan oleh penerima tugas",
            value = deskripsiTugasValue,
            onValueChange = { deskripsiTugasValue = it },
            isError = false,
            errorMessage = ""
        )
    }
}