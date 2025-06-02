package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R

@Composable
fun AppBottomBar(
    onPreviewClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    onContinueClick: (() -> Unit)? = null,
    onSubmitClick: (() -> Unit)? = null,
    continueText: String = "Lanjutkan",
    submitText: String = "Ajukan Surat",
    submitIcon: Int? = R.drawable.ic_ajukan_surat
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Preview Button (always present)
            onPreviewClick?.let { previewClick ->
                OutlinedButton(
                    onClick = previewClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = "Preview",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            // Back Button (conditional)
            onBackClick?.let { backClick ->
                OutlinedButton(
                    onClick = backClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(48.dp)
                ) {
                    Text(
                        text = "Kembali",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            // Continue Button (conditional)
            onContinueClick?.let { continueClick ->
                Button(
                    onClick = continueClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(48.dp)
                ) {
                    Text(
                        text = continueText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            // Submit Button (conditional)
            onSubmitClick?.let { submitClick ->
                Button(
                    onClick = submitClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(48.dp)
                ) {
                    submitIcon?.let { iconRes ->
                        Icon(
                            painter = painterResource(iconRes),
                            contentDescription = "Send",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = submitText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
