package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skpenghasilan

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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator


@Composable
internal fun SKPenghasilan3Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Orang Tua", "Informasi Anak", "Informasi Pelengkap"),
                currentStep = 3
            )
        }

        item {
            InformasiPelengkapPenghasilan()
        }
    }
}

@Composable
private fun InformasiPelengkapPenghasilan() {
    Column {
        SectionTitle("Informasi Pelengkap")

        Spacer(modifier = Modifier.height(16.dp))

        var keperluanValue by remember { mutableStateOf("") }

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = keperluanValue,
            onValueChange = { keperluanValue = it },
            isError = false,
            errorMessage = null
        )
    }
}
