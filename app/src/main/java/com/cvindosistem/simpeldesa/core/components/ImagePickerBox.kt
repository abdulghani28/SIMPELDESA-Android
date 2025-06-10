package com.cvindosistem.simpeldesa.core.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.main.presentation.screens.blogdesa.AsyncImage

@Composable
fun ImagePickerBox(
    fotoCover: Uri?, // Replace with your actual type, e.g., Uri or String
    launcher: ManagedActivityResultLauncher<String, Uri?>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF8F7FD))
            .border(
                1.dp,
                Color(0xFFDCE2FB),
                RoundedCornerShape(8.dp)
            )
            .clickable { launcher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        if (fotoCover != null) {
            AsyncImage(
                model = fotoCover,
                contentDescription = "Foto Cover",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_image_placeholder),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "JPG, JPEG, PNG Max 2 MB",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB0B0B0)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { launcher.launch("image/*") },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF6B73FF)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF6B73FF)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Upload Foto")
                }
            }
        }
    }
}