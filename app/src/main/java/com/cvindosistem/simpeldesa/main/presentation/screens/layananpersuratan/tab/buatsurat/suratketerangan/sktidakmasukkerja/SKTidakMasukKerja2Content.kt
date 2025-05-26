package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab.buatsurat.suratketerangan.sktidakmasukkerja

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
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
internal fun SKTidakMasukKerja2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Perusahaan"),
                currentStep = 2
            )
        }

        item {
            InformasiPerusahaan()
        }
    }
}

@Composable
private fun InformasiPerusahaan() {
    Column {
        SectionTitle("Informasi Perusahaan")

        Spacer(modifier = Modifier.height(16.dp))

        var namaPerusahaanValue by remember { mutableStateOf("") }
        var jabatanValue by remember { mutableStateOf("") }
        var lamaIzinValue by remember { mutableStateOf("") }
        var tanggalIzinValue by remember { mutableStateOf("") }
        var alasanIzinValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nama Perusahaan",
            placeholder = "Masukkan nama perusahaan",
            value = namaPerusahaanValue,
            onValueChange = { namaPerusahaanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jabatan",
            placeholder = "Masukkan jabatan",
            value = jabatanValue,
            onValueChange = { jabatanValue = it },
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
                    label = "Lama Izin",
                    placeholder = "0",
                    value = lamaIzinValue,
                    onValueChange = { lamaIzinValue = it },
                    isError = false,
                    errorMessage = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Terhitung dari tanggal",
                    value = tanggalIzinValue,
                    onValueChange = { tanggalIzinValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alasan Izin",
            placeholder = "Masukkan alasan",
            value = alasanIzinValue,
            onValueChange = { alasanIzinValue = it },
            isError = false,
            errorMessage = null
        )
    }
}