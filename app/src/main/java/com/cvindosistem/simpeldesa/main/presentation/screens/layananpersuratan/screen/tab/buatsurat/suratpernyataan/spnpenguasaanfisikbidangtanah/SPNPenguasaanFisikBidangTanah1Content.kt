package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpernyataan.spnpenguasaanfisikbidangtanah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.CheckBoxField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah.SPNPenguasaanFisikBidangTanahViewModel

@Composable
internal fun SPNPenguasaanFisikBidangTanah1Content(
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicatorFlexible(
                steps = listOf(
                    "Data Pemohon",
                    "Lokasi & Identitas Tanah",
                    "Perolehan & Batas Tanah",
                    "Data Saksi",
                    "Keperluan"
                ),
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
            DataPemohon(viewModel = viewModel)
        }
    }
}

@Composable
private fun DataPemohon(
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
) {
    Column {
        SectionTitle("Data Pemohon")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK) Pemohon",
            placeholder = "Masukkan NIK pemohon",
            value = viewModel.nikPemohonValue,
            onValueChange = viewModel::updateNikPemohon,
            isError = viewModel.hasFieldError("nik_pemohon"),
            errorMessage = viewModel.getFieldError("nik_pemohon"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Pemohon",
            placeholder = "Masukkan nama pemohon",
            value = viewModel.namaPemohonValue,
            onValueChange = viewModel::updateNamaPemohon,
            isError = viewModel.hasFieldError("nama_pemohon"),
            errorMessage = viewModel.getFieldError("nama_pemohon")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirPemohonValue,
                    onValueChange = viewModel::updateTempatLahirPemohon,
                    isError = viewModel.hasFieldError("tempat_lahir_pemohon"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_pemohon")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirPemohonValue,
                    onValueChange = viewModel::updateTanggalLahirPemohon,
                    isError = viewModel.hasFieldError("tanggal_lahir_pemohon"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_pemohon")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanValue,
            onValueChange = viewModel::updatePekerjaan,
            isError = viewModel.hasFieldError("pekerjaan"),
            errorMessage = viewModel.getFieldError("pekerjaan")
        )
    }
}