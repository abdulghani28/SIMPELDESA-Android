package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkematian

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
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian.SKKematianViewModel

@Composable
internal fun SKKematian2Content(
    viewModel: SKKematianViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Mendiang", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiMendiang(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiMendiang(
    viewModel: SKKematianViewModel
) {
    Column {
        SectionTitle("Informasi Mendiang")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikMendiangValue,
            onValueChange = viewModel::updateNikMendiang,
            isError = viewModel.hasFieldError("nik_mendiang"),
            errorMessage = viewModel.getFieldError("nik_mendiang"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaMendiangValue,
            onValueChange = viewModel::updateNamaMendiang,
            isError = viewModel.hasFieldError("nama_mendiang"),
            errorMessage = viewModel.getFieldError("nama_mendiang")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Mendiang",
                    value = viewModel.tempatLahirMendiangValue,
                    onValueChange = viewModel::updateTempatLahirMendiang,
                    isError = viewModel.hasFieldError("tempat_lahir_mendiang"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_mendiang"),
                    placeholder = "Isi tempat lahir mendiang",
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Mendiang",
                    value = viewModel.tanggalLahirMendiangValue,
                    onValueChange = viewModel::updateTanggalLahirMendiang,
                    isError = viewModel.hasFieldError("tanggal_lahir_mendiang"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_mendiang")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.jenisKelaminMendiangValue,
            onGenderSelected = viewModel::updateJenisKelaminMendiang,
            isError = viewModel.hasFieldError("jenis_kelamin_mendiang"),
            errorMessage = viewModel.getFieldError("jenis_Kelamin_mendiang")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatMendiangValue,
            onValueChange = viewModel::updateAlamatMendiang,
            isError = viewModel.hasFieldError("alamat_mendiang"),
            errorMessage = viewModel.getFieldError("alamat_mendiang")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DropdownField(
                    label = "Hari Meninggal",
                    value = viewModel.hariMeninggalValue,
                    onValueChange = viewModel::updateHariMeninggal,
                    options = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"),
                    isError = viewModel.hasFieldError("hari_meninggal"),
                    errorMessage = viewModel.getFieldError("hari_meninggal")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Meninggal",
                    value = viewModel.tanggalMeninggalValue,
                    onValueChange = viewModel::updateTanggalMeninggal,
                    isError = viewModel.hasFieldError("tanggal_meninggal"),
                    errorMessage = viewModel.getFieldError("tanggal_meninggal")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Meninggal",
            placeholder = "Tempat meninggal",
            value = viewModel.tempatMeninggalValue,
            onValueChange = viewModel::updateTempatMeninggal,
            isError = viewModel.hasFieldError("tempat_meninggal"),
            errorMessage = viewModel.getFieldError("tempat_meninggal")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Sebab Meninggal",
            placeholder = "Sebab meninggal",
            value = viewModel.sebabMeninggalValue,
            onValueChange = viewModel::updateSebabMeninggal,
            isError = viewModel.hasFieldError("sebab_meninggal"),
            errorMessage = viewModel.getFieldError("sebab_meninggal")
        )
    }
}