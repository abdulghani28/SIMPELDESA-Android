package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ghaib

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKGhaibValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SKGhaibStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (stateManager.namaValue.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (stateManager.hubunganIdValue.isBlank()) errors["hubungan_id"] = "Hubungan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(stateManager: SKGhaibStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.namaOrangHilangValue.isBlank()) errors["nama_orang_hilang"] = "Nama orang hilang tidak boleh kosong"
        if (stateManager.jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
        if (stateManager.usiaValue <= 0) errors["usia"] = "Usia harus lebih dari 0"
        if (stateManager.alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (stateManager.hilangSejakValue.isBlank()) errors["hilang_sejak"] = "Tanggal hilang tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SKGhaibStateManager): Boolean {
        return validateStep1(stateManager) && validateStep2(stateManager)
    }

    fun clearFieldError(fieldName: String) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove(fieldName)
        _validationErrors.value = currentErrors
    }

    fun clearMultipleFieldErrors(fieldNames: List<String>) {
        val currentErrors = _validationErrors.value.toMutableMap()
        fieldNames.forEach { fieldName ->
            currentErrors.remove(fieldName)
        }
        _validationErrors.value = currentErrors
    }

    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
    }

    fun clearAllErrors() {
        _validationErrors.value = emptyMap()
    }
}
