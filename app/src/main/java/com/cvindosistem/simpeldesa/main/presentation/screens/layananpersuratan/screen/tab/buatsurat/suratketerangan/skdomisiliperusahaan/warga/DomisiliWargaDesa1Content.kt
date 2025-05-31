package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skdomisiliperusahaan.warga

import android.annotation.SuppressLint
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliPerusahaanViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DomisiliPerusahaanWargaDesa1Content(
    viewModel: SKDomisiliPerusahaanViewModel,
    modifier: Modifier = Modifier
) {
    // Observing validation errors
    val validationErrors by viewModel.validationErrors.collectAsState()
    val isLoadingUserData by remember { derivedStateOf { viewModel.isLoadingUserData } }
    val useMyDataChecked by remember { derivedStateOf { viewModel.useMyDataChecked } }

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Perusahaan", "Informasi Pelengkap"),
                currentStep = viewModel.getCurrentStepForUI()
            )
        }

        item {
            UseMyDataCheckbox(
                checked = useMyDataChecked,
                onCheckedChange = { viewModel.updateUseMyData(it) },
                isLoading = isLoadingUserData
            )
        }

        item {
            InformasiPelapor(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPelapor(
    viewModel: SKDomisiliPerusahaanViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Pelapor")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.wargaNikValue,
            onValueChange = { viewModel.updateWargaNik(it) },
            isError = validationErrors.containsKey("nik"),
            errorMessage = validationErrors["nik"],
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.wargaNamaValue,
            onValueChange = { viewModel.updateWargaNama(it) },
            isError = validationErrors.containsKey("nama"),
            errorMessage = validationErrors["nama"]
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Tempat lahir",
                    value = viewModel.wargaTempatLahirValue,
                    onValueChange = { viewModel.updateWargaTempatLahir(it) },
                    isError = validationErrors.containsKey("tempat_lahir"),
                    errorMessage = validationErrors["tempat_lahir"]
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.wargaTanggalLahirValue,
                    onValueChange = { viewModel.updateWargaTanggalLahir(it) },
                    isError = validationErrors.containsKey("tanggal_lahir"),
                    errorMessage = validationErrors["tanggal_lahir"]
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.wargaSelectedGender,
            onGenderSelected = { viewModel.updateWargaGender(it) },
            isError = validationErrors.containsKey("jenis_kelamin"),
            errorMessage = validationErrors["jenis_kelamin"]
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.wargaPekerjaanValue,
            onValueChange = { viewModel.updateWargaPekerjaan(it) },
            isError = validationErrors.containsKey("pekerjaan"),
            errorMessage = validationErrors["pekerjaan"]
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.wargaAlamatValue,
            onValueChange = { viewModel.updateWargaAlamat(it) },
            isError = validationErrors.containsKey("alamat"),
            errorMessage = validationErrors["alamat"]
        )
    }
}