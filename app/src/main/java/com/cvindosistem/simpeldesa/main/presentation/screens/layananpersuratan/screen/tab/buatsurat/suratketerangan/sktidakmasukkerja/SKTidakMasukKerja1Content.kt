package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sktidakmasukkerja

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
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja.SKTidakMasukKerjaViewModel

@Composable
internal fun SKTidakMasukKerja1Content(
    viewModel: SKTidakMasukKerjaViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Barang Hilang"),
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
            InformasiPelapor(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPelapor(
    viewModel: SKTidakMasukKerjaViewModel
) {
    Column {
        SectionTitle("Informasi Pelapor")

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Tempat lahir",
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
                    errorMessage = viewModel.getFieldError("tanggal_lahir"),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelaminValue,
            onGenderSelected = viewModel::updateJenisKelamin,
            isError = viewModel.hasFieldError("jenis_kelamin"),
            errorMessage = viewModel.getFieldError("jenis_kelamin"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgamaId(it.id) }
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

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )
    }
}