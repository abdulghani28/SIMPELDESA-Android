package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R

@Composable
fun TagsInputSection(
    tags: SnapshotStateList<String>,
    currentInput: String,
    onInputChange: (String) -> Unit
) {
    Column {
        LabelFieldText("Tambahkan Tag (opsional)")

        // Tags display
        if (tags.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(tags) { tag ->
                    TagChip(
                        text = tag,
                        onRemove = { tags.remove(tag) }
                    )
                }
            }
        }

        // Input field
        AppOutlinedTextField(
            value = currentInput,
            onValueChange = { newValue ->
                if (newValue.endsWith(" ") && newValue.trim().isNotEmpty()) {
                    val newTag = newValue.trim()
                    if (newTag.isNotEmpty() && !tags.contains(newTag)) {
                        tags.add(newTag)
                    }
                    onInputChange("")
                } else {
                    onInputChange(newValue)
                }
            },
            isError = false,
            placeholder = "Ketik tag dan tekan spasi untuk menambahkan"
        )
    }
}

@Composable
private fun TagChip(
    text: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                Color(0xFFE8F0FE),
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF1565C0)
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close), // Replace with close icon
                contentDescription = "Remove tag",
                modifier = Modifier.size(12.dp),
                tint = Color.Unspecified
            )
        }
    }
}