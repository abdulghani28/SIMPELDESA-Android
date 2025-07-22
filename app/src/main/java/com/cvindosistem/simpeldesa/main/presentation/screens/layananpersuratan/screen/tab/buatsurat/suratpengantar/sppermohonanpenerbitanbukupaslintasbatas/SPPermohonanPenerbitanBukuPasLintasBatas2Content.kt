package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppermohonanpenerbitanbukupaslintasbatas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku.SPPermohonanPenerbitanBukuPasLintasBatasViewModel

@Composable
internal fun SPPermohonanPenerbitanBukuPasLintasBatas2Content(
    viewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemohon", "Alamat & Keluarga", "Anggota Keluarga", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            AlamatKeluargaSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun AlamatKeluargaSection(
    viewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
) {
    Column {
        SectionTitle("Alamat & Keluarga")

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Kartu Keluarga (KK)",
            placeholder = "Masukkan nomor KK",
            value = viewModel.noKkValue,
            onValueChange = viewModel::updateNoKk,
            isError = viewModel.hasFieldError("no_kk"),
            errorMessage = viewModel.getFieldError("no_kk"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Kepala Keluarga",
            placeholder = "Masukkan nama kepala keluarga",
            value = viewModel.kepalaKeluargaValue,
            onValueChange = viewModel::updateKepalaKeluarga,
            isError = viewModel.hasFieldError("kepala_keluarga"),
            errorMessage = viewModel.getFieldError("kepala_keluarga")
        )
    }
}
