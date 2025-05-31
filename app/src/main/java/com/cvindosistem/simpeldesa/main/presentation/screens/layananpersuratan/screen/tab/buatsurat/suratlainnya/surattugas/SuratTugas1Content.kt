package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratlainnya.surattugas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.SuratTugasViewModel


@Composable
internal fun SuratTugas1Content(
    viewModel: SuratTugasViewModel,
    modifier: Modifier = Modifier
) {
    val validationErrors by viewModel.validationErrors.collectAsState()

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Informasi Penerima Tugas", "Informasi Tugas"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            InformasiPenerimaTugas(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPenerimaTugas(
    viewModel: SuratTugasViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Penerima Tugas")

        Spacer(modifier = Modifier.height(16.dp))

        // Form untuk penerima tugas pertama (primary)
        PenerimaTugasForm(
            index = 0,
            nikValue = viewModel.nikValue,
            namaValue = viewModel.namaValue,
            jabatanValue = viewModel.jabatanValue,
            onNikChange = viewModel::updateNik,
            onNamaChange = viewModel::updateNama,
            onJabatanChange = viewModel::updateJabatan,
            validationErrors = validationErrors,
            viewModel = viewModel,
            showRemoveButton = false
        )

        // Form untuk penerima tugas tambahan
        viewModel.additionalRecipients.forEachIndexed { index, recipient ->
            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            PenerimaTugasForm(
                index = index + 1,
                nikValue = recipient.nik,
                namaValue = recipient.nama,
                jabatanValue = recipient.jabatan,
                onNikChange = { viewModel.updateAdditionalRecipientNik(index, it) },
                onNamaChange = { viewModel.updateAdditionalRecipientNama(index, it) },
                onJabatanChange = { viewModel.updateAdditionalRecipientJabatan(index, it) },
                validationErrors = validationErrors,
                viewModel = viewModel,
                showRemoveButton = true,
                onRemove = { viewModel.removeAdditionalRecipient(index) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol tambah penerima tugas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.addAdditionalRecipient() }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Tambah penerima tugas",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Tambah Penerima Tugas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun PenerimaTugasForm(
    index: Int,
    nikValue: String,
    namaValue: String,
    jabatanValue: String,
    onNikChange: (String) -> Unit,
    onNamaChange: (String) -> Unit,
    onJabatanChange: (String) -> Unit,
    validationErrors: Map<String, String>,
    viewModel: SuratTugasViewModel,
    showRemoveButton: Boolean,
    onRemove: (() -> Unit)? = null
) {
    Column {
        // Header dengan nomor urut dan tombol hapus
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleMediumText("Penerima Tugas ${index + 1}")

            if (showRemoveButton && onRemove != null) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Hapus penerima tugas",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = nikValue,
            onValueChange = onNikChange,
            isError = viewModel.hasFieldError(if (index == 0) "nik" else "nik_$index"),
            errorMessage = viewModel.getFieldError(if (index == 0) "nik" else "nik_$index"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = namaValue,
            onValueChange = onNamaChange,
            isError = viewModel.hasFieldError(if (index == 0) "nama" else "nama_$index"),
            errorMessage = viewModel.getFieldError(if (index == 0) "nama" else "nama_$index")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Jabatan",
            placeholder = "Masukkan jabatan",
            value = jabatanValue,
            onValueChange = onJabatanChange,
            isError = viewModel.hasFieldError(if (index == 0) "jabatan" else "jabatan_$index"),
            errorMessage = viewModel.getFieldError(if (index == 0) "jabatan" else "jabatan_$index")
        )
    }
}