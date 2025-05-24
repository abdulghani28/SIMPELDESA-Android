package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skkematian

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox

@Composable
fun SKKematian1Screen(
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
                onContinueClick = { }
            )
        }
    ) { paddingValues ->
        SKKematian1Content(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
private fun SKKematian1Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Mendiang", "Informasi Pelengkap"),
                currentStep = 1
            )
        }

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

        var namaValue by remember { mutableStateOf("") }
        var agamaValue by remember { mutableStateOf("") }
        var alamatValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = namaValue,
            onValueChange = { namaValue = it },
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

        DropdownField(
            label = "Hubungan dengan Mendiang",
            value = agamaValue,
            onValueChange = { agamaValue = it },
            options = listOf("Suami", "Istri", "Saudara Kandung", "Saudara Tiri", "Ayah", "Ibu")
        )
    }
}
