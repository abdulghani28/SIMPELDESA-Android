package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbedaidentitas

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
import com.cvindosistem.simpeldesa.core.components.InformationDivider
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox

@Composable
internal fun SKBedaIdentitas1Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Identitas 1", "Informasi Identitas 2", "Informasi Pelengkap"),
                currentStep = 1
            )
        }

        item {
            UseMyDataCheckbox()
        }

        item {
            InformasiPerbedaanIdentitas()
        }
    }
}

@Composable
private fun InformasiPerbedaanIdentitas() {
    Column {
        SectionTitle("Informasi Perbedaan Identitas")

        Spacer(modifier = Modifier.height(16.dp))

        var perbedaanDataValue by remember { mutableStateOf("") }
        var tercantumDalamValue by remember { mutableStateOf("") }
        var nomorValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var tempatLahirValue by remember { mutableStateOf("") }
        var tanggalLahirValue by remember { mutableStateOf("") }
        var alamatValue by remember { mutableStateOf("") }

        DropdownField(
            label = "Perbedaan Data yang Akan Dilaporkan",
            value = perbedaanDataValue,
            onValueChange = {perbedaanDataValue = it },
            options = listOf("NIK", "Nama", "Tempat Lahir", "Tanggal Lahir", "Alamat"),
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        InformationDivider("Identitas 1 (identitas sebenarnya)")

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Tercantum Dalam",
            value = tercantumDalamValue,
            onValueChange = { tercantumDalamValue = it },
            options = listOf("KTP", "KK", "Ijazah", "Akta Kelahiran", "Buku Nikah"),
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor",
            placeholder = "XXXX XXXX XXXX",
            value = nomorValue,
            onValueChange = { nomorValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama",
            placeholder = "Masukkan nama lengkap",
            value = namaValue,
            onValueChange = { namaValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Lahir",
            placeholder = "Masukkan tempat lahir",
            value = tempatLahirValue,
            onValueChange = { tempatLahirValue = it },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Lahir",
            value = tanggalLahirValue,
            onValueChange = { tanggalLahirValue = it },
            isError = false,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = alamatValue,
            onValueChange = { alamatValue = it },
            isError = false,
            errorMessage = null
        )
    }
}