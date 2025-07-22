package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sknikahwarganonmuslim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.CheckBoxField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua.SKIzinOrangTuaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel

@Composable
internal fun SKNikahWargaNonMuslim1Content(
    viewModel: SKNikahWargaNonMuslimViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Suami & Istri", "Alamat & Anak", "Orang Tua Suami", "Orang Tua Istri", "Saksi Pernikahan", "Pernikahan & Pemuka Agama", "Legalitas & Putusan"),
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
            DataSuamiSection(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }

        item {
            DataIstriSection(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun DataSuamiSection(
    viewModel: SKNikahWargaNonMuslimViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Data Suami")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Suami",
            placeholder = "Masukkan NIK suami",
            value = viewModel.nikSuami,
            onValueChange = viewModel::updateNikSuami,
            isError = viewModel.hasFieldError("nik_suami"),
            errorMessage = viewModel.getFieldError("nik_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Suami",
            placeholder = "Masukkan nama lengkap suami",
            value = viewModel.namaSuami,
            onValueChange = viewModel::updateNamaSuami,
            isError = viewModel.hasFieldError("nama_suami"),
            errorMessage = viewModel.getFieldError("nama_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Suami",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirSuami,
                    onValueChange = viewModel::updateTempatLahirSuami,
                    isError = viewModel.hasFieldError("tempat_lahir_suami"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_suami")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Suami",
                    value = viewModel.tanggalLahirSuami,
                    onValueChange = viewModel::updateTanggalLahirSuami,
                    isError = viewModel.hasFieldError("tanggal_lahir_suami"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_suami")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Suami",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanSuami,
            onValueChange = viewModel::updateKewarganegaraanSuami,
            isError = viewModel.hasFieldError("kewarganegaraan_suami"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Warga Negara Suami",
            placeholder = "Masukkan warga negara",
            value = viewModel.wargaNegaraSuami,
            onValueChange = viewModel::updateWargaNegaraSuami,
            isError = viewModel.hasFieldError("warga_negara_suami"),
            errorMessage = viewModel.getFieldError("warga_negara_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Suami",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanSuami,
            onValueChange = viewModel::updatePekerjaanSuami,
            isError = viewModel.hasFieldError("pekerjaan_suami"),
            errorMessage = viewModel.getFieldError("pekerjaan_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Pendidikan Suami",
            value = viewModel.pendidikanList.find { it.id == viewModel.pendidikanIdSuami }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.pendidikanList.find { it.nama == selectedNama }?.let {
                    viewModel.updatePendidikanIdSuami(it.id)
                }
            },
            options = viewModel.pendidikanList.map { it.nama },
            isError = viewModel.hasFieldError("pendidikan_id_suami"),
            errorMessage = viewModel.getFieldError("pendidikan_id_suami"),
            onDropdownExpanded = viewModel::loadPendidikan
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Status Kawin Suami",
            placeholder = "Masukkan status kawin",
            value = viewModel.statusKawinSuami,
            onValueChange = viewModel::updateStatusKawinSuami,
            isError = viewModel.hasFieldError("status_kawin_suami"),
            errorMessage = viewModel.getFieldError("status_kawin_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Perkawinan Ke- (Suami)",
            placeholder = "Masukkan urutan perkawinan",
            value = viewModel.perkawinanKeSuami,
            onValueChange = viewModel::updatePerkawinanKeSuami,
            isError = viewModel.hasFieldError("perkawinan_ke_suami"),
            errorMessage = viewModel.getFieldError("perkawinan_ke_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Paspor Suami",
            placeholder = "Masukkan nomor paspor",
            value = viewModel.pasporSuami,
            onValueChange = viewModel::updatePasporSuami,
            isError = viewModel.hasFieldError("paspor_suami"),
            errorMessage = viewModel.getFieldError("paspor_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor KK Suami",
            placeholder = "Masukkan nomor KK",
            value = viewModel.noKkSuami,
            onValueChange = viewModel::updateNoKkSuami,
            isError = viewModel.hasFieldError("no_kk_suami"),
            errorMessage = viewModel.getFieldError("no_kk_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Suami",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiSuami,
            onValueChange = viewModel::updateNamaOrganisasiSuami,
            isError = viewModel.hasFieldError("nama_organisasi_suami"),
            errorMessage = viewModel.getFieldError("nama_organisasi_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Suami",
            value = viewModel.agamaList.find { it.id == viewModel.agamaSuamiId }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaSuamiId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_suami_id"),
            errorMessage = viewModel.getFieldError("agama_suami_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Suami",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponSuami,
            onValueChange = viewModel::updateTeleponSuami,
            isError = viewModel.hasFieldError("telepon_suami"),
            errorMessage = viewModel.getFieldError("telepon_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
    }
}

@Composable
private fun DataIstriSection(
    viewModel: SKNikahWargaNonMuslimViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Data Istri")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Istri",
            placeholder = "Masukkan NIK istri",
            value = viewModel.nikIstri,
            onValueChange = viewModel::updateNikIstri,
            isError = viewModel.hasFieldError("nik_istri"),
            errorMessage = viewModel.getFieldError("nik_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Istri",
            placeholder = "Masukkan nama lengkap istri",
            value = viewModel.namaIstri,
            onValueChange = viewModel::updateNamaIstri,
            isError = viewModel.hasFieldError("nama_istri"),
            errorMessage = viewModel.getFieldError("nama_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Istri",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirIstri,
                    onValueChange = viewModel::updateTempatLahirIstri,
                    isError = viewModel.hasFieldError("tempat_lahir_istri"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_istri")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Istri",
                    value = viewModel.tanggalLahirIstri,
                    onValueChange = viewModel::updateTanggalLahirIstri,
                    isError = viewModel.hasFieldError("tanggal_lahir_istri"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_istri")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Istri",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanIstri,
            onValueChange = viewModel::updateKewarganegaraanIstri,
            isError = viewModel.hasFieldError("kewarganegaraan_istri"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Warga Negara Istri",
            placeholder = "Masukkan warga negara",
            value = viewModel.wargaNegaraIstri,
            onValueChange = viewModel::updateWargaNegaraIstri,
            isError = viewModel.hasFieldError("warga_negara_istri"),
            errorMessage = viewModel.getFieldError("warga_negara_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Istri",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanIstri,
            onValueChange = viewModel::updatePekerjaanIstri,
            isError = viewModel.hasFieldError("pekerjaan_istri"),
            errorMessage = viewModel.getFieldError("pekerjaan_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Pendidikan Istri",
            value = viewModel.pendidikanList.find { it.id == viewModel.pendidikanIdIstri }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.pendidikanList.find { it.nama == selectedNama }?.let {
                    viewModel.updatePendidikanIdIstri(it.id)
                }
            },
            options = viewModel.pendidikanList.map { it.nama },
            isError = viewModel.hasFieldError("pendidikan_id_istri"),
            errorMessage = viewModel.getFieldError("pendidikan_id_istri"),
            onDropdownExpanded = viewModel::loadPendidikan
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Status Kawin Istri",
            placeholder = "Masukkan status kawin",
            value = viewModel.statusKawinIstri,
            onValueChange = viewModel::updateStatusKawinIstri,
            isError = viewModel.hasFieldError("status_kawin_istri"),
            errorMessage = viewModel.getFieldError("status_kawin_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Perkawinan Ke- (Istri)",
            placeholder = "Masukkan urutan perkawinan",
            value = viewModel.perkawinanKeIstri,
            onValueChange = viewModel::updatePerkawinanKeIstri,
            isError = viewModel.hasFieldError("perkawinan_ke_istri"),
            errorMessage = viewModel.getFieldError("perkawinan_ke_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Paspor Istri",
            placeholder = "Masukkan nomor paspor",
            value = viewModel.pasporIstri,
            onValueChange = viewModel::updatePasporIstri,
            isError = viewModel.hasFieldError("paspor_istri"),
            errorMessage = viewModel.getFieldError("paspor_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor KK Istri",
            placeholder = "Masukkan nomor KK",
            value = viewModel.noKkIstri,
            onValueChange = viewModel::updateNoKkIstri,
            isError = viewModel.hasFieldError("no_kk_istri"),
            errorMessage = viewModel.getFieldError("no_kk_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Istri",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiIstri,
            onValueChange = viewModel::updateNamaOrganisasiIstri,
            isError = viewModel.hasFieldError("nama_organisasi_istri"),
            errorMessage = viewModel.getFieldError("nama_organisasi_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Istri",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIstriId }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaIstriId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_istri_id"),
            errorMessage = viewModel.getFieldError("agama_istri_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Istri",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponIstri,
            onValueChange = viewModel::updateTeleponIstri,
            isError = viewModel.hasFieldError("telepon_istri"),
            errorMessage = viewModel.getFieldError("telepon_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckBoxField(
            label = "Istri adalah warga desa",
            checked = viewModel.isIstriWargaDesa,
            onCheckedChange = viewModel::updateIsIstriWargaDesa,
        )
    }
}