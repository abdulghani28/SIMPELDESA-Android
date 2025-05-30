package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skdomisiliperusahaan.pendatang

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DomisiliPendatang3Content(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(
                onPreviewClick = { },
                onBackClick = onBackClick,
                onSubmitClick = onSubmit,
                submitText = "Ajukan Surat"
            )
        }
    ) {
        FormSectionList(
            modifier = modifier,
            background = MaterialTheme.colorScheme.background
        ) {
            item {
                StepIndicator(
                    steps = listOf("Informasi Pelapor", "Informasi Perusahaan", "Informasi Pelengkap"),
                    currentStep = 3
                )
            }

            item {
                InformasiPelengkapDomisili()
            }
        }
    }
}

@Composable
private fun InformasiPelengkapDomisili() {
    Column {
        SectionTitle("Informasi Pelengkap")

        Spacer(modifier = Modifier.height(16.dp))

        var keperluanValue by remember { mutableStateOf("") }

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = keperluanValue,
            onValueChange = { keperluanValue = it },
            isError = false,
            errorMessage = null
        )
    }
}