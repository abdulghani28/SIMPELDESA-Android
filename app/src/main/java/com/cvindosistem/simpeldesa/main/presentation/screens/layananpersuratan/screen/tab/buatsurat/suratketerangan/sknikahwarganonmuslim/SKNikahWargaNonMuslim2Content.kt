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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel

@Composable
internal fun SKNikahWargaNonMuslim2Content(
    viewModel: SKNikahWargaNonMuslimViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

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
            AlamatSection(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }

        item {
            DataAnakSection(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun AlamatSection(
    viewModel: SKNikahWargaNonMuslimViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Alamat")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Suami",
            placeholder = "Masukkan alamat lengkap suami",
            value = viewModel.alamatSuami,
            onValueChange = viewModel::updateAlamatSuami,
            isError = viewModel.hasFieldError("alamat_suami"),
            errorMessage = viewModel.getFieldError("alamat_suami"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Istri",
            placeholder = "Masukkan alamat lengkap istri",
            value = viewModel.alamatIstri,
            onValueChange = viewModel::updateAlamatIstri,
            isError = viewModel.hasFieldError("alamat_istri"),
            errorMessage = viewModel.getFieldError("alamat_istri"),
        )
    }
}

@Composable
private fun DataAnakSection(
    viewModel: SKNikahWargaNonMuslimViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Data Anak")
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Anak Ke- (Suami)",
                    placeholder = "Urutan anak suami",
                    value = viewModel.anakKeSuami,
                    onValueChange = viewModel::updateAnakKeSuami,
                    isError = viewModel.hasFieldError("anak_ke_suami"),
                    errorMessage = viewModel.getFieldError("anak_ke_suami"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Anak Ke- (Istri)",
                    placeholder = "Urutan anak istri",
                    value = viewModel.anakKeIstri,
                    onValueChange = viewModel::updateAnakKeIstri,
                    isError = viewModel.hasFieldError("anak_ke_istri"),
                    errorMessage = viewModel.getFieldError("anak_ke_istri"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jumlah Anak Yang Diakui",
            placeholder = "Masukkan jumlah anak",
            value = viewModel.jumlahAnakYangDiakui,
            onValueChange = viewModel::updateJumlahAnakYangDiakui,
            isError = viewModel.hasFieldError("jumlah_anak_yang_diakui"),
            errorMessage = viewModel.getFieldError("jumlah_anak_yang_diakui"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Anak Pertama")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Anak Pertama",
            placeholder = "Masukkan nama anak",
            value = viewModel.namaAnak1,
            onValueChange = viewModel::updateNamaAnak1,
            isError = viewModel.hasFieldError("nama_anak_1"),
            errorMessage = viewModel.getFieldError("nama_anak_1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "No. Akta Lahir",
                    placeholder = "Masukkan nomor akta",
                    value = viewModel.noAktaLahir1,
                    onValueChange = viewModel::updateNoAktaLahir1,
                    isError = viewModel.hasFieldError("no_akta_lahir_1"),
                    errorMessage = viewModel.getFieldError("no_akta_lahir_1")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirAyahSuami,
                    onValueChange = viewModel::updateTanggalLahirAyahSuami,
                    isError = viewModel.hasFieldError("tanggal_lahir_ayah_suami"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ayah_suami")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Ayah Suami",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanAyahSuami,
            onValueChange = viewModel::updatePekerjaanAyahSuami,
            isError = viewModel.hasFieldError("pekerjaan_ayah_suami"),
            errorMessage = viewModel.getFieldError("pekerjaan_ayah_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Ayah Suami",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatAyahSuami,
            onValueChange = viewModel::updateAlamatAyahSuami,
            isError = viewModel.hasFieldError("alamat_ayah_suami"),
            errorMessage = viewModel.getFieldError("alamat_ayah_suami"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Ayah Suami",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponAyahSuami,
            onValueChange = viewModel::updateTeleponAyahSuami,
            isError = viewModel.hasFieldError("telepon_ayah_suami"),
            errorMessage = viewModel.getFieldError("telepon_ayah_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Ayah Suami",
            value = viewModel.agamaList.find { it.id == viewModel.agamaAyahSuamiId }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaAyahSuamiId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ayah_suami_id"),
            errorMessage = viewModel.getFieldError("agama_ayah_suami_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Ayah Suami",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanAyahSuami,
            onValueChange = viewModel::updateKewarganegaraanAyahSuami,
            isError = viewModel.hasFieldError("kewarganegaraan_ayah_suami"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ayah_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Ayah Suami",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiAyahSuami,
            onValueChange = viewModel::updateNamaOrganisasiAyahSuami,
            isError = viewModel.hasFieldError("nama_organisasi_ayah_suami"),
            errorMessage = viewModel.getFieldError("nama_organisasi_ayah_suami")
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Data Ibu Suami")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Ibu Suami",
            placeholder = "Masukkan NIK ibu suami",
            value = viewModel.nikIbuSuami,
            onValueChange = viewModel::updateNikIbuSuami,
            isError = viewModel.hasFieldError("nik_ibu_suami"),
            errorMessage = viewModel.getFieldError("nik_ibu_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Ibu Suami",
            placeholder = "Masukkan nama lengkap ibu suami",
            value = viewModel.namaIbuSuami,
            onValueChange = viewModel::updateNamaIbuSuami,
            isError = viewModel.hasFieldError("nama_ibu_suami"),
            errorMessage = viewModel.getFieldError("nama_ibu_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirIbuSuami,
                    onValueChange = viewModel::updateTempatLahirIbuSuami,
                    isError = viewModel.hasFieldError("tempat_lahir_ibu_suami"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ibu_suami")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirIbuSuami,
                    onValueChange = viewModel::updateTanggalLahirIbuSuami,
                    isError = viewModel.hasFieldError("tanggal_lahir_ibu_suami"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ibu_suami")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Ibu Suami",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanIbuSuami,
            onValueChange = viewModel::updatePekerjaanIbuSuami,
            isError = viewModel.hasFieldError("pekerjaan_ibu_suami"),
            errorMessage = viewModel.getFieldError("pekerjaan_ibu_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Ibu Suami",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatIbuSuami,
            onValueChange = viewModel::updateAlamatIbuSuami,
            isError = viewModel.hasFieldError("alamat_ibu_suami"),
            errorMessage = viewModel.getFieldError("alamat_ibu_suami"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Telepon Ibu Suami",
            placeholder = "Masukkan nomor telepon",
            value = viewModel.teleponIbuSuami,
            onValueChange = viewModel::updateTeleponIbuSuami,
            isError = viewModel.hasFieldError("telepon_ibu_suami"),
            errorMessage = viewModel.getFieldError("telepon_ibu_suami"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Ibu Suami",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIbuSuamiId }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                viewModel.agamaList.find { it.nama == selectedNama }?.let {
                    viewModel.updateAgamaIbuSuamiId(it.id)
                }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ibu_suami_id"),
            errorMessage = viewModel.getFieldError("agama_ibu_suami_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Kewarganegaraan Ibu Suami",
            placeholder = "Masukkan kewarganegaraan",
            value = viewModel.kewarganegaraanIbuSuami,
            onValueChange = viewModel::updateKewarganegaraanIbuSuami,
            isError = viewModel.hasFieldError("kewarganegaraan_ibu_suami"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ibu_suami")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Organisasi Ibu Suami",
            placeholder = "Masukkan nama organisasi",
            value = viewModel.namaOrganisasiIbuSuami,
            onValueChange = viewModel::updateNamaOrganisasiIbuSuami,
            isError = viewModel.hasFieldError("nama_organisasi_ibu_suami"),
            errorMessage = viewModel.getFieldError("nama_organisasi_ibu_suami")
        )
    }
}