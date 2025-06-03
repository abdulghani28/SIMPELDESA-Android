package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.domain.model.SuratKematianResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKematianUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SKKematianFormSubmitter(
    private val createSKKematianUseCase: CreateSuratKematianUseCase,
    private val stateManager: SKKematianStateManager,
    private val coroutineScope: CoroutineScope
) {
    var isLoading by mutableStateOf(false)
        private set

    private val _events = MutableSharedFlow<SubmitterEvent>()
    val events = _events.asSharedFlow()

    fun submitForm() {
        coroutineScope.launch {
            isLoading = true
            try {
                val request = stateManager.toRequest()

                when (val result = createSKKematianUseCase(request)) {
                    is SuratKematianResult.Success -> {
                        _events.emit(SubmitterEvent.SubmitSuccess)
                    }
                    is SuratKematianResult.Error -> {
                        _events.emit(SubmitterEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                val message = e.message ?: "Terjadi kesalahan"
                _events.emit(SubmitterEvent.SubmitError(message))
            } finally {
                isLoading = false
            }
        }
    }

    sealed class SubmitterEvent {
        data object SubmitSuccess : SubmitterEvent()
        data class SubmitError(val message: String) : SubmitterEvent()
    }
}