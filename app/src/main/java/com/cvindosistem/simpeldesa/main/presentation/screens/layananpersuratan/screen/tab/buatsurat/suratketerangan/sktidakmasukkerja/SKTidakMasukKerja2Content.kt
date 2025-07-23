package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sktidakmasukkerja

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
import com.cvindosistem.simpeldesa.core.components.AppNumberField
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja.SKTidakMasukKerjaViewModel

@Composable
internal fun SKTidakMasukKerja2Content(
    viewModel: SKTidakMasukKerjaViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Perusahaan"),
                currentStep = 2
            )
        }

        item {
            InformasiPerusahaan(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPerusahaan(
    viewModel: SKTidakMasukKerjaViewModel
) {
    Column {
        SectionTitle("Informasi Perusahaan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Perusahaan",
            placeholder = "Masukkan nama perusahaan",
            value = viewModel.namaPerusahaanValue,
            onValueChange = viewModel::updateNamaPerusahaan,
            isError = viewModel.hasFieldError("nama_perusahaan"),
            errorMessage = viewModel.getFieldError("nama_perusahaan"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jabatan",
            placeholder = "Masukkan jabatan",
            value = viewModel.jabatanValue,
            onValueChange = viewModel::updateJabatan,
            isError = viewModel.hasFieldError("jabatan"),
            errorMessage = viewModel.getFieldError("jabatan"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppNumberField(
                    label = "Lama Izin",
                    placeholder = "0",
                    value = viewModel.lamaValue,
                    onValueChange = viewModel::updateLama,
                    isError = viewModel.hasFieldError("lama"),
                    errorMessage = viewModel.getFieldError("lama"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Terhitung dari tanggal",
                    value = viewModel.terhitungDariValue,
                    onValueChange = viewModel::updateTerhitungDari,
                    isError = viewModel.hasFieldError("terhitung_dari"),
                    errorMessage = viewModel.getFieldError("terhitung_dari"),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alasan Izin",
            placeholder = "Masukkan alasan",
            value = viewModel.alasanIzinValue,
            onValueChange = viewModel::updateAlasanIzin,
            isError = viewModel.hasFieldError("alasan_izin"),
            errorMessage = viewModel.getFieldError("alasan_izin"),
        )
    }
}