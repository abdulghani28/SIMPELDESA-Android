package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha.warga

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsahaWargaDesa2Content(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(
                onPreviewClick = { },
                onBackClick = onBackClick,
                onContinueClick = onContinueClick
            )
        }
    ) {
        FormSectionList(
            modifier = modifier,
            background = MaterialTheme.colorScheme.background
        ) {
            item {
                StepIndicator(
                    steps = listOf("Informasi Pelapor", "Informasi Usaha", "Informasi Pelengkap"),
                    currentStep = 2
                )
            }

            item {
                InformasiUsaha()
            }
        }
    }
}

@Composable
private fun InformasiUsaha() {
    Column {
        SectionTitle("Informasi Usaha")

        Spacer(modifier = Modifier.height(16.dp))

        var namaUsahaValue by remember { mutableStateOf("") }
        var jenisUsahaValue by remember { mutableStateOf("") }
        var bidangUsahaValue by remember { mutableStateOf("") }
        var npwpValue by remember { mutableStateOf("") }
        var alamatUsahaValue by remember { mutableStateOf("") }

        AppTextField(
            label = "Nama Usaha",
            placeholder = "Masukkan nama usaha",
            value = namaUsahaValue,
            onValueChange = { namaUsahaValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jenis Usaha",
            placeholder = "Masukkan jenis usaha",
            value = jenisUsahaValue,
            onValueChange = { jenisUsahaValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Bidang Usaha",
            placeholder = "Masukkan bidang usaha",
            value = bidangUsahaValue,
            onValueChange = { bidangUsahaValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Pajak Wajib Pajak (NPWP)",
            placeholder = "Masukkan NPWP",
            value = npwpValue,
            onValueChange = { npwpValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = alamatUsahaValue,
            onValueChange = { alamatUsahaValue = it },
            isError = false,
            errorMessage = null
        )
    }
}