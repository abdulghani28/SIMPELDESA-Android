package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skjandaduda

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.DasarPengajuanSelection
import com.cvindosistem.simpeldesa.core.components.PenyebabStatusSelection
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jandaduda.SKJandaDudaViewModel

@Composable
internal fun SKJandaDuda2Content(
    viewModel: SKJandaDudaViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Janda/Duda", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiJandaDuda(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiJandaDuda(
    viewModel: SKJandaDudaViewModel
) {
    Column {
        SectionTitle("Informasi Janda/Duda")

        Spacer(modifier = Modifier.height(16.dp))

        DasarPengajuanSelection(
            selectedDasarPengajuan = viewModel.dasarPengajuanValue,
            onDasarPengajuanSelected = viewModel::updateDasarPengajuan,
            isError = viewModel.hasFieldError("dasar_pengajuan"),
            errorMessage = viewModel.getFieldError("dasar_pengajuan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Akta atau Nomor Surat Keterangan",
            placeholder = "Nomor Akta atau Nomor Surat Keterangan",
            value = viewModel.nomorPengajuanValue,
            onValueChange = viewModel::updateNomorPengajuan,
            isError = viewModel.hasFieldError("nomor_pengajuan"),
            errorMessage = viewModel.getFieldError("nomor_pengajuan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = viewModel.dasarPengajuanValue == "Akta Cerai",
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            val isAktaCerai = viewModel.dasarPengajuanValue == "Akta Cerai"
            val isError = if (isAktaCerai) viewModel.hasFieldError("penyebab") else false
            val errorMessage = if (isAktaCerai) viewModel.getFieldError("penyebab") else null

            PenyebabStatusSelection(
                selectedPenyebabStatus = viewModel.penyebabValue,
                onPenyebabStatusSelected = viewModel::updatePenyebab,
                isError = isError,
                errorMessage = errorMessage
            )
        }
    }
}
