package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun GenderSelection(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
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
                    selected = selectedGender == "L",
                    onClick = { onGenderSelected("L") },
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
                    selected = selectedGender == "P",
                    onClick = { onGenderSelected("P") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                BodyMediumText("Perempuan")
            }
        }

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
internal fun StatusPerkawinanSelection(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
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

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
internal fun DasarPengajuanSelection(
    selectedDasarPengajuan: String,
    onDasarPengajuanSelected: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
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

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
internal fun PenyebabStatusSelection(
    selectedPenyebabStatus: String,
    onPenyebabStatusSelected: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
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

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
internal fun KewarganegaraanSection(
    selectedKewarganegaraan: String,
    onSelectedKewarganegaraan: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column {
        SectionTitle("Kewarganegaraan")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedKewarganegaraan == "wni",
                onClick = { onSelectedKewarganegaraan("wni") },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )
            BodyMediumText("Warga Negara Indonesia (WNI)")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedKewarganegaraan == "wna",
                onClick = { onSelectedKewarganegaraan("wna") },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )
            BodyMediumText("Warga Negara Asing (WNA)")
        }

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}