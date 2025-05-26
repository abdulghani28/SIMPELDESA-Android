package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratpengantar.sppernikahan

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
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox

@Composable
internal fun SPPernikahan1Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf("Informasi Calon Suami", "Informasi Orang Tua Calon Suami", "Informasi Calon Istri", "Informasi Orang Tua Calon Istri", "Informasi Rencana Pernikahan"),
                currentStep = 1
            )
        }

        item {
            UseMyDataCheckbox()
        }

        item {
            InformasiCalonSuami()
        }

        item {
            InformasiStatusPerkawinan()
        }
    }
}

@Composable
private fun InformasiCalonSuami() {
    Column {
        SectionTitle("Informasi Calon Suami")

        Spacer(modifier = Modifier.height(16.dp))

        var nikValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var tempatLahirValue by remember { mutableStateOf("") }
        var tanggalLahirValue by remember { mutableStateOf("") }
        var selectedKewarganegaraan by remember { mutableStateOf("") }
        var agamaValue by remember { mutableStateOf("") }
        var pekerjaanValue by remember { mutableStateOf("") }
        var alamatValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = nikValue,
            onValueChange = { nikValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = namaValue,
            onValueChange = { namaValue = it },
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
                    value = tempatLahirValue,
                    onValueChange = { tempatLahirValue = it },
                    isError = false,
                    errorMessage = null
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = tanggalLahirValue,
                    onValueChange = { tanggalLahirValue = it },
                    isError = false,
                    errorMessage = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = selectedKewarganegaraan,
            onSelectedKewarganegaraan = { selectedKewarganegaraan = it },
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = agamaValue,
            onValueChange = { agamaValue = it },
            options = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu"),
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = pekerjaanValue,
            onValueChange = { pekerjaanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = alamatValue,
            onValueChange = { alamatValue = it },
            isError = false,
            errorMessage = null
        )
    }
}

@Composable
private fun InformasiStatusPerkawinan() {
    Column {
        SectionTitle("Informasi Status Perkawinan")

        Spacer(modifier = Modifier.height(16.dp))

        var statusValue by remember { mutableStateOf("") }
        var jumlahIstriValue by remember { mutableStateOf("") }
        var namaIstriSebelumnyaValue by remember { mutableStateOf("") }

        DropdownField(
            label = "Status",
            value = statusValue,
            onValueChange = { statusValue = it },
            options = listOf("Duda", "Lajang"),
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jumlah Istri",
            placeholder = "Masukkan jumlah istri",
            value = jumlahIstriValue,
            onValueChange = { jumlahIstriValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Istri Sebelumnya",
            placeholder = "Masukkan nama istri sebelumnya",
            value = namaIstriSebelumnyaValue,
            onValueChange = { namaIstriSebelumnyaValue = it },
            isError = false,
            errorMessage = null,
        )
    }
}
