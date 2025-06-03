package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SKKematianStepManager(
    private val validator: SKKematianValidator,
    private val coroutineScope: CoroutineScope
) {
    var currentStep by mutableIntStateOf(1)
        private set

    private val _events = MutableSharedFlow<StepEvent>()
    val events = _events.asSharedFlow()

    fun nextStep() {
        when (currentStep) {
            1 -> {
                if (validator.validateStep1()) {
                    currentStep = 2
                    emitStepChanged()
                } else {
                    emitValidationError()
                }
            }
            2 -> {
                if (validator.validateStep2()) {
                    currentStep = 3
                    emitStepChanged()
                } else {
                    emitValidationError()
                }
            }
            3 -> {
                if (validator.validateStep3()) {
                    coroutineScope.launch {
                        _events.emit(StepEvent.ReadyToSubmit)
                    }
                } else {
                    emitValidationError()
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            currentStep -= 1
            emitStepChanged()
        }
    }

    fun resetToFirstStep() {
        currentStep = 1
    }

    private fun emitStepChanged() {
        coroutineScope.launch {
            _events.emit(StepEvent.StepChanged(currentStep))
        }
    }

    private fun emitValidationError() {
        coroutineScope.launch {
            _events.emit(StepEvent.ValidationError)
        }
    }

    sealed class StepEvent {
        data class StepChanged(val step: Int) : StepEvent()
        data object ValidationError : StepEvent()
        data object ReadyToSubmit : StepEvent()
    }
}