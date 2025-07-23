package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbedaidentitas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.InformationDivider
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas.SKBedaIdentitasViewModel

@Composable
internal fun SKBedaIdentitas1Content(
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
            UseMyDataCheckbox(
                checked = viewModel.useMyDataChecked,
                onCheckedChange = viewModel::updateUseMyData,
                isLoading = viewModel.isLoadingUserData
            )
        }

        item {
            InformasiPerbedaanIdentitas(
                viewModel = viewModel
            )
        }
    }
}


@Composable
private fun InformasiPerbedaanIdentitas(
    viewModel: SKBedaIdentitasViewModel
) {
    Column {
        SectionTitle("Informasi Perbedaan Identitas")

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Perbedaan Data yang Akan Dilaporkan",
            value = viewModel.perbedaanIdentitasList.find { it.id == viewModel.perbedaanIdValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.perbedaanIdentitasList.find { it.nama == selectedNama }
                selected?.let { viewModel.updatePerbedaanId(it.id) }
            },
            options = viewModel.perbedaanIdentitasList.map { it.nama },
            isError = viewModel.hasFieldError("perbedaan_id"),
            errorMessage = viewModel.getFieldError("perbedaan_id")
        )

        Spacer(modifier = Modifier.height(16.dp))

        InformationDivider("Identitas 1 (identitas sebenarnya)")

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Tercantum Dalam",
            value = viewModel.tercantumIdentitasList.find { it.id == viewModel.tercantumId1Value }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.tercantumIdentitasList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateTercantumId1(it.id) }
            },
            options = viewModel.tercantumIdentitasList.map { it.nama },
            isError = viewModel.hasFieldError("tercantum_id"),
            errorMessage = viewModel.getFieldError("tercantum_id")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor",
            placeholder = "XXXX XXXX XXXX",
            value = viewModel.nomor1Value,
            onValueChange = viewModel::updateNomor1,
            isError = viewModel.hasFieldError("nomor_1"),
            errorMessage = viewModel.getFieldError("nomor_1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.nama1Value,
            onValueChange = viewModel::updateNama1,
            isError = viewModel.hasFieldError("nama_1"),
            errorMessage = viewModel.getFieldError("nama_1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Tempat Lahir",
            placeholder = "Masukkan tempat lahir",
            value = viewModel.tempatLahir1Value,
            onValueChange = viewModel::updateTempatLahir1,
            isError = viewModel.hasFieldError("tempat_lahir_1"),
            errorMessage = viewModel.getFieldError("tempat_lahir_1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(
            label = "Tanggal Lahir",
            value = viewModel.tanggalLahir1Value,
            onValueChange = viewModel::updateTanggalLahir1,
            isError = viewModel.hasFieldError("tanggal_lahir_1"),
            errorMessage = viewModel.getFieldError("tanggal_lahir_1")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamat1Value,
            onValueChange = viewModel::updateAlamat1,
            isError = viewModel.hasFieldError("alamat_1"),
            errorMessage = viewModel.getFieldError("alamat_1")
        )
    }
}