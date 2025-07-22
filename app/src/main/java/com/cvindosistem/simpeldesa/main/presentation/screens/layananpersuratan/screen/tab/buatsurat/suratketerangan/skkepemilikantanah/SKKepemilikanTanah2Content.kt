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
internal fun SKKepemilikanTanah2Content(
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
            InformasiTanahSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiTanahSection(
    viewModel: SKKepemilikanTanahViewModel
) {
    Column {
        SectionTitle("Informasi Tanah")
        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat/Lokasi Tanah",
            placeholder = "Masukkan alamat lengkap lokasi tanah",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = { Text("m²", style = MaterialTheme.typography.bodyMedium) }
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Jenis Tanah",
                    placeholder = "Contoh: Sawah, Ladang, Pekarangan",
                    value = viewModel.jenisTanahValue,
                    onValueChange = viewModel::updateJenisTanah,
                    isError = viewModel.hasFieldError("jenis_tanah"),
                    errorMessage = viewModel.getFieldError("jenis_tanah")
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Batas-Batas Tanah")
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Batas Utara",
            placeholder = "Masukkan batas sebelah utara",
            value = viewModel.batasUtaraValue,
            onValueChange = viewModel::updateBatasUtara,
            isError = viewModel.hasFieldError("batas_utara"),
            errorMessage = viewModel.getFieldError("batas_utara")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Batas Timur",
            placeholder = "Masukkan batas sebelah timur",
            value = viewModel.batasTimurValue,
            onValueChange = viewModel::updateBatasTimur,
            isError = viewModel.hasFieldError("batas_timur"),
            errorMessage = viewModel.getFieldError("batas_timur")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Batas Selatan",
            placeholder = "Masukkan batas sebelah selatan",
            value = viewModel.batasSelatanValue,
            onValueChange = viewModel::updateBatasSelatan,
            isError = viewModel.hasFieldError("batas_selatan"),
            errorMessage = viewModel.getFieldError("batas_selatan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Batas Barat",
            placeholder = "Masukkan batas sebelah barat",
            value = viewModel.batasBaratValue,
            onValueChange = viewModel::updateBatasBarat,
            isError = viewModel.hasFieldError("batas_barat"),
            errorMessage = viewModel.getFieldError("batas_barat")
        )
    }
}