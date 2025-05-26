package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab.buatsurat.suratketerangan.skbedaidentitas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
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
import com.cvindosistem.simpeldesa.core.components.LabelFieldText
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
internal fun SKBedaIdentitas2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Identitas 1", "Informasi Identitas 2", "Informasi Pelengkap"),
                currentStep = 2
            )
        }

        item {
            InformasiPerbedaanIdentitas()
        }
    }
}

@Composable
private fun InformasiPerbedaanIdentitas() {
    Column {

        var tercantumDalamValue by remember { mutableStateOf("") }
        var nomorValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var tempatLahirValue by remember { mutableStateOf("") }
        var tanggalLahirValue by remember { mutableStateOf("") }
        var alamatValue by remember { mutableStateOf("") }

        LabelFieldText("Identitas 2")

        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))

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