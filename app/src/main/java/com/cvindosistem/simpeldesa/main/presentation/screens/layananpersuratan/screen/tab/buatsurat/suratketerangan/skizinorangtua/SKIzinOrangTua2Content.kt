package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skizinorangtua

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua.SKIzinOrangTuaViewModel

@Composable
internal fun SKIzinOrangTua2Content(
    viewModel: SKIzinOrangTuaViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Pemberi Izin", "Yang Diberi Izin", "Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiYangDiberiIzinSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiYangDiberiIzinSection(
    viewModel: SKIzinOrangTuaViewModel
) {
    Column {
        SectionTitle("Informasi yang Diberi Izin")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Yang Diberi Izin",
            placeholder = "Contoh: Orang Tua/Suami/Istri/Keluarga.",
            value = viewModel.diberiIzinValue,
            onValueChange = viewModel::updateDiberiIzin,
            isError = viewModel.hasFieldError("diberi_izin"),
            errorMessage = viewModel.getFieldError("diberi_izin")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK",
            placeholder = "Masukkan NIK",
            value = viewModel.nik2Value,
            onValueChange = viewModel::updateNik2,
            isError = viewModel.hasFieldError("nik2"),
            errorMessage = viewModel.getFieldError("nik2"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.nama2Value,
            onValueChange = viewModel::updateNama2,
            isError = viewModel.hasFieldError("nama2"),
            errorMessage = viewModel.getFieldError("nama2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahir2Value,
                    onValueChange = viewModel::updateTempatLahir2,
                    isError = viewModel.hasFieldError("tempat_lahir2"),
                    errorMessage = viewModel.getFieldError("tempat_lahir2")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahir2Value,
                    onValueChange = viewModel::updateTanggalLahir2,
                    isError = viewModel.hasFieldError("tanggal_lahir2"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir2")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaList.find { it.id == viewModel.agama2IdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgama2Id(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama2_id"),
            errorMessage = viewModel.getFieldError("agama2_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaan2Value,
            onValueChange = viewModel::updatePekerjaan2,
            isError = viewModel.hasFieldError("pekerjaan2"),
            errorMessage = viewModel.getFieldError("pekerjaan2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Status Pekerjaan",
            value = viewModel.statusPekerjaanValue,
            onValueChange = viewModel::updateStatusPekerjaan,
            options = listOf("Aktif", "Pensiun", "Tidak Bekerja", "Pelajar/Mahasiswa"), // contoh
            isError = viewModel.hasFieldError("status_pekerjaan"),
            errorMessage = viewModel.getFieldError("status_pekerjaan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamat2Value,
            onValueChange = viewModel::updateAlamat2,
            isError = viewModel.hasFieldError("alamat2"),
            errorMessage = viewModel.getFieldError("alamat2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraan2Value,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraan2,
            isError = viewModel.hasFieldError("kewarganegaraan2"),
            errorMessage = viewModel.getFieldError("kewarganegaraan2")
        )
    }
}
