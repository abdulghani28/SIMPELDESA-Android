package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkepemilikantanah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah.SKKepemilikanTanahViewModel

@Composable
internal fun SKKepemilikanTanah3Content(
    viewModel: SKKepemilikanTanahViewModel,
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemohon", "Informasi Tanah", "Bukti Kepemilikan", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            BuktiKepemilikanSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun BuktiKepemilikanSection(
    viewModel: SKKepemilikanTanahViewModel
) {
    Column {
        SectionTitle("Bukti Kepemilikan Tanah")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Atas Nama",
            placeholder = "Masukkan nama pemilik dalam dokumen",
            value = viewModel.atasNamaValue,
            onValueChange = viewModel::updateAtasNama,
            isError = viewModel.hasFieldError("atas_nama"),
            errorMessage = viewModel.getFieldError("atas_nama")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Asal Kepemilikan Tanah",
            value = viewModel.asalKepemilikanTanahValue,
            onValueChange = viewModel::updateAsalKepemilikanTanah,
            options = listOf(
                "Warisan",
                "Jual Beli",
                "Hibah",
                "Tukar Menukar",
                "Pemberian Negara",
                "Lainnya"
            ),
            isError = viewModel.hasFieldError("asal_kepemilikan_tanah"),
            errorMessage = viewModel.getFieldError("asal_kepemilikan_tanah")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Jenis Bukti Kepemilikan",
            value = viewModel.buktiKepemilikanTanahValue,
            onValueChange = viewModel::updateBuktiKepemilikanTanah,
            options = listOf(
                "Sertifikat Hak Milik (SHM)",
                "Sertifikat Hak Guna Bangunan (SHGB)",
                "Sertifikat Hak Pakai (SHP)",
                "Akta Jual Beli (AJB)",
                "Girik/Letter C",
                "Surat Keterangan Tanah",
                "Lainnya"
            ),
            isError = viewModel.hasFieldError("bukti_kepemilikan_tanah"),
            errorMessage = viewModel.getFieldError("bukti_kepemilikan_tanah")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Detail Bukti Kepemilikan",
            placeholder = "Masukkan detail tambahan bukti kepemilikan",
            value = viewModel.buktiKepemilikanTanahTanahValue,
            onValueChange = viewModel::updateBuktiKepemilikanTanahTanah,
            isError = viewModel.hasFieldError("bukti_kepemilikan_tanah_tanah"),
            errorMessage = viewModel.getFieldError("bukti_kepemilikan_tanah_tanah")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Bukti Kepemilikan",
            placeholder = "Masukkan nomor dokumen bukti kepemilikan",
            value = viewModel.nomorBuktiKepemilikanValue,
            onValueChange = viewModel::updateNomorBuktiKepemilikan,
            isError = viewModel.hasFieldError("nomor_bukti_kepemilikan"),
            errorMessage = viewModel.getFieldError("nomor_bukti_kepemilikan")
        )
    }
}