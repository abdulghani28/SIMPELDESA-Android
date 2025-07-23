package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skjualbeli

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli.SKJualBeliViewModel

@Composable
internal fun SKJualBeli3Content(
    viewModel: SKJualBeliViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Penjual", "Informasi Pembeli", "Informasi Barang"),
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
            InformasiBarang(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun InformasiBarang(
    viewModel: SKJualBeliViewModel
) {
    Column {
        SectionTitle("Informasi Barang")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jenis Barang",
            placeholder = "Masukkan jenis barang",
            value = viewModel.jenisBarangValue,
            onValueChange = viewModel::updateJenisBarang,
            isError = viewModel.hasFieldError("jenis_barang"),
            errorMessage = viewModel.getFieldError("jenis_barang")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Rincian Barang",
            placeholder = "Masukkan rincian barang",
            value = viewModel.rincianBarangValue,
            onValueChange = viewModel::updateRincianBarang,
            isError = viewModel.hasFieldError("rincian_barang"),
            errorMessage = viewModel.getFieldError("rincian_barang")
        )
    }
}
