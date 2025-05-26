package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratpengantar.sppernikahan

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
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.core.components.TimePickerField

@Composable
internal fun SPPernikahan5Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf("Informasi Calon Suami", "Informasi Orang Tua Calon Suami", "Informasi Calon Istri", "Informasi Orang Tua Calon Istri", "Informasi Rencana Pernikahan"),
                currentStep = 5
            )
        }

        item {
            InformasiRencanaPernikahan()
        }
    }
}

@Composable
private fun InformasiRencanaPernikahan() {
    Column {
        SectionTitle("Informasi Rencana Pernikahan")

        Spacer(modifier = Modifier.height(16.dp))

        var hariValue by remember { mutableStateOf("") }
        var tanggalPernikahanValue by remember { mutableStateOf("") }
        var jamValue by remember { mutableStateOf("") }
        var tempatPernikahanValue by remember { mutableStateOf("") }

        DropdownField(
            label = "Hari",
            value = hariValue,
            onValueChange = { hariValue = it },
            options = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"),
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Pernikahan",
            value = tanggalPernikahanValue,
            onValueChange = { tanggalPernikahanValue = it },
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TimePickerField(
            label = "Jam",
            value = jamValue,
            onValueChange = { jamValue = it },
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Pernikahan",
            placeholder = "Masukkan tempat pernikahan",
            value = tempatPernikahanValue,
            onValueChange = { tempatPernikahanValue = it },
            isError = false,
            errorMessage = null
        )
    }
}
