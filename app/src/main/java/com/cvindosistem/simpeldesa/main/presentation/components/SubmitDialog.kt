package com.cvindosistem.simpeldesa.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SubmitConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onPreview: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Konfirmasi Pengajuan",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = "Apakah Anda yakin ingin mengajukan surat ini? Pastikan semua data yang dimasukkan sudah benar.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    onClick = onPreview
                ) {
                    Text("Lihat Preview")
                }
                Button(
                    onClick = onConfirm
                ) {
                    Text("Ya, Ajukan")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Batal")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}