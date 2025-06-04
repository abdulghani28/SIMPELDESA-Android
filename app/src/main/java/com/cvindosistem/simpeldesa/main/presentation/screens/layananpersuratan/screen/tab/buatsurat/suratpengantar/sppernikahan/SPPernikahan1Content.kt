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
import com.cvindosistem.simpeldesa.core.components.AppNumberField
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.pernikahan.SPPernikahanViewModel

@Composable
internal fun SPPernikahan1Content(
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
            UseMyDataCheckbox(
                checked = viewModel.useMyDataChecked,
                onCheckedChange = viewModel::updateUseMyData,
                isLoading = viewModel.isLoadingUserData
            )
        }

        item {
            InformasiCalonSuami(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }

        item {
            InformasiStatusPerkawinan(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiCalonSuami(
    viewModel: SPPernikahanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Calon Suami")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikSuamiValue,
            onValueChange = viewModel::updateNikSuami,
            isError = viewModel.hasFieldError("nik_suami"),
            errorMessage = viewModel.getFieldError("nik_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaSuamiValue,
            onValueChange = viewModel::updateNamaSuami,
            isError = viewModel.hasFieldError("nama_suami"),
            errorMessage = viewModel.getFieldError("nama_suami")
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
                    value = viewModel.tempatLahirSuamiValue,
                    onValueChange = viewModel::updateTempatLahirSuami,
                    isError = viewModel.hasFieldError("tempat_lahir_suami"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_suami")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirSuamiValue,
                    onValueChange = viewModel::updateTanggalLahirSuami,
                    isError = viewModel.hasFieldError("tanggal_lahir_suami"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_suami")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanSuamiValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanSuami,
            isError = viewModel.hasFieldError("kewarganegaraan_suami"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = viewModel.agamaList.find { it.id == viewModel.agamaSuamiIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgamaSuamiId(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_suami_id"),
            errorMessage = viewModel.getFieldError("agama_suami_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanSuamiValue,
            onValueChange = viewModel::updatePekerjaanSuami,
            isError = viewModel.hasFieldError("pekerjaan_suami"),
            errorMessage = viewModel.getFieldError("pekerjaan_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tinggal",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatSuamiValue,
            onValueChange = viewModel::updateAlamatSuami,
            isError = viewModel.hasFieldError("alamat_suami"),
            errorMessage = viewModel.getFieldError("alamat_suami")
        )
    }
}

@Composable
private fun InformasiStatusPerkawinan(
    viewModel: SPPernikahanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Status Perkawinan")

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Status",
            value = viewModel.statusKawinList.find { it.id == viewModel.statusKawinSuamiIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.statusKawinList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateStatusKawinSuamiId(it.id) }
            },
            options = viewModel.statusKawinList.map { it.nama },
            isError = viewModel.hasFieldError("status_kawin_suami_id"),
            errorMessage = viewModel.getFieldError("status_kawin_suami_id"),
            onDropdownExpanded = viewModel::loadStatusKawin
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.statusKawinSuamiIdValue in listOf("KAWIN", "KAWIN_TERCATAT", "KAWIN_TIDAK_TERCATAT")) {
            AppNumberField(
                label = "Jumlah Istri",
                placeholder = "Masukkan jumlah istri",
                value = viewModel.jumlahIstriValue,
                onValueChange = viewModel::updateJumlahIstri,
                isError = viewModel.hasFieldError("jumlah_istri"),
                errorMessage = viewModel.getFieldError("jumlah_istri"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.statusKawinSuamiIdValue != "BELUM_KAWIN") {
            AppTextField(
                label = "Nama Istri Sebelumnya",
                placeholder = "Masukkan nama istri sebelumnya",
                value = viewModel.namaIstriSebelumnyaValue,
                onValueChange = viewModel::updateNamaIstriSebelumnya,
                isError = viewModel.hasFieldError("nama_istri_sebelumnya"),
                errorMessage = viewModel.getFieldError("nama_istri_sebelumnya"),
            )
        }
    }
}
