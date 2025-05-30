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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.InformationDivider
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SPPernikahanViewModel

@Composable
internal fun SPPernikahan2Content(
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
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiOrangTuaCalonSuami(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiOrangTuaCalonSuami(
    viewModel: SPPernikahanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Orang Tua Calon Suami")

        Spacer(modifier = Modifier.height(16.dp))

        InformationDivider("Ayah")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikAyahSuamiValue,
            onValueChange = viewModel::updateNikAyahSuami,
            isError = viewModel.hasFieldError("nik_ayah_suami"),
            errorMessage = viewModel.getFieldError("nik_ayah_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaAyahSuamiValue,
            onValueChange = viewModel::updateNamaAyahSuami,
            isError = viewModel.hasFieldError("nama_ayah_suami"),
            errorMessage = viewModel.getFieldError("nama_ayah_suami")
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
                    value = viewModel.tempatLahirAyahSuamiValue,
                    onValueChange = viewModel::updateTempatLahirAyahSuami,
                    isError = viewModel.hasFieldError("tempat_lahir_ayah_suami"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ayah_suami")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirAyahSuamiValue,
                    onValueChange = viewModel::updateTanggalLahirAyahSuami,
                    isError = viewModel.hasFieldError("tanggal_lahir_ayah_suami"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ayah_suami")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanAyahSuamiValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanAyahSuami,
            isError = viewModel.hasFieldError("kewarganegaraan_ayah_suami"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ayah_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaAyahSuamiIdValue,
            onValueChange = viewModel::updateAgamaAyahSuamiId,
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ayah_suami_id"),
            errorMessage = viewModel.getFieldError("agama_ayah_suami_id")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanAyahSuamiValue,
            onValueChange = viewModel::updatePekerjaanAyahSuami,
            isError = viewModel.hasFieldError("pekerjaan_ayah_suami"),
            errorMessage = viewModel.getFieldError("pekerjaan_ayah_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatAyahSuamiValue,
            onValueChange = viewModel::updateAlamatAyahSuami,
            isError = viewModel.hasFieldError("alamat_ayah_suami"),
            errorMessage = viewModel.getFieldError("alamat_ayah_suami")
        )

        Spacer(modifier = Modifier.height(24.dp))

        InformationDivider("Ibu")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikIbuSuamiValue,
            onValueChange = viewModel::updateNikIbuSuami,
            isError = viewModel.hasFieldError("nik_ibu_suami"),
            errorMessage = viewModel.getFieldError("nik_ibu_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaIbuSuamiValue,
            onValueChange = viewModel::updateNamaIbuSuami,
            isError = viewModel.hasFieldError("nama_ibu_suami"),
            errorMessage = viewModel.getFieldError("nama_ibu_suami")
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
                    value = viewModel.tempatLahirIbuSuamiValue,
                    onValueChange = viewModel::updateTempatLahirIbuSuami,
                    isError = viewModel.hasFieldError("tempat_lahir_ibu_suami"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ibu_suami")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirIbuSuamiValue,
                    onValueChange = viewModel::updateTanggalLahirIbuSuami,
                    isError = viewModel.hasFieldError("tanggal_lahir_ibu_suami"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ibu_suami")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanIbuSuamiValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanIbuSuami,
            isError = viewModel.hasFieldError("kewarganegaraan_ibu_suami"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ibu_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaIbuSuamiIdValue,
            onValueChange = viewModel::updateAgamaIbuSuamiId,
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ibu_suami_id"),
            errorMessage = viewModel.getFieldError("agama_ibu_suami_id")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanIbuSuamiValue,
            onValueChange = viewModel::updatePekerjaanIbuSuami,
            isError = viewModel.hasFieldError("pekerjaan_ibu_suami"),
            errorMessage = viewModel.getFieldError("pekerjaan_ibu_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatIbuSuamiValue,
            onValueChange = viewModel::updateAlamatIbuSuami,
            isError = viewModel.hasFieldError("alamat_ibu_suami"),
            errorMessage = viewModel.getFieldError("alamat_ibu_suami")
        )
    }
}