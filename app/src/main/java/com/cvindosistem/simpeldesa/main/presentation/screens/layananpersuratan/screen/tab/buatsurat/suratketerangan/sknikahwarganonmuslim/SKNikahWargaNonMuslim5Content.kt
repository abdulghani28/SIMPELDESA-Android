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
import com.cvindosistem.simpeldesa.core.components.CheckBoxField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel

@Composable
internal fun SKNikahWargaNonMuslim5Content(
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
            SaksiPernikahanSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun SaksiPernikahanSection(
    viewModel: SKNikahWargaNonMuslimViewModel
) {
    Column {
        SectionTitle("Data Saksi 1")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Saksi 1",
            placeholder = "Masukkan NIK saksi 1",
            value = viewModel.nikSaksi1,
            onValueChange = viewModel::updateNikSaksi1,
            isError = viewModel.hasFieldError("nik_saksi1"),
            errorMessage = viewModel.getFieldError("nik_saksi1"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Saksi 1",
            placeholder = "Masukkan nama lengkap saksi 1",
            value = viewModel.namaSaksi1,
            onValueChange = viewModel::updateNamaSaksi1,
            isError = viewModel.hasFieldError("nama_saksi1"),
            errorMessage = viewModel.getFieldError("nama_saksi1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirSaksi1,
                    onValueChange = viewModel::updateTempatLahirSaksi1,
                    isError = viewModel.hasFieldError("tempat_lahir_saksi1"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_saksi1")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirSaksi1,
                    onValueChange = viewModel::updateTanggalLahirSaksi1,
                    isError = viewModel.hasFieldError("tanggal_lahir_saksi1"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_saksi1")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Saksi 1",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanSaksi1,
            onValueChange = viewModel::updatePekerjaanSaksi1,
            isError = viewModel.hasFieldError("pekerjaan_saksi1"),
            errorMessage = viewModel.getFieldError("pekerjaan_saksi1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Saksi 1",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatSaksi1,
            onValueChange = viewModel::updateAlamatSaksi1,
            isError = viewModel.hasFieldError("alamat_saksi1"),
            errorMessage = viewModel.getFieldError("alamat_saksi1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Saksi 1",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponSaksi1,
            onValueChange = viewModel::updateTeleponSaksi1,
            isError = viewModel.hasFieldError("telepon_saksi1"),
            errorMessage = viewModel.getFieldError("telepon_saksi1"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Saksi 1",
            value = viewModel.agamaList.find { it.id == viewModel.agamaSaksi1Id }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaSaksi1Id(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_saksi1_id"),
            errorMessage = viewModel.getFieldError("agama_saksi1_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Saksi 1",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanSaksi1,
            onValueChange = viewModel::updateKewarganegaraanSaksi1,
            isError = viewModel.hasFieldError("kewarganegaraan_saksi1"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_saksi1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Saksi 1",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiSaksi1,
            onValueChange = viewModel::updateNamaOrganisasiSaksi1,
            isError = viewModel.hasFieldError("nama_organisasi_saksi1"),
            errorMessage = viewModel.getFieldError("nama_organisasi_saksi1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ayah Saksi 1",
            placeholder = "Masukkan nama ayah saksi 1",
            value = viewModel.namaAyahSaksi1,
            onValueChange = viewModel::updateNamaAyahSaksi1,
            isError = viewModel.hasFieldError("nama_ayah_saksi1"),
            errorMessage = viewModel.getFieldError("nama_ayah_saksi1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckBoxField(
            label = "Apakah saksi 1 adalah warga desa?",
            checked = viewModel.isSaksi1WargaDesa,
            onCheckedChange = viewModel::updateIsSaksi1WargaDesa
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Data Saksi 2")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Saksi 2",
            placeholder = "Masukkan NIK saksi 2",
            value = viewModel.nikSaksi2,
            onValueChange = viewModel::updateNikSaksi2,
            isError = viewModel.hasFieldError("nik_saksi2"),
            errorMessage = viewModel.getFieldError("nik_saksi2"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Saksi 2",
            placeholder = "Masukkan nama lengkap saksi 2",
            value = viewModel.namaSaksi2,
            onValueChange = viewModel::updateNamaSaksi2,
            isError = viewModel.hasFieldError("nama_saksi2"),
            errorMessage = viewModel.getFieldError("nama_saksi2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirSaksi2,
                    onValueChange = viewModel::updateTempatLahirSaksi2,
                    isError = viewModel.hasFieldError("tempat_lahir_saksi2"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_saksi2")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirSaksi2,
                    onValueChange = viewModel::updateTanggalLahirSaksi2,
                    isError = viewModel.hasFieldError("tanggal_lahir_saksi2"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_saksi2")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Saksi 2",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanSaksi2,
            onValueChange = viewModel::updatePekerjaanSaksi2,
            isError = viewModel.hasFieldError("pekerjaan_saksi2"),
            errorMessage = viewModel.getFieldError("pekerjaan_saksi2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Saksi 2",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatSaksi2,
            onValueChange = viewModel::updateAlamatSaksi2,
            isError = viewModel.hasFieldError("alamat_saksi2"),
            errorMessage = viewModel.getFieldError("alamat_saksi2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Saksi 2",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponSaksi2,
            onValueChange = viewModel::updateTeleponSaksi2,
            isError = viewModel.hasFieldError("telepon_saksi2"),
            errorMessage = viewModel.getFieldError("telepon_saksi2"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Saksi 2",
            value = viewModel.agamaList.find { it.id == viewModel.agamaSaksi2Id }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaSaksi2Id(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_saksi2_id"),
            errorMessage = viewModel.getFieldError("agama_saksi2_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Saksi 2",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanSaksi2,
            onValueChange = viewModel::updateKewarganegaraanSaksi2,
            isError = viewModel.hasFieldError("kewarganegaraan_saksi2"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_saksi2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Saksi 2",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiSaksi2,
            onValueChange = viewModel::updateNamaOrganisasiSaksi2,
            isError = viewModel.hasFieldError("nama_organisasi_saksi2"),
            errorMessage = viewModel.getFieldError("nama_organisasi_saksi2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ayah Saksi 2",
            placeholder = "Masukkan nama ayah saksi 2",
            value = viewModel.namaAyahSaksi2,
            onValueChange = viewModel::updateNamaAyahSaksi2,
            isError = viewModel.hasFieldError("nama_ayah_saksi2"),
            errorMessage = viewModel.getFieldError("nama_ayah_saksi2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckBoxField(
            label = "Apakah saksi 2 adalah warga desa?",
            checked = viewModel.isSaksi2WargaDesa,
            onCheckedChange = viewModel::updateIsSaksi2WargaDesa
        )
    }
}