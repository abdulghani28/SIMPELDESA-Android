package com.cvindosistem.simpeldesa.main.presentation.screens.layanankesehatan.submenu.tambahdata

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle

@Composable
fun TambahDataWUSPUSScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Tambah Data WUS/PUS",
                    showBackButton = true,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        },
        bottomBar = {
            AppBottomBar(
                onSubmitClick = { },
                submitText = "Tambahkan Data"
            )
        }
    ) { paddingValues ->
        FormSectionList(
            modifier = Modifier.padding(paddingValues),
            background = MaterialTheme.colorScheme.background
        ) {
            item {
                InformasiWUSPUS()
            }
        }
    }
}

@Composable
private fun InformasiWUSPUS() {
    Column {
        SectionTitle("Informasi WUS/PUS")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Istri",
            placeholder = "Masukkan nama istri",
            value = "",
            onValueChange = { },
            isError = false,
            errorMessage = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = "",
            onValueChange = { },
            isError = false,
            errorMessage = "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                    value = "",
                    onValueChange = { },
                    isError = false,
                    errorMessage = ""
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = "",
                    onValueChange = { },
                    isError = false,
                    errorMessage = ""
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = "",
            onValueChange = { },
            isError = false,
            errorMessage = ""
        )
    }
}
