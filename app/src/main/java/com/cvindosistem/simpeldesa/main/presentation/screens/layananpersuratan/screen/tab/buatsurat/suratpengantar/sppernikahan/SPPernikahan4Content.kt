package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppernikahan

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
import com.cvindosistem.simpeldesa.core.components.InformationDivider
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SPPernikahanViewModel

@Composable
internal fun SPPernikahan4Content(
    viewModel: SPPernikahanViewModel,
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf("Informasi Calon Suami", "Informasi Orang Tua Calon Suami", "Informasi Calon Istri", "Informasi Orang Tua Calon Istri", "Informasi Rencana Pernikahan"),
                currentStep = 4
            )
        }

        item {
            InformasiOrangTuaCalonIstri()
        }
    }
}

@Composable
private fun InformasiOrangTuaCalonIstri() {
    Column {
        SectionTitle("Informasi Orang Tua Calon Istri")

        Spacer(modifier = Modifier.height(16.dp))

        var ayahNikValue by remember { mutableStateOf("") }
        var ayahNamaValue by remember { mutableStateOf("") }
        var ayahTempatLahirValue by remember { mutableStateOf("") }
        var ayahTanggalLahirValue by remember { mutableStateOf("") }
        var ayahSelectedKewarganegaraan by remember { mutableStateOf("") }
        var ayahAgamaValue by remember { mutableStateOf("") }
        var ayahPekerjaanValue by remember { mutableStateOf("") }
        var ayahAlamatValue by remember { mutableStateOf("") }

        InformationDivider("Ayah")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = ayahNikValue,
            onValueChange = { ayahNikValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = ayahNamaValue,
            onValueChange = { ayahNamaValue = it },
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
                    label = "Tempat Lahir",
                    placeholder = "Tempat lahir",
                    value = ayahTempatLahirValue,
                    onValueChange = { ayahTempatLahirValue = it },
                    isError = false,
                    errorMessage = null
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = ayahTanggalLahirValue,
                    onValueChange = { ayahTanggalLahirValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = ayahSelectedKewarganegaraan,
            onSelectedKewarganegaraan = { ayahSelectedKewarganegaraan = it },
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = ayahAgamaValue,
            onValueChange = { ayahAgamaValue = it },
            options = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu"),
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = ayahPekerjaanValue,
            onValueChange = { ayahPekerjaanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = ayahAlamatValue,
            onValueChange = { ayahAlamatValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ibu Section
        var ibuNikValue by remember { mutableStateOf("") }
        var ibuNamaValue by remember { mutableStateOf("") }
        var ibuTempatLahirValue by remember { mutableStateOf("") }
        var ibuTanggalLahirValue by remember { mutableStateOf("") }
        var ibuSelectedKewarganegaraan by remember { mutableStateOf("") }
        var ibuAgamaValue by remember { mutableStateOf("") }
        var ibuPekerjaanValue by remember { mutableStateOf("") }
        var ibuAlamatValue by remember { mutableStateOf("") }

        InformationDivider("Ibu")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = ibuNikValue,
            onValueChange = { ibuNikValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = ibuNamaValue,
            onValueChange = { ibuNamaValue = it },
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
                    label = "Tempat Lahir",
                    placeholder = "Tempat lahir",
                    value = ibuTempatLahirValue,
                    onValueChange = { ibuTempatLahirValue = it },
                    isError = false,
                    errorMessage = null
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = ibuTanggalLahirValue,
                    onValueChange = { ibuTanggalLahirValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = ibuSelectedKewarganegaraan,
            onSelectedKewarganegaraan = { ibuSelectedKewarganegaraan = it },
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = ibuAgamaValue,
            onValueChange = { ibuAgamaValue = it },
            options = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu"),
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = ibuPekerjaanValue,
            onValueChange = { ibuPekerjaanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = ibuAlamatValue,
            onValueChange = { ibuAlamatValue = it },
            isError = false,
            errorMessage = null
        )
    }
}