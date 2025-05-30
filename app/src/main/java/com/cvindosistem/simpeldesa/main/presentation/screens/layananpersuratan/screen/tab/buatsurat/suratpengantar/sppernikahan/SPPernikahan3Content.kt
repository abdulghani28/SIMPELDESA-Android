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
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SPPernikahanViewModel

@Composable
internal fun SPPernikahan3Content(
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
            InformasiCalonIstri(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiCalonIstri(
    viewModel: SPPernikahanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Biodata Istri")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikIstriValue,
            onValueChange = viewModel::updateNikIstri,
            isError = viewModel.hasFieldError("nik_istri"),
            errorMessage = viewModel.getFieldError("nik_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaIstriValue,
            onValueChange = viewModel::updateNamaIstri,
            isError = viewModel.hasFieldError("nama_istri"),
            errorMessage = viewModel.getFieldError("nama_istri"),
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
                    value = viewModel.tempatLahirIstriValue,
                    onValueChange = viewModel::updateTempatLahirIstri,
                    isError = viewModel.hasFieldError("tempat_lahir_istri"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_istri"),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirIstriValue,
                    onValueChange = viewModel::updateTanggalLahirIstri,
                    isError = viewModel.hasFieldError("tanggal_lahir_istri"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_istri"),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanIstriValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanIstri,
            isError = viewModel.hasFieldError("kewarganegaraan_istri"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_istri"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaIstriIdValue,
            onValueChange = viewModel::updateAgamaIstriId,
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_istri"),
            errorMessage = viewModel.getFieldError("agama_istri"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanIstriValue,
            onValueChange = viewModel::updatePekerjaanIstri,
            isError = viewModel.hasFieldError("pekerjaan_istri"),
            errorMessage = viewModel.getFieldError("pekerjaan_istri"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatIstriValue,
            onValueChange = viewModel::updateAlamatIstri,
            isError = viewModel.hasFieldError("alamat_istri"),
            errorMessage = viewModel.getFieldError("alamat_istri"),
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Informasi Status Perkawinan")

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Status",
            value = viewModel.statusKawinIstriIdValue,
            onValueChange = viewModel::updateStatusKawinIstriId,
            options = listOf("BELUM_KAWIN", "KAWIN"),
            isError = viewModel.hasFieldError("status_kawin_istri_id"),
            errorMessage = viewModel.getFieldError("status_kawin_istri_id"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Suami Sebelumnya",
            placeholder = "Masukkan nama suami sebelumnya",
            value = viewModel.namaSuamiSebelumnyaValue,
            onValueChange = viewModel::updateNamaSuamiSebelumnya,
            isError = viewModel.hasFieldError("nama_suami_sebelumnya"),
            errorMessage = viewModel.getFieldError("nama_suami_sebelumnya"),
        )
    }
}