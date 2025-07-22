package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sknikahwarganonmuslim

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
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel

@Composable
internal fun SKNikahWargaNonMuslim6Content(
    viewModel: SKNikahWargaNonMuslimViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Suami & Istri", "Alamat & Anak", "Orang Tua Suami", "Orang Tua Istri", "Saksi Pernikahan", "Pernikahan & Pemuka Agama", "Legalitas & Putusan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            PernikahanPemukaAgamaSection(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun PernikahanPemukaAgamaSection(
    viewModel: SKNikahWargaNonMuslimViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Data Pernikahan & Pemuka Agama")
        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Pernikahan",
            value = viewModel.agamaList.find { it.id == viewModel.agamaPernikahanId }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaPernikahanId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_pernikahan_id"),
            errorMessage = viewModel.getFieldError("agama_pernikahan_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Pernikahan",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiPernikahan,
            onValueChange = viewModel::updateNamaOrganisasiPernikahan,
            isError = viewModel.hasFieldError("nama_organisasi_pernikahan"),
            errorMessage = viewModel.getFieldError("nama_organisasi_pernikahan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Pemuka Agama",
            placeholder = "Masukkan nama pemuka agama",
            value = viewModel.namaPemukaAgama,
            onValueChange = viewModel::updateNamaPemukaAgama,
            isError = viewModel.hasFieldError("nama_pemuka_agama"),
            errorMessage = viewModel.getFieldError("nama_pemuka_agama")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Pemberkatan Pernikahan",
            value = viewModel.tanggalPemberkatanPernikahan,
            onValueChange = viewModel::updateTanggalPemberkatanPernikahan,
            isError = viewModel.hasFieldError("tanggal_pemberkatan_pernikahan"),
            errorMessage = viewModel.getFieldError("tanggal_pemberkatan_pernikahan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Melapor Pernikahan",
            value = viewModel.tanggalMelaporPernikahan,
            onValueChange = viewModel::updateTanggalMelaporPernikahan,
            isError = viewModel.hasFieldError("tanggal_melapor_pernikahan"),
            errorMessage = viewModel.getFieldError("tanggal_melapor_pernikahan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Badan Peradilan Pernikahan",
            placeholder = "Masukkan badan peradilan",
            value = viewModel.badanPeradilanPernikahan,
            onValueChange = viewModel::updateBadanPeradilanPernikahan,
            isError = viewModel.hasFieldError("badan_peradilan_pernikahan"),
            errorMessage = viewModel.getFieldError("badan_peradilan_pernikahan")
        )
    }
}