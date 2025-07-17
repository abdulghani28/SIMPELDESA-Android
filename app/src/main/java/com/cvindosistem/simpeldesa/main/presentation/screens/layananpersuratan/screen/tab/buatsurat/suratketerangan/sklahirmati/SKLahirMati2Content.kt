package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sklahirmati

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati.SKLahirMatiViewModel

@Composable
internal fun SKLahirMati2Content(
    viewModel: SKLahirMatiViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Pelapor", "Informasi Ibu", "Keterangan Lahir Mati"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiIbu(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiIbu(
    viewModel: SKLahirMatiViewModel
) {
    Column {
        SectionTitle("Informasi Ibu")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK Ibu",
            value = viewModel.nikIbuValue,
            onValueChange = viewModel::updateNikIbu,
            isError = viewModel.hasFieldError("nik_ibu"),
            errorMessage = viewModel.getFieldError("nik_ibu"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap Ibu",
            placeholder = "Masukkan nama lengkap ibu",
            value = viewModel.namaIbuValue,
            onValueChange = viewModel::updateNamaIbu,
            isError = viewModel.hasFieldError("nama_ibu"),
            errorMessage = viewModel.getFieldError("nama_ibu")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir Ibu",
                    placeholder = "Masukkan tempat lahir ibu",
                    value = viewModel.tempatLahirIbuValue,
                    onValueChange = viewModel::updateTempatLahirIbu,
                    isError = viewModel.hasFieldError("tempat_lahir_ibu"),
                    errorMessage = viewModel.getFieldError("tempat_lahir_ibu")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir Ibu",
                    value = viewModel.tanggalLahirIbuValue,
                    onValueChange = viewModel::updateTanggalLahirIbu,
                    isError = viewModel.hasFieldError("tanggal_lahir_ibu"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir_ibu")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama Ibu",
            value = viewModel.agamaList.find { it.id == viewModel.agamaIbuIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgamaIbuId(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_ibu_id"),
            errorMessage = viewModel.getFieldError("agama_ibu_id"),
            onDropdownExpanded = viewModel::loadAgama
        )

        Spacer(modifier = Modifier.height(16.dp))

        KewarganegaraanSection(
            selectedKewarganegaraan = viewModel.kewarganegaraanIbuIdValue,
            onSelectedKewarganegaraan = viewModel::updateKewarganegaraanIbuId,
            isError = viewModel.hasFieldError("kewarganegaraan_ibu_id"),
            errorMessage = viewModel.getFieldError("kewarganegaraan_ibu_id")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan Ibu",
            placeholder = "Masukkan pekerjaan ibu",
            value = viewModel.pekerjaanIbuValue,
            onValueChange = viewModel::updatePekerjaanIbu,
            isError = viewModel.hasFieldError("pekerjaan_ibu"),
            errorMessage = viewModel.getFieldError("pekerjaan_ibu")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap Ibu",
            placeholder = "Masukkan alamat lengkap ibu",
            value = viewModel.alamatIbuValue,
            onValueChange = viewModel::updateAlamatIbu,
            isError = viewModel.hasFieldError("alamat_ibu"),
            errorMessage = viewModel.getFieldError("alamat_ibu")
        )
    }
}
