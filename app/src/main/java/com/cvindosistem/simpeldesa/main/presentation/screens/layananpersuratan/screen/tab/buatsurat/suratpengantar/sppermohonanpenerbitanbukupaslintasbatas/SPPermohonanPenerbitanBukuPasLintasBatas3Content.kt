package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppermohonanpenerbitanbukupaslintasbatas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.StepIndicator
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPermohonanPenerbitanBukuPasLintasBatasRequest
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku.SPPermohonanPenerbitanBukuPasLintasBatasViewModel

@Composable
internal fun SPPermohonanPenerbitanBukuPasLintasBatas3Content(
    viewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
    modifier: Modifier = Modifier
) {

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            StepIndicator(
                steps = listOf("Data Pemohon", "Alamat & Keluarga", "Anggota Keluarga", "Keperluan"),
                currentStep = viewModel.currentStep
            )
        }

        item {
            AnggotaKeluargaSection(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun AnggotaKeluargaSection(
    viewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
) {
    Column {
        SectionTitle("Anggota Keluarga yang Ikut")

        Spacer(modifier = Modifier.height(16.dp))

        AnggotaKeluargaList(
            anggotaKeluargaList = viewModel.anggotaKeluargaValue,
            onAddAnggotaKeluarga = {
                viewModel.addAnggotaKeluarga(
                    SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga(
                        keterangan = "",
                        nik = ""
                    )
                )
            },
            onRemoveAnggotaKeluarga = viewModel::removeAnggotaKeluarga,
            onUpdateAnggotaKeluarga = viewModel::updateAnggotaKeluargaAt,
            isError = viewModel.hasFieldError("anggota_keluarga"),
            errorMessage = viewModel.getFieldError("anggota_keluarga")
        )
    }
}

@Composable
private fun AnggotaKeluargaList(
    anggotaKeluargaList: List<SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga>,
    onAddAnggotaKeluarga: () -> Unit,
    onRemoveAnggotaKeluarga: (Int) -> Unit,
    onUpdateAnggotaKeluarga: (Int, SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column {
        // Header dengan tombol tambah
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Daftar Anggota Keluarga",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            IconButton(
                onClick = onAddAnggotaKeluarga,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah anggota keluarga",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // List anggota keluarga
        anggotaKeluargaList.forEachIndexed { index, anggotaKeluarga ->
            AnggotaKeluargaItem(
                index = index,
                anggotaKeluarga = anggotaKeluarga,
                onUpdate = { updatedAnggota ->
                    onUpdateAnggotaKeluarga(index, updatedAnggota)
                },
                onRemove = { onRemoveAnggotaKeluarga(index) }
            )

            if (index < anggotaKeluargaList.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Error message
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Placeholder ketika list kosong
        if (anggotaKeluargaList.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Belum ada anggota keluarga yang ditambahkan",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnggotaKeluargaItem(
    index: Int,
    anggotaKeluarga: SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga,
    onUpdate: (SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F7FD)
        ),
        border = BorderStroke(1.dp, Color(0xFFDCE2FB))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header dengan nomor urut dan tombol hapus
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Anggota Keluarga ${index + 1}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Hapus anggota keluarga",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Form fields
            AppTextField(
                label = "Keterangan/Hubungan Keluarga",
                placeholder = "Contoh: Anak, Istri, Suami",
                value = anggotaKeluarga.keterangan,
                onValueChange = { newKeterangan ->
                    onUpdate(anggotaKeluarga.copy(keterangan = newKeterangan))
                },
                isError = false,
                errorMessage = null
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppTextField(
                label = "NIK Anggota Keluarga",
                placeholder = "Masukkan NIK",
                value = anggotaKeluarga.nik,
                onValueChange = { newNik ->
                    onUpdate(anggotaKeluarga.copy(nik = newNik))
                },
                isError = false,
                errorMessage = null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}