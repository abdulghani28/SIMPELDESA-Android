package com.cvindosistem.simpeldesa.core.helpers

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun RemoteImage(
    fileId: String,
    imageLoader: ImageLoader,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
    placeholder: @Composable () -> Unit = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        }
    },
    error: @Composable () -> Unit = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Error loading image",
                tint = Color.Gray
            )
        }
    }
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(fileId) {
        isLoading = true
        isError = false
        imageBitmap = null

        try {
            imageBitmap = imageLoader.loadImage(fileId)
            isError = imageBitmap == null
        } catch (e: Exception) {
            Log.e("RemoteImage", "Error loading image", e)
            isError = true
        } finally {
            isLoading = false
        }
    }

    Box(modifier = modifier) {
        when {
            isLoading -> placeholder()
            isError -> error()
            else -> imageBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    alignment = alignment,
                    colorFilter = colorFilter
                )
            }
        }
    }
}