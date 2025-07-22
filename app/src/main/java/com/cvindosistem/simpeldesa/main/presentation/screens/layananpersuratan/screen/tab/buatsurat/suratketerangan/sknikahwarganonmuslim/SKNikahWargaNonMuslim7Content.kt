package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sknikahwarganonmuslim

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel

@Composable
internal fun SKNikahWargaNonMuslim7Content(
    viewModel: SKNikahWargaNonMuslimViewModel,
    modifier: Modifier = Modifier
) {

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
            LegalitasPutusanSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun LegalitasPutusanSection(
    viewModel: SKNikahWargaNonMuslimViewModel
) {
    Column {
        SectionTitle("Data Legalitas & Putusan")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Putusan Pengadilan",
            placeholder = "Masukkan nomor putusan pengadilan",
            value = viewModel.nomorPutusanPengadilan,
            onValueChange = viewModel::updateNomorPutusanPengadilan,
            isError = viewModel.hasFieldError("nomor_putusan_pengadilan"),
            errorMessage = viewModel.getFieldError("nomor_putusan_pengadilan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Putusan Pengadilan",
            value = viewModel.tanggalPutusanPengadilan,
            onValueChange = viewModel::updateTanggalPutusanPengadilan,
            isError = viewModel.hasFieldError("tanggal_putusan_pengadilan"),
            errorMessage = viewModel.getFieldError("tanggal_putusan_pengadilan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Izin Perwakilan",
            placeholder = "Masukkan nomor izin perwakilan",
            value = viewModel.nomorIzinPerwakilan,
            onValueChange = viewModel::updateNomorIzinPerwakilan,
            isError = viewModel.hasFieldError("nomor_izin_perwakilan"),
            errorMessage = viewModel.getFieldError("nomor_izin_perwakilan")
        )
    }
}