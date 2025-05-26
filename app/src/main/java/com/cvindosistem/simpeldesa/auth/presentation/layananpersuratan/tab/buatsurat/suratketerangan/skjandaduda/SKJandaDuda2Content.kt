package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skjandaduda

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.components.DasarPengajuanSelection
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.components.PenyebabStatusSelection
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator

@Composable
internal fun SKJandaDuda2Content(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Janda/Duda", "Informasi Janda/Duda", "Informasi Pelengkap"),
                currentStep = 2
            )
        }

        item {
            InformasiJandaDuda()
        }
    }
}

@Composable
private fun InformasiJandaDuda() {
    Column {
        SectionTitle("Informasi Janda/Duda")

        Spacer(modifier = Modifier.height(16.dp))

        var dasarPengajuanValue by remember { mutableStateOf("") }
        var noAktaValue by remember { mutableStateOf("") }
        var penyebabStatusValue by remember { mutableStateOf("") }

        DasarPengajuanSelection(
            selectedDasarPengajuan = dasarPengajuanValue,
            onDasarPengajuanSelected = { dasarPengajuanValue = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Akta atau Nomor Surat Keterangan",
            placeholder = "Nomor Akta atau Nomor Surat Keterangan",
            value = noAktaValue,
            onValueChange = { noAktaValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = dasarPengajuanValue == "Akta Cerai",
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            PenyebabStatusSelection(
                selectedPenyebabStatus = penyebabStatusValue,
                onPenyebabStatusSelected = { penyebabStatusValue = it }
            )
        }
    }
}