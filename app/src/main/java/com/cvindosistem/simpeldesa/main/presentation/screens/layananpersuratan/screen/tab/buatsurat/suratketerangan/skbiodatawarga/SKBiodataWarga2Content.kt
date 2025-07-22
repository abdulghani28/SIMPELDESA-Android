package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbiodatawarga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata.SKBiodataWargaViewModel

@Composable
internal fun SKBiodataWarga2Content(
    viewModel: SKBiodataWargaViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Diri", "Alamat & Status", "Perkawinan", "Data Orang Tua", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            AlamatStatusSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun AlamatStatusSection(
    viewModel: SKBiodataWargaViewModel
) {
    Column {
        SectionTitle("Alamat & Status")
        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Status dalam Keluarga",
            value = viewModel.statusKawinList.find { it.id == viewModel.statusValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.statusKawinList.find { it.nama == selectedNama }?.let {
                    viewModel.updateStatus(it.id)
                }
            },
            options = viewModel.statusKawinList.map { it.nama },
            isError = viewModel.hasFieldError("status"),
            errorMessage = viewModel.getFieldError("status"),
            onDropdownExpanded = viewModel::loadStatusKawin
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Hubungan dengan Kepala Keluarga",
            placeholder = "Masukkan hubungan dengan keluarga",
            value = viewModel.hubunganValue,
            onValueChange = viewModel::updateHubungan,
            isError = viewModel.hasFieldError("hubungan"),
            errorMessage = viewModel.getFieldError("hubungan"),
        )
    }
}