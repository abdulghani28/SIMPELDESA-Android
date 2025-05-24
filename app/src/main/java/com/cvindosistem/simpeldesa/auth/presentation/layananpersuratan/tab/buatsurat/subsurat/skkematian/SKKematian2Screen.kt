package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skkematian

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
fun SKKematian2Screen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "SK Kematian",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
            }
        },
        bottomBar = {
            AppBottomBar(
                onPreviewClick = { },
                onBackClick = { },
                onContinueClick = { }
            )
        }
    ) { paddingValues ->
        SKKematian2Content(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
private fun SKKematian2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Mendiang", "Informasi Pelengkap"),
                currentStep = 2
            )
        }

        item {
            InformasiMendiang()
        }
    }
}

@Composable
private fun InformasiMendiang() {
    Column {
        SectionTitle("Informasi Mendiang")

        Spacer(modifier = Modifier.height(16.dp))

        var nikValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var tempatMeninggalValue by remember { mutableStateOf("") }
        var tanggalMeninggalValue by remember { mutableStateOf("") }
        var selectedGender by remember { mutableStateOf("") }
        var hariMeninggalValue by remember { mutableStateOf("") }
        var sebabMeninggalValue by remember { mutableStateOf("") }
        var alamatValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan nama lengkap",
            value = nikValue,
            onValueChange = { nikValue = it },
            isError = false,
            errorMessage = null
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

        GenderSelection(
            selectedGender = selectedGender,
            onGenderSelected = { selectedGender = it }
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DropdownField(
                    label = "Hari Meninggal",
                    value = hariMeninggalValue,
                    onValueChange = { hariMeninggalValue = it },
                    options = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Meninggal",
                    value = tanggalMeninggalValue,
                    onValueChange = { tanggalMeninggalValue = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Meninggal",
            placeholder = "Tempat meninggal",
            value = tempatMeninggalValue,
            onValueChange = { tempatMeninggalValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Sebab Meninggal",
            placeholder = "Sebab meninggal",
            value = sebabMeninggalValue,
            onValueChange = { sebabMeninggalValue = it },
            isError = false,
            errorMessage = null
        )
    }
}