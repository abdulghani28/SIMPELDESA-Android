package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbedaidentitas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.LabelFieldText
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas.SKBedaIdentitasViewModel

@Composable
internal fun SKBedaIdentitas2Content(
    viewModel: SKBedaIdentitasViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Identitas 1", "Informasi Identitas 2", "Informasi Pelengkap"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPerbedaanIdentitas2(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiPerbedaanIdentitas2(
    viewModel: SKBedaIdentitasViewModel
) {
    Column {
        LabelFieldText("Identitas 2")

        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Tercantum Dalam",
            value = viewModel.tercantumIdentitasList.find { it.id == viewModel.tercantumId2Value }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.tercantumIdentitasList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateTercantumId2(it.id) }
            },
            options = viewModel.tercantumIdentitasList.map { it.nama },
            isError = viewModel.hasFieldError("tercantum_id_2"),
            errorMessage = viewModel.getFieldError("tercantum_id_2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor",
            placeholder = "XXXX XXXX XXXX",
            value = viewModel.nomor2Value,
            onValueChange = viewModel::updateNomor2,
            isError = viewModel.hasFieldError("nomor_2"),
            errorMessage = viewModel.getFieldError("nomor_2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.nama2Value,
            onValueChange = viewModel::updateNama2,
            isError = viewModel.hasFieldError("nama_2"),
            errorMessage = viewModel.getFieldError("nama_2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Lahir",
            placeholder = "Masukkan tempat lahir",
            value = viewModel.tempatLahir2Value,
            onValueChange = viewModel::updateTempatLahir2,
            isError = viewModel.hasFieldError("tempat_lahir_2"),
            errorMessage = viewModel.getFieldError("tempat_lahir_2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Lahir",
            value = viewModel.tanggalLahir2Value,
            onValueChange = viewModel::updateTanggalLahir2,
            isError = viewModel.hasFieldError("tanggal_lahir_2"),
            errorMessage = viewModel.getFieldError("tanggal_lahir_2")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamat2Value,
            onValueChange = viewModel::updateAlamat2,
            isError = viewModel.hasFieldError("alamat_2"),
            errorMessage = viewModel.getFieldError("alamat_2")
        )
    }
}
