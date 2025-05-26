package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.LabelFieldText

@Composable
internal fun GenderSelection(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    Column {
        LabelFieldText("Jenis Kelamin")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedGender == "Laki-laki",
                    onClick = { onGenderSelected("Laki-laki") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Laki-laki")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedGender == "Perempuan",
                    onClick = { onGenderSelected("Perempuan") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Perempuan")
            }
        }
    }
}

@Composable
internal fun StatusPerkawinanSelection(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit
) {
    Column {
        LabelFieldText("Status Perkawinan")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedStatus == "Kawin",
                    onClick = { onStatusSelected("Kawin") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Kawin")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedStatus == "Belum Kawin",
                    onClick = { onStatusSelected("Belum Kawin") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Belum Kawin")
            }
        }
    }
}

@Composable
internal fun DasarPengajuanSelection(
    selectedDasarPengajuan: String,
    onDasarPengajuanSelected: (String) -> Unit
) {
    Column {
        LabelFieldText("Dasar Pengajuan")

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedDasarPengajuan == "Akta Cerai",
                    onClick = { onDasarPengajuanSelected("Akta Cerai") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Akta Cerai")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedDasarPengajuan == "Surat Keterangan Kematian",
                    onClick = { onDasarPengajuanSelected("Surat Keterangan Kematian") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Surat Keterangan Kematian")
            }
        }
    }
}

@Composable
internal fun PenyebabStatusSelection(
    selectedPenyebabStatus: String,
    onPenyebabStatusSelected: (String) -> Unit
) {
    Column {
        LabelFieldText("Penyebab Status")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedPenyebabStatus == "Cerai Hidup",
                    onClick = { onPenyebabStatusSelected("Cerai Hidup") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Cerai Hidup")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedPenyebabStatus == "Cerai Mati",
                    onClick = { onPenyebabStatusSelected("Cerai Mati") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Cerai Mati")
            }
        }
    }
}