package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skberpergian

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
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
internal fun SKBerpergian2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Kepergian", "Informasi Pelengkap"),
                currentStep = 2
            )
        }

        item {
            InformasiKepergian()
        }
    }
}

@Composable
private fun InformasiKepergian() {
    Column {
        SectionTitle("Informasi Kepergian")

        Spacer(modifier = Modifier.height(16.dp))

        var tempatTujuanValue by remember { mutableStateOf("") }
        var maksudTujuanValue by remember { mutableStateOf("") }
        var jumlahWaktuValue by remember { mutableStateOf("") }
        var satuanWaktuValue by remember { mutableStateOf("") }
        var tanggalKeberangkatanValue by remember { mutableStateOf("") }
        var jumlahPengikutValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Tempat Tujuan",
            placeholder = "Masukkan tempat tujuam",
            value = tempatTujuanValue,
            onValueChange = { tempatTujuanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Maksud Tujuan",
            placeholder = "Masukkan maksud tujuam",
            value = maksudTujuanValue,
            onValueChange = { maksudTujuanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Lamanya",
                    placeholder = "0",
                    value = jumlahWaktuValue,
                    onValueChange = { jumlahWaktuValue = it },
                    isError = false,
                    errorMessage = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DropdownField(
                    label = "",
                    value = satuanWaktuValue,
                    onValueChange = { satuanWaktuValue = it },
                    options = listOf("Jam", "Hari", "Bulan"),
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Keberangkatan",
            value = tanggalKeberangkatanValue,
            onValueChange = { tanggalKeberangkatanValue = it },
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jumlah Pengikut (Orang)",
            placeholder = "0",
            value = jumlahPengikutValue,
            onValueChange = { jumlahPengikutValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}