package com.cvindosistem.simpeldesa.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.core.domain.model.FileUploadResult
import com.cvindosistem.simpeldesa.core.domain.usecases.UploadFileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

/**
 * ViewModel untuk mengelola proses unggah (upload) file.
 * Menggunakan [UploadFileUseCase] untuk mengirim file ke server.
 * Menyediakan state reaktif via StateFlow untuk diamati oleh UI.
 */
class FileUploadViewModel(
    private val uploadFileUseCase: UploadFileUseCase
) : ViewModel() {

    // --- State Internal ---

    private val _state = MutableStateFlow(FileUploadState())

    /**
     * State publik yang dapat diamati oleh UI, mencerminkan status proses unggah:
     * - [isLoading]: sedang mengunggah atau tidak
     * - [isSuccess]: apakah unggahan berhasil
     * - [fileId]: ID file yang berhasil diunggah
     * - [createdAt]: waktu pembuatan file di server
     * - [error]: pesan kesalahan jika terjadi error
     */
    val state = _state.asStateFlow()

    // --- Fungsi Utama ---

    /**
     * Mengunggah file ke server.
     * Memperbarui state sesuai dengan status proses unggah (loading, success, atau error).
     *
     * @param file File yang akan diunggah.
     * @param mimeType Tipe MIME dari file, misalnya "image/jpeg" atau "application/pdf".
     */
    fun uploadFile(file: File, mimeType: String) {
        _state.update { it.copy(isLoading = true, error = null) }
        Log.d("FileUploadViewModel", "Start uploading file: ${file.name}, type: $mimeType")

        viewModelScope.launch {
            when (val result = uploadFileUseCase(file, mimeType)) {
                is FileUploadResult.Success -> {
                    val data = result.data.data
                    Log.d("FileUploadViewModel", "Upload success. ID: ${data.id}, createdAt: ${data.created_at}")

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            fileId = data.id,
                            createdAt = data.created_at,
                            error = null
                        )
                    }
                }
                is FileUploadResult.Error -> {
                    Log.e("FileUploadViewModel", "Upload failed: ${result.message}")

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    /**
     * Mereset state ke nilai awal. Biasanya dipanggil setelah unggahan selesai atau dibatalkan.
     */
    fun resetState() {
        _state.update { FileUploadState() }
    }

    // --- Struktur Data State ---

    /**
     * Menyimpan status unggahan file.
     *
     * @property isLoading Apakah sedang dalam proses unggah.
     * @property isSuccess Apakah unggahan berhasil.
     * @property fileId ID file yang diunggah, jika berhasil.
     * @property createdAt Timestamp pembuatan file di server, jika berhasil.
     * @property error Pesan kesalahan jika unggahan gagal.
     */
    data class FileUploadState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val fileId: String? = null,
        val createdAt: String? = null,
        val error: String? = null
    )
}