package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skizinorangtua

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
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua.SKIzinOrangTuaViewModel

@Composable
internal fun SKIzinOrangTua3Content(
    viewModel: SKIzinOrangTuaViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Pemberi Izin", "Yang Diberi Izin", "Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPelengkapSection(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPelengkapSection(
    viewModel: SKIzinOrangTuaViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Pelengkap")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Perusahaan",
            placeholder = "Masukkan nama perusahaan",
            value = viewModel.namaPerusahaanValue,
            onValueChange = viewModel::updateNamaPerusahaan,
            isError = viewModel.hasFieldError("nama_perusahaan"),
            errorMessage = viewModel.getFieldError("nama_perusahaan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Negara Tujuan",
            placeholder = "Masukkan negara tujuan",
            value = viewModel.negaraTujuanValue,
            onValueChange = viewModel::updateNegaraTujuan,
            isError = viewModel.hasFieldError("negara_tujuan"),
            errorMessage = viewModel.getFieldError("negara_tujuan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Masa Kontrak",
            placeholder = "Masukkan masa kontrak",
            value = viewModel.masaKontrakValue,
            onValueChange = viewModel::updateMasaKontrak,
            isError = viewModel.hasFieldError("masa_kontrak"),
            errorMessage = viewModel.getFieldError("masa_kontrak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan")
        )
    }
}
