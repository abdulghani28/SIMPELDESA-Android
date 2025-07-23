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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel

@Composable
internal fun SKNikahWargaNonMuslim2Content(
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
            AlamatSection(
                viewModel = viewModel
            )
        }

        item {
            DataAnakSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun AlamatSection(
    viewModel: SKNikahWargaNonMuslimViewModel
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
    viewModel: SKNikahWargaNonMuslimViewModel
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
            isError = viewModel.hasFieldError("nama_anak1"),
            errorMessage = viewModel.getFieldError("nama_anak1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "No. Akta Lahir",
                    placeholder = "Masukkan nomor akta",
                    value = viewModel.noAktaLahir1,
                    onValueChange = viewModel::updateNoAktaLahir1,
                    isError = viewModel.hasFieldError("no_akta_lahir1"),
                    errorMessage = viewModel.getFieldError("no_akta_lahir1")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahir1,
                    onValueChange = viewModel::updateTanggalLahir1,
                    isError = viewModel.hasFieldError("tanggal_lahir1"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir1")
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Anak Kedua")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Anak Kedua",
            placeholder = "Masukkan nama anak",
            value = viewModel.namaAnak2,
            onValueChange = viewModel::updateNamaAnak2,
            isError = viewModel.hasFieldError("nama_anak2"),
            errorMessage = viewModel.getFieldError("nama_anak2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "No. Akta Lahir",
                    placeholder = "Masukkan nomor akta",
                    value = viewModel.noAktaLahir2,
                    onValueChange = viewModel::updateNoAktaLahir2,
                    isError = viewModel.hasFieldError("no_akta_lahir2"),
                    errorMessage = viewModel.getFieldError("no_akta_lahir2")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahir2,
                    onValueChange = viewModel::updateTanggalLahir2,
                    isError = viewModel.hasFieldError("tanggal_lahir2"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir2")
                )
            }
        }
    }
}