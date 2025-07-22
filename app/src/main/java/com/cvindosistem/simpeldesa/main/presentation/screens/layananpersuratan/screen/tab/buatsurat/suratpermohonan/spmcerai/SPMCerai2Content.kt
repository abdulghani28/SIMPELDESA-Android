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
internal fun SPMCerai2Content(
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
            InformasiIstri(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiIstri(
    viewModel: SPMCeraiViewModel
) {
    Column {
        SectionTitle("Informasi Istri")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK) Istri",
            placeholder = "Masukkan NIK Istri",
            value = viewModel.nikIstriValue,
            onValueChange = viewModel::updateNikIstri,
            isError = viewModel.hasFieldError("nik_istri"),
            errorMessage = viewModel.getFieldError("nik_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap Istri",
            placeholder = "Masukkan nama lengkap istri",
            value = viewModel.namaIstriValue,
            onValueChange = viewModel::updateNamaIstri,
            isError = viewModel.hasFieldError("nama_istri"),
            errorMessage = viewModel.getFieldError("nama_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Istri",
                    placeholder = "Masukkan tempat lahir istri",
                    value = viewModel.tempatLahirIstriValue,
                    onValueChange = viewModel::updateTempatLahirIstri,
                    isError = viewModel.hasFieldError("tempat_lahir_istri"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_istri")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Istri",
                    value = viewModel.tanggalLahirIstriValue,
                    onValueChange = viewModel::updateTanggalLahirIstri,
                    isError = viewModel.hasFieldError("tanggal_lahir_istri"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_istri")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Istri",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIdIstriValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgamaIdIstri(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_id_istri"),
            errorMessage = viewModel.getFieldError("agama_id_istri"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Istri",
            placeholder = "Masukkan pekerjaan istri",
            value = viewModel.pekerjaanIstriValue,
            onValueChange = viewModel::updatePekerjaanIstri,
            isError = viewModel.hasFieldError("pekerjaan_istri"),
            errorMessage = viewModel.getFieldError("pekerjaan_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap Istri",
            placeholder = "Masukkan alamat lengkap istri",
            value = viewModel.alamatIstriValue,
            onValueChange = viewModel::updateAlamatIstri,
            isError = viewModel.hasFieldError("alamat_istri"),
            errorMessage = viewModel.getFieldError("alamat_istri")
        )
    }
}