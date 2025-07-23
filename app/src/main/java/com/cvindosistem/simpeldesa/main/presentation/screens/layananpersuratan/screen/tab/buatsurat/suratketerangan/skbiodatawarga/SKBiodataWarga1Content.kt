package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbiodatawarga

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
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata.SKBiodataWargaViewModel

@Composable
internal fun SKBiodataWarga1Content(
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
            UseMyDataCheckbox(
                checked = viewModel.useMyDataChecked,
                onCheckedChange = viewModel::updateUseMyData,
                isLoading = viewModel.isLoadingUserData
            )
        }

        item {
            DataDiriPemohon(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun DataDiriPemohon(
    viewModel: SKBiodataWargaViewModel
) {
    Column {
        SectionTitle("Data Diri Pemohon")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikValue,
            onValueChange = viewModel::updateNik,
            isError = viewModel.hasFieldError("nik"),
            errorMessage = viewModel.getFieldError("nik"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaValue,
            onValueChange = viewModel::updateNama,
            isError = viewModel.hasFieldError("nama"),
            errorMessage = viewModel.getFieldError("nama")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirValue,
                    onValueChange = viewModel::updateTempatLahir,
                    isError = viewModel.hasFieldError("tempat_lahir"),
                    errorMessage = viewModel.getFieldError("tempat_lahir")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirValue,
                    onValueChange = viewModel::updateTanggalLahir,
                    isError = viewModel.hasFieldError("tanggal_lahir"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Golongan Darah",
            placeholder = "Masukkan golongan darah",
            value = viewModel.golonganDarahValue,
            onValueChange = viewModel::updateGolonganDarah,
            isError = viewModel.hasFieldError("golongan_darah"),
            errorMessage = viewModel.getFieldError("golongan_darah")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_id"),
            errorMessage = viewModel.getFieldError("agama_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanValue,
            onValueChange = viewModel::updatePekerjaan,
            isError = viewModel.hasFieldError("pekerjaan"),
            errorMessage = viewModel.getFieldError("pekerjaan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Pendidikan",
            value = viewModel.pendidikanList.find { it.id == viewModel.pendidikanIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.pendidikanList.find { it.nama == selectedNama }?.let {
                    viewModel.updatePendidikanId(it.id)
                }
            },
            options = viewModel.pendidikanList.map { it.nama },
            isError = viewModel.hasFieldError("pendidikan_id"),
            errorMessage = viewModel.getFieldError("pendidikan_id"),
            onDropdownExpanded = viewModel::loadPendidikan
        )

        Spacer(modifier = Modifier.height(16.dp))

//        DropdownField(
//            label = "Disabilitas",
//            value = viewModel.disabilitasList.find { it.id == viewModel.disabilitasIdValue }?.nama.orEmpty(),
//            onValueChange = { selectedNama ->
//                viewModel.disabilitasList.find { it.nama == selectedNama }?.let {
//                    viewModel.updateDisabilitasId(it.id)
//                }
//            },
//            options = viewModel.disabilitasList.map { it.nama },
//            isError = viewModel.hasFieldError("disabilitas_id"),
//            errorMessage = viewModel.getFieldError("disabilitas_id"),
//            onDropdownExpanded = viewModel::loadDisabilitas
//        )
    }
}
