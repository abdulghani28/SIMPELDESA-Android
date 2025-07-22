package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sknikahwarganonmuslim

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
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel

@Composable
internal fun SKNikahWargaNonMuslim4Content(
    viewModel: SKNikahWargaNonMuslimViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf("Data Suami & Istri", "Alamat & Anak", "Orang Tua Suami", "Orang Tua Istri", "Saksi Pernikahan", "Pernikahan & Pemuka Agama", "Legalitas & Putusan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            OrangTuaIstriSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun OrangTuaIstriSection(
    viewModel: SKNikahWargaNonMuslimViewModel
) {
    Column {
        SectionTitle("Data Ayah Istri")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Ayah Istri",
            placeholder = "Masukkan NIK ayah istri",
            value = viewModel.nikAyahIstri,
            onValueChange = viewModel::updateNikAyahIstri,
            isError = viewModel.hasFieldError("nik_ayah_istri"),
            errorMessage = viewModel.getFieldError("nik_ayah_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ayah Istri",
            placeholder = "Masukkan nama lengkap ayah istri",
            value = viewModel.namaAyahIstri,
            onValueChange = viewModel::updateNamaAyahIstri,
            isError = viewModel.hasFieldError("nama_ayah_istri"),
            errorMessage = viewModel.getFieldError("nama_ayah_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirAyahIstri,
                    onValueChange = viewModel::updateTempatLahirAyahIstri,
                    isError = viewModel.hasFieldError("tempat_lahir_ayah_istri"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ayah_istri")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirAyahIstri,
                    onValueChange = viewModel::updateTanggalLahirAyahIstri,
                    isError = viewModel.hasFieldError("tanggal_lahir_ayah_istri"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ayah_istri")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Ayah Istri",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanAyahIstri,
            onValueChange = viewModel::updatePekerjaanAyahIstri,
            isError = viewModel.hasFieldError("pekerjaan_ayah_istri"),
            errorMessage = viewModel.getFieldError("pekerjaan_ayah_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Ayah Istri",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatAyahIstri,
            onValueChange = viewModel::updateAlamatAyahIstri,
            isError = viewModel.hasFieldError("alamat_ayah_istri"),
            errorMessage = viewModel.getFieldError("alamat_ayah_istri"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Ayah Istri",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponAyahIstri,
            onValueChange = viewModel::updateTeleponAyahIstri,
            isError = viewModel.hasFieldError("telepon_ayah_istri"),
            errorMessage = viewModel.getFieldError("telepon_ayah_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Ayah Istri",
            value = viewModel.agamaList.find { it.id == viewModel.agamaAyahIstriId }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaAyahIstriId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ayah_istri_id"),
            errorMessage = viewModel.getFieldError("agama_ayah_istri_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Ayah Istri",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanAyahIstri,
            onValueChange = viewModel::updateKewarganegaraanAyahIstri,
            isError = viewModel.hasFieldError("kewarganegaraan_ayah_istri"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ayah_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Ayah Istri",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiAyahIstri,
            onValueChange = viewModel::updateNamaOrganisasiAyahIstri,
            isError = viewModel.hasFieldError("nama_organisasi_ayah_istri"),
            errorMessage = viewModel.getFieldError("nama_organisasi_ayah_istri")
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Data Ibu Istri")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Ibu Istri",
            placeholder = "Masukkan NIK ibu istri",
            value = viewModel.nikIbuIstri,
            onValueChange = viewModel::updateNikIbuIstri,
            isError = viewModel.hasFieldError("nik_ibu_istri"),
            errorMessage = viewModel.getFieldError("nik_ibu_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ibu Istri",
            placeholder = "Masukkan nama lengkap ibu istri",
            value = viewModel.namaIbuIstri,
            onValueChange = viewModel::updateNamaIbuIstri,
            isError = viewModel.hasFieldError("nama_ibu_istri"),
            errorMessage = viewModel.getFieldError("nama_ibu_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirIbuIstri,
                    onValueChange = viewModel::updateTempatLahirIbuIstri,
                    isError = viewModel.hasFieldError("tempat_lahir_ibu_istri"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ibu_istri")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirIbuIstri,
                    onValueChange = viewModel::updateTanggalLahirIbuIstri,
                    isError = viewModel.hasFieldError("tanggal_lahir_ibu_istri"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ibu_istri")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Ibu Istri",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanIbuIstri,
            onValueChange = viewModel::updatePekerjaanIbuIstri,
            isError = viewModel.hasFieldError("pekerjaan_ibu_istri"),
            errorMessage = viewModel.getFieldError("pekerjaan_ibu_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Ibu Istri",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatIbuIstri,
            onValueChange = viewModel::updateAlamatIbuIstri,
            isError = viewModel.hasFieldError("alamat_ibu_istri"),
            errorMessage = viewModel.getFieldError("alamat_ibu_istri"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Ibu Istri",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponIbuIstri,
            onValueChange = viewModel::updateTeleponIbuIstri,
            isError = viewModel.hasFieldError("telepon_ibu_istri"),
            errorMessage = viewModel.getFieldError("telepon_ibu_istri"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Ibu Istri",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIbuIstriId }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaIbuIstriId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ibu_istri_id"),
            errorMessage = viewModel.getFieldError("agama_ibu_istri_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Ibu Istri",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanIbuIstri,
            onValueChange = viewModel::updateKewarganegaraanIbuIstri,
            isError = viewModel.hasFieldError("kewarganegaraan_ibu_istri"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ibu_istri")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Ibu Istri",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiIbuIstri,
            onValueChange = viewModel::updateNamaOrganisasiIbuIstri,
            isError = viewModel.hasFieldError("nama_organisasi_ibu_istri"),
            errorMessage = viewModel.getFieldError("nama_organisasi_ibu_istri")
        )
    }
}