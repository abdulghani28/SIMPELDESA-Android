package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmduplikatkelahiran

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
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatkelahiran.SPMDuplikatKelahiranViewModel

@Composable
internal fun SPMDuplikatKelahiran2Content(
    viewModel: SPMDuplikatKelahiranViewModel,
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Anak", "Informasi Orang Tua", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiAnak(viewModel = viewModel)
        }
    }
}

@Composable
private fun InformasiAnak(
    viewModel: SPMDuplikatKelahiranViewModel
) {
    Column {
        SectionTitle("Informasi Anak")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "NIK Anak",
            placeholder = "Masukkan NIK anak",
            value = viewModel.nikAnakValue,
            onValueChange = viewModel::updateNikAnak,
            isError = viewModel.hasFieldError("nik_anak"),
            errorMessage = viewModel.getFieldError("nik_anak"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Anak",
            placeholder = "Masukkan nama anak",
            value = viewModel.namaAnakValue,
            onValueChange = viewModel::updateNamaAnak,
            isError = viewModel.hasFieldError("nama_anak"),
            errorMessage = viewModel.getFieldError("nama_anak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Anak",
                    placeholder = "Masukkan tempat lahir",
                    value = viewModel.tempatLahirAnakValue,
                    onValueChange = viewModel::updateTempatLahirAnak,
                    isError = viewModel.hasFieldError("tempat_lahir_anak"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_anak")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Anak",
                    value = viewModel.tanggalLahirAnakValue,
                    onValueChange = viewModel::updateTanggalLahirAnak,
                    isError = viewModel.hasFieldError("tanggal_lahir_anak"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_anak")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jenis Kelamin",
            placeholder = "Masukkan jenis kelamin",
            value = viewModel.jenisKelaminAnakValue,
            onValueChange = viewModel::updateJenisKelaminAnak,
            isError = viewModel.hasFieldError("jenis_kelamin_anak"),
            errorMessage = viewModel.getFieldError("jenis_kelamin_anak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Agama ID",
            placeholder = "Masukkan ID agama",
            value = viewModel.agamaIdAnakValue,
            onValueChange = viewModel::updateAgamaIdAnak,
            isError = viewModel.hasFieldError("agama_id_anak"),
            errorMessage = viewModel.getFieldError("agama_id_anak")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Alamat Anak",
            placeholder = "Masukkan alamat anak",
            value = viewModel.alamatAnakValue,
            onValueChange = viewModel::updateAlamatAnak,
            isError = viewModel.hasFieldError("alamat_anak"),
            errorMessage = viewModel.getFieldError("alamat_anak")
        )
    }
}
