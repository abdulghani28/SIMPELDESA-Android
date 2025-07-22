package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmcerai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.cerai.SPMCeraiViewModel

@Composable
internal fun SPMCerai1Content(
    viewModel: SPMCeraiViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Suami", "Informasi Istri", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiSuami(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiSuami(
    viewModel: SPMCeraiViewModel
) {
    Column {
        SectionTitle("Informasi Suami")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK) Suami",
            placeholder = "Masukkan NIK Suami",
            value = viewModel.nikSuamiValue,
            onValueChange = viewModel::updateNikSuami,
            isError = viewModel.hasFieldError("nik_suami"),
            errorMessage = viewModel.getFieldError("nik_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap Suami",
            placeholder = "Masukkan nama lengkap suami",
            value = viewModel.namaSuamiValue,
            onValueChange = viewModel::updateNamaSuami,
            isError = viewModel.hasFieldError("nama_suami"),
            errorMessage = viewModel.getFieldError("nama_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Suami",
                    placeholder = "Masukkan tempat lahir suami",
                    value = viewModel.tempatLahirSuamiValue,
                    onValueChange = viewModel::updateTempatLahirSuami,
                    isError = viewModel.hasFieldError("tempat_lahir_suami"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_suami")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Suami",
                    value = viewModel.tanggalLahirSuamiValue,
                    onValueChange = viewModel::updateTanggalLahirSuami,
                    isError = viewModel.hasFieldError("tanggal_lahir_suami"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_suami")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Suami",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIdSuamiValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgamaIdSuami(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_id_suami"),
            errorMessage = viewModel.getFieldError("agama_id_suami"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Suami",
            placeholder = "Masukkan pekerjaan suami",
            value = viewModel.pekerjaanSuamiValue,
            onValueChange = viewModel::updatePekerjaanSuami,
            isError = viewModel.hasFieldError("pekerjaan_suami"),
            errorMessage = viewModel.getFieldError("pekerjaan_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap Suami",
            placeholder = "Masukkan alamat lengkap suami",
            value = viewModel.alamatSuamiValue,
            onValueChange = viewModel::updateAlamatSuami,
            isError = viewModel.hasFieldError("alamat_suami"),
            errorMessage = viewModel.getFieldError("alamat_suami")
        )
    }
}