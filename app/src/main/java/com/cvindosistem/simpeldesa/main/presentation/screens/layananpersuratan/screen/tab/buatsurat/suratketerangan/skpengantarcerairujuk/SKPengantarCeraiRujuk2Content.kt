package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skpengantarcerairujuk

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pengantarcerairujuk.SKPengantarCeraiRujukViewModel

@Composable
internal fun SKPengantarCeraiRujuk2Content(
    viewModel: SKPengantarCeraiRujukViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemohon", "Data Pasangan", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPasangan(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPasangan(
    viewModel: SKPengantarCeraiRujukViewModel
) {
    Column {
        SectionTitle("Data Diri Pasangan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Pasangan",
            placeholder = "Masukkan NIK pasangan",
            value = viewModel.nikPasanganValue,
            onValueChange = viewModel::updateNikPasangan,
            isError = viewModel.hasFieldError("nik_pasangan"),
            errorMessage = viewModel.getFieldError("nik_pasangan"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Pasangan",
            placeholder = "Masukkan nama pasangan",
            value = viewModel.namaPasanganValue,
            onValueChange = viewModel::updateNamaPasangan,
            isError = viewModel.hasFieldError("nama_pasangan"),
            errorMessage = viewModel.getFieldError("nama_pasangan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirPasanganValue,
                    onValueChange = viewModel::updateTempatLahirPasangan,
                    isError = viewModel.hasFieldError("tempat_lahir_pasangan"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_pasangan")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirPasanganValue,
                    onValueChange = viewModel::updateTanggalLahirPasangan,
                    isError = viewModel.hasFieldError("tanggal_lahir_pasangan"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_pasangan")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIdPasanganValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgamaIdPasangan(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_id_pasangan"),
            errorMessage = viewModel.getFieldError("agama_id_pasangan"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan pasangan",
            value = viewModel.pekerjaanPasanganValue,
            onValueChange = viewModel::updatePekerjaanPasangan,
            isError = viewModel.hasFieldError("pekerjaan_pasangan"),
            errorMessage = viewModel.getFieldError("pekerjaan_pasangan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat",
            placeholder = "Masukkan alamat pasangan",
            value = viewModel.alamatPasanganValue,
            onValueChange = viewModel::updateAlamatPasangan,
            isError = viewModel.hasFieldError("alamat_pasangan"),
            errorMessage = viewModel.getFieldError("alamat_pasangan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanPasanganValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanPasangan,
            isError = viewModel.hasFieldError("kewarganegaraan_pasangan"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_pasangan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("Data Ayah Pasangan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ayah Pasangan",
            placeholder = "Masukkan nama ayah pasangan",
            value = viewModel.namaAyahPasanganValue,
            onValueChange = viewModel::updateNamaAyahPasangan,
            isError = viewModel.hasFieldError("nama_ayah_pasangan"),
            errorMessage = viewModel.getFieldError("nama_ayah_pasangan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Ayah Pasangan",
            placeholder = "Masukkan NIK ayah pasangan",
            value = viewModel.nikAyahPasanganValue,
            onValueChange = viewModel::updateNikAyahPasangan,
            isError = viewModel.hasFieldError("nik_ayah_pasangan"),
            errorMessage = viewModel.getFieldError("nik_ayah_pasangan"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}
