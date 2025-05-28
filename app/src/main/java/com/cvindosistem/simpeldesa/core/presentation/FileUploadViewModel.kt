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

class FileUploadViewModel(
    private val uploadFileUseCase: UploadFileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FileUploadState())
    val state = _state.asStateFlow()

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

    fun resetState() {
        _state.update { FileUploadState() }
    }

    data class FileUploadState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val fileId: String? = null,
        val createdAt: String? = null,
        val error: String? = null
    )
}