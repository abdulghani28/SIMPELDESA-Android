package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.warga

import android.annotation.SuppressLint
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
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.usaha.SKUsahaViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsahaWargaDesa2Content(
    viewModel: SKUsahaViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Usaha", "Informasi Pelengkap"),
                currentStep = viewModel.getCurrentStepForUI()
            )
        }

        item {
            InformasiUsaha(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiUsaha(
    viewModel: SKUsahaViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Usaha")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Usaha",
            placeholder = "Masukkan nama usaha",
            value = viewModel.wargaNamaUsahaValue,
            onValueChange = { viewModel.updateWargaNamaUsaha(it) },
            isError = validationErrors.containsKey("nama_usaha"),
            errorMessage = validationErrors["nama_usaha"],
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Jenis Usaha",
            value = viewModel.jenisUsahaList.find { it.id == viewModel.wargaJenisUsahaValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.jenisUsahaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateWargaJenisUsaha(it.id) }
            },
            options = viewModel.jenisUsahaList.map { it.nama },
            isError = viewModel.hasFieldError("jenis_usaha"),
            errorMessage = viewModel.getFieldError("jenis_usaha"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Bidang Usaha",
            value = viewModel.bidangUsahaList.find { it.id == viewModel.wargaBidangUsahaValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.bidangUsahaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateWargaBidangUsaha(it.id) }
            },
            options = viewModel.bidangUsahaList.map { it.nama },
            isError = viewModel.hasFieldError("bidang_usaha"),
            errorMessage = viewModel.getFieldError("bidang_usaha"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Pajak Wajib Pajak (NPWP)",
            placeholder = "Masukkan NPWP",
            value = viewModel.wargaNpwpValue,
            onValueChange = { viewModel.updateWargaNpwp(it) },
            isError = validationErrors.containsKey("npwp"),
            errorMessage = validationErrors["npwp"],
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.wargaAlamatUsahaValue,
            onValueChange = { viewModel.updateWargaAlamatUsaha(it) },
            isError = validationErrors.containsKey("alamat_usaha"),
            errorMessage = validationErrors["alamat_usaha"],
        )
    }
}