package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab.buatsurat.suratrekomendasi

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
import com.cvindosistem.simpeldesa.core.components.TimePickerField

@Composable
internal fun SRKeramaian2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Kegiatan"),
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
        SectionTitle("Informasi Kegiatan")

        Spacer(modifier = Modifier.height(16.dp))

        var namaAcaraValue by remember { mutableStateOf("") }
        var tempatAcaraValue by remember { mutableStateOf("") }
        var hariValue by remember { mutableStateOf("") }
        var tanggalValue by remember { mutableStateOf("") }
        var jamMulaiValue by remember { mutableStateOf("") }
        var jamSelesaiValue by remember { mutableStateOf("") }
        var penanggungJawabValue by remember { mutableStateOf("") }
        var kontakValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nama Acara",
            placeholder = "Masukkan nama acara",
            value = namaAcaraValue,
            onValueChange = { namaAcaraValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Acara",
            placeholder = "Masukkan tempat acara",
            value = tempatAcaraValue,
            onValueChange = { tempatAcaraValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DropdownField(
                    label = "Hari",
                    value = hariValue,
                    onValueChange = { hariValue = it },
                    options = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"),
                    isError = false,
                    errorMessage = null,
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Acara",
                    value = tanggalValue,
                    onValueChange = { tanggalValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TimePickerField(
                    label = "Dimulai Dari Pukul (WIB)",
                    value = jamMulaiValue,
                    onValueChange = { jamMulaiValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                TimePickerField(
                    label = "Selesai Pada (WIB)",
                    value = jamSelesaiValue,
                    onValueChange = { jamSelesaiValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Penanggung Jawab",
            placeholder = "Masukkan nama penanggung jawab",
            value = penanggungJawabValue,
            onValueChange = { penanggungJawabValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kontak",
            placeholder = "Masukkan nomor kontak penanggungjawab",
            value = kontakValue,
            onValueChange = { kontakValue = it },
            isError = false,
            errorMessage = "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}