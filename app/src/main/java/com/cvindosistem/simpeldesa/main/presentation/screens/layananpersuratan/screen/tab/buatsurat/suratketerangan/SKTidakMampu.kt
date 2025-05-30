package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox

@Composable
fun SKTidakMampu(
    navController: NavController
) {

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "SK Tidak Mampu",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
            }
        },
        bottomBar = {
            AppBottomBar(
                onPreviewClick = { },
                onSubmitClick = { }
            )
        }
    ) { paddingValues ->
        SKTidakMampuContent(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
private fun SKTidakMampuContent(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            UseMyDataCheckbox()
        }

        item {
            InformasiPelapor()
        }
    }
}

@Composable
private fun InformasiPelapor() {
    Column {
        SectionTitle("Informasi Pelapor")

        Spacer(modifier = Modifier.height(16.dp))

        var nikValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var tempatLahirValue by remember { mutableStateOf("") }
        var tanggalLahirValue by remember { mutableStateOf("") }
        var selectedGender by remember { mutableStateOf("") }
        var agamaValue by remember { mutableStateOf("") }
        var pekerjaanValue by remember { mutableStateOf("") }
        var alamatValue by remember { mutableStateOf("") }
        var keperluan by remember { mutableStateOf("") }

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

        GenderSelection(
            selectedGender = selectedGender,
            onGenderSelected = { selectedGender = it },
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
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = alamatValue,
            onValueChange = { alamatValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = keperluan,
            onValueChange = { keperluan = it },
            isError = false,
            errorMessage = null
        )
    }
}