package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppernikahan

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
import com.cvindosistem.simpeldesa.core.components.InformationDivider
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPPernikahanViewModel

@Composable
internal fun SPPernikahan4Content(
    viewModel: SPPernikahanViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf("Informasi Calon Suami", "Informasi Orang Tua Calon Suami", "Informasi Calon Istri", "Informasi Orang Tua Calon Istri", "Informasi Rencana Pernikahan"),
                currentStep = 4
            )
        }

        item {
            InformasiOrangTuaCalonIstri(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiOrangTuaCalonIstri(
    viewModel: SPPernikahanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Orang Tua Calon Istri")

        Spacer(modifier = Modifier.height(16.dp))

        InformationDivider("Ayah")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikAyahIstriValue,
            onValueChange = viewModel::updateNikAyahIstri,
            isError = viewModel.hasFieldError("nik_ayah_istri"),
            errorMessage = viewModel.getFieldError("nik_ayah_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaAyahIstriValue,
            onValueChange = viewModel::updateNamaAyahIstri,
            isError = viewModel.hasFieldError("nama_ayah_istri"),
            errorMessage = viewModel.getFieldError("nama_ayah_istri")
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
                    value = viewModel.tempatLahirAyahIstriValue,
                    onValueChange = viewModel::updateTempatLahirAyahIstri,
                    isError = viewModel.hasFieldError("tempat_lahir_ayah_istri"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ayah_istri")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirAyahIstriValue,
                    onValueChange = viewModel::updateTanggalLahirAyahIstri,
                    isError = viewModel.hasFieldError("tanggal_lahir_ayah_istri"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ayah_istri")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanAyahIstriValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanAyahIstri,
            isError = viewModel.hasFieldError("kewarganegaraan_ayah_istri"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ayah_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaList.find { it.id == viewModel.agamaAyahIstriIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgamaAyahIstriId(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ayah_istri_id"),
            errorMessage = viewModel.getFieldError("agama_ayah_istri_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanAyahIstriValue,
            onValueChange = viewModel::updatePekerjaanAyahIstri,
            isError = viewModel.hasFieldError("pekerjaan_ayah_istri"),
            errorMessage = viewModel.getFieldError("pekerjaan_ayah_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatAyahIstriValue,
            onValueChange = viewModel::updateAlamatAyahIstri,
            isError = viewModel.hasFieldError("alamat_ayah_istri"),
            errorMessage = viewModel.getFieldError("alamat_ayah_istri")
        )

        Spacer(modifier = Modifier.height(24.dp))


        InformationDivider("Ibu")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikIbuIstriValue,
            onValueChange = viewModel::updateNikIbuIstri,
            isError = viewModel.hasFieldError("nik_ibu_istri"),
            errorMessage = viewModel.getFieldError("nik_ibu_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaIbuIstriValue,
            onValueChange = viewModel::updateNamaIbuIstri,
            isError = viewModel.hasFieldError("nama_ibu_istri"),
            errorMessage = viewModel.getFieldError("nama_ibu_istri")
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
                    value = viewModel.tempatLahirIbuIstriValue,
                    onValueChange = viewModel::updateTempatLahirIbuIstri,
                    isError = viewModel.hasFieldError("tempat_lahir_ibu_istri"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ibu_istri")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirIbuIstriValue,
                    onValueChange = viewModel::updateTanggalLahirIbuIstri,
                    isError = viewModel.hasFieldError("tanggal_lahir_ibu_istri"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ibu_istri")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanIbuIstriValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanIbuIstri,
            isError = viewModel.hasFieldError("kewarganegaraan_ibu_istri"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ibu_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaIbuIstriIdValue,
            onValueChange = viewModel::updateAgamaIbuIstriId,
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ibu_istri_id"),
            errorMessage = viewModel.getFieldError("agama_ibu_istri_id")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanIbuIstriValue,
            onValueChange = viewModel::updatePekerjaanIbuIstri,
            isError = viewModel.hasFieldError("pekerjaan_ibu_istri"),
            errorMessage = viewModel.getFieldError("pekerjaan_ibu_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatIbuIstriValue,
            onValueChange = viewModel::updateAlamatIbuIstri,
            isError = viewModel.hasFieldError("alamat_ibu_istri"),
            errorMessage = viewModel.getFieldError("alamat_ibu_istri")
        )
    }
}