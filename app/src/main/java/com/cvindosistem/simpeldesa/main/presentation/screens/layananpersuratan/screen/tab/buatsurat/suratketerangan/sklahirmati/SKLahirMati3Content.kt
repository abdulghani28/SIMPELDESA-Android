package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sklahirmati

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati.SKLahirMatiViewModel

@Composable
internal fun SKLahirMati3Content(
    viewModel: SKLahirMatiViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Ibu", "Keterangan Lahir Mati"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            KeteranganLahirMati(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun KeteranganLahirMati(
    viewModel: SKLahirMatiViewModel
) {
    Column {
        SectionTitle("Keterangan Lahir Mati")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Lama Dikandung",
            placeholder = "Contoh: 9 bulan",
            value = viewModel.lamaDikandungValue,
            onValueChange = viewModel::updateLamaDikandung,
            isError = viewModel.hasFieldError("lama_dikandung"),
            errorMessage = viewModel.getFieldError("lama_dikandung")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Meninggal",
                    value = viewModel.tanggalMeninggalValue,
                    onValueChange = viewModel::updateTanggalMeninggal,
                    isError = viewModel.hasFieldError("tanggal_meninggal"),
                    errorMessage = viewModel.getFieldError("tanggal_meninggal")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Meninggal",
                    placeholder = "Masukkan tempat meninggal",
                    value = viewModel.tempatMeninggalValue,
                    onValueChange = viewModel::updateTempatMeninggal,
                    isError = viewModel.hasFieldError("tempat_meninggal"),
                    errorMessage = viewModel.getFieldError("tempat_meninggal")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Hubungan Pelapor dengan Anak",
            value = viewModel.hubunganList.find { it.id == viewModel.hubunganIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.hubunganList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateHubunganId(it.id) }
            },
            options = viewModel.hubunganList.map { it.nama },
            isError = viewModel.hasFieldError("hubungan_id"),
            errorMessage = viewModel.getFieldError("hubungan_id"),
            onDropdownExpanded = viewModel::loadHubungan
        )
    }
}
