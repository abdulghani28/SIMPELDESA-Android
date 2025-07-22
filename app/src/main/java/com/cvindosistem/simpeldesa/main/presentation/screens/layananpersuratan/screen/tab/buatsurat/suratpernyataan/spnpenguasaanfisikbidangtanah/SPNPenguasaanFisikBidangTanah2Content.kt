package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpernyataan.spnpenguasaanfisikbidangtanah

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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.StepIndicatorFlexible
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah.SPNPenguasaanFisikBidangTanahViewModel

@Composable
internal fun SPNPenguasaanFisikBidangTanah2Content(
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
            LokasiIdentitasTanah(viewModel = viewModel)
        }
    }
}

@Composable
private fun LokasiIdentitasTanah(
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
) {
    Column {
        SectionTitle("Lokasi & Identitas Tanah")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Tanah",
            placeholder = "Masukkan alamat tanah",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat 1",
            placeholder = "Masukkan alamat 1",
            value = viewModel.alamat1Value,
            onValueChange = viewModel::updateAlamat1,
            isError = viewModel.hasFieldError("alamat1"),
            errorMessage = viewModel.getFieldError("alamat1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat 2",
            placeholder = "Masukkan alamat 2",
            value = viewModel.alamat2Value,
            onValueChange = viewModel::updateAlamat2,
            isError = viewModel.hasFieldError("alamat2"),
            errorMessage = viewModel.getFieldError("alamat2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "RT/RW",
                    placeholder = "Masukkan RT/RW",
                    value = viewModel.rtRwValue,
                    onValueChange = viewModel::updateRtRw,
                    isError = viewModel.hasFieldError("rt_rw"),
                    errorMessage = viewModel.getFieldError("rt_rw")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Jalan",
                    placeholder = "Masukkan nama jalan",
                    value = viewModel.jalanValue,
                    onValueChange = viewModel::updateJalan,
                    isError = viewModel.hasFieldError("jalan"),
                    errorMessage = viewModel.getFieldError("jalan")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Desa/Kelurahan",
            placeholder = "Masukkan desa/kelurahan",
            value = viewModel.desaValue,
            onValueChange = viewModel::updateDesa,
            isError = viewModel.hasFieldError("desa"),
            errorMessage = viewModel.getFieldError("desa")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Kecamatan",
                    placeholder = "Masukkan kecamatan",
                    value = viewModel.kecamatanValue,
                    onValueChange = viewModel::updateKecamatan,
                    isError = viewModel.hasFieldError("kecamatan"),
                    errorMessage = viewModel.getFieldError("kecamatan")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Kabupaten/Kota",
                    placeholder = "Masukkan kabupaten/kota",
                    value = viewModel.kabupatenValue,
                    onValueChange = viewModel::updateKabupaten,
                    isError = viewModel.hasFieldError("kabupaten"),
                    errorMessage = viewModel.getFieldError("kabupaten")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIB (Nomor Identitas Bidang)",
            placeholder = "Masukkan NIB",
            value = viewModel.nibValue,
            onValueChange = viewModel::updateNib,
            isError = viewModel.hasFieldError("nib"),
            errorMessage = viewModel.getFieldError("nib"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Luas Tanah (m²)",
                    placeholder = "Masukkan luas tanah",
                    value = viewModel.luasTanahValue,
                    onValueChange = viewModel::updateLuasTanah,
                    isError = viewModel.hasFieldError("luas_tanah"),
                    errorMessage = viewModel.getFieldError("luas_tanah"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Status Tanah",
                    placeholder = "Masukkan status tanah",
                    value = viewModel.statusTanahValue,
                    onValueChange = viewModel::updateStatusTanah,
                    isError = viewModel.hasFieldError("status_tanah"),
                    errorMessage = viewModel.getFieldError("status_tanah")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Dipergunakan untuk",
            placeholder = "Masukkan pergunaan tanah",
            value = viewModel.diperggunakanValue,
            onValueChange = viewModel::updateDipergunakan,
            isError = viewModel.hasFieldError("dipergunakan"),
            errorMessage = viewModel.getFieldError("dipergunakan")
        )
    }
}
