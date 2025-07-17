package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkepemilikankendaraan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikankendaraan.SKKepemilikanKendaraanViewModel

@Composable
internal fun SKKepemilikanKendaraan2Content(
    viewModel: SKKepemilikanKendaraanViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemilik", "Data Kendaraan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiKendaraan(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiKendaraan(
    viewModel: SKKepemilikanKendaraanViewModel
) {
    Column {
        SectionTitle("Data Kendaraan")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Merk Kendaraan",
            placeholder = "Masukkan merk kendaraan",
            value = viewModel.merkValue,
            onValueChange = viewModel::updateMerk,
            isError = viewModel.hasFieldError("merk"),
            errorMessage = viewModel.getFieldError("merk")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tahun Pembuatan",
            placeholder = "Masukkan tahun pembuatan",
            value = viewModel.tahunPembuatanValue,
            onValueChange = viewModel::updateTahunPembuatan,
            isError = viewModel.hasFieldError("tahun_pembuatan"),
            errorMessage = viewModel.getFieldError("tahun_pembuatan"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Warna",
            placeholder = "Masukkan warna kendaraan",
            value = viewModel.warnaValue,
            onValueChange = viewModel::updateWarna,
            isError = viewModel.hasFieldError("warna"),
            errorMessage = viewModel.getFieldError("warna")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Polisi",
            placeholder = "Masukkan nomor polisi",
            value = viewModel.nomorPolisiValue,
            onValueChange = viewModel::updateNomorPolisi,
            isError = viewModel.hasFieldError("nomor_polisi"),
            errorMessage = viewModel.getFieldError("nomor_polisi")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Mesin",
            placeholder = "Masukkan nomor mesin",
            value = viewModel.nomorMesinValue,
            onValueChange = viewModel::updateNomorMesin,
            isError = viewModel.hasFieldError("nomor_mesin"),
            errorMessage = viewModel.getFieldError("nomor_mesin")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Rangka",
            placeholder = "Masukkan nomor rangka",
            value = viewModel.nomorRangkaValue,
            onValueChange = viewModel::updateNomorRangka,
            isError = viewModel.hasFieldError("nomor_rangka"),
            errorMessage = viewModel.getFieldError("nomor_rangka")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor BPKB",
            placeholder = "Masukkan nomor BPKB",
            value = viewModel.nomorBpkbValue,
            onValueChange = viewModel::updateNomorBpkb,
            isError = viewModel.hasFieldError("nomor_bpkb"),
            errorMessage = viewModel.getFieldError("nomor_bpkb")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Bahan Bakar",
            placeholder = "Masukkan jenis bahan bakar",
            value = viewModel.bahanBakarValue,
            onValueChange = viewModel::updateBahanBakar,
            isError = viewModel.hasFieldError("bahan_bakar"),
            errorMessage = viewModel.getFieldError("bahan_bakar")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Isi Silinder (CC)",
            placeholder = "Masukkan isi silinder",
            value = viewModel.isiSilinderValue,
            onValueChange = viewModel::updateIsiSilinder,
            isError = viewModel.hasFieldError("isi_silinder"),
            errorMessage = viewModel.getFieldError("isi_silinder"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Atas Nama",
            placeholder = "Kendaraan atas nama siapa",
            value = viewModel.atasNamaValue,
            onValueChange = viewModel::updateAtasNama,
            isError = viewModel.hasFieldError("atas_nama"),
            errorMessage = viewModel.getFieldError("atas_nama")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan pengajuan",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan")
        )
    }
}
