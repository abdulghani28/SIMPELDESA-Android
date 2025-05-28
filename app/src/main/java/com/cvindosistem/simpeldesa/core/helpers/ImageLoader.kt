package com.cvindosistem.simpeldesa.core.helpers

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.cvindosistem.simpeldesa.core.domain.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageLoader(private val fileRepository: FileRepository) {
    private val iconCache = mutableMapOf<String, ImageBitmap>()

    suspend fun loadImage(fileId: String): ImageBitmap? = withContext(Dispatchers.IO) {
        // Check cache first
        iconCache[fileId]?.let { return@withContext it }

        // Load from repository
        fileRepository.getFile(fileId).fold(
            onSuccess = { bytes ->
                try {
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    val imageBitmap = bitmap.asImageBitmap()
                    iconCache[fileId] = imageBitmap
                    return@withContext imageBitmap
                } catch (e: Exception) {
                    Log.e("ImageLoader", "Failed to decode image", e)
                    return@withContext null
                }
            },
            onFailure = {
                Log.e("ImageLoader", "Failed to load image: ${it.message}")
                return@withContext null
            }
        )
    }

    fun clearCache() {
        iconCache.clear()
    }

    fun removeFromCache(fileId: String) {
        iconCache.remove(fileId)
    }
}