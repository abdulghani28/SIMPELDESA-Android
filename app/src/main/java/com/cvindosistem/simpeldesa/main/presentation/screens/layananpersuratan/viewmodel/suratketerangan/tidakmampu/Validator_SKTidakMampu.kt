package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmampu

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKTidakMampuValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SKTidakMampuStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (stateManager.namaValue.isBlank()) {
            errors["nama"] = "Nama lengkap tidak boleh kosong"
        }

        if (stateManager.tempatLahirValue.isBlank()) {
            errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        }

        if (stateManager.tanggalLahirValue.isBlank()) {
            errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        }

        if (stateManager.selectedGender.isBlank()) {
            errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        }

        if (stateManager.pekerjaanValue.isBlank()) {
            errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        if (stateManager.alamatValue.isBlank()) {
            errors["alamat"] = "Alamat tidak boleh kosong"
        }

        if (stateManager.agamaValue.isBlank()) {
            errors["agama_id"] = "Agama tidak boleh kosong"
        }

        if (stateManager.statusKawinValue.isBlank()) {
            errors["status_kawin_id"] = "Status kawin tidak boleh kosong"
        }

        if (stateManager.keperluanValue.isBlank()) {
            errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SKTidakMampuStateManager): Boolean {
        return validateStep1(stateManager)
    }

    fun clearFieldError(fieldName: String) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove(fieldName)
        _validationErrors.value = currentErrors
    }

    fun clearMultipleFieldErrors(fieldNames: List<String>) {
        val currentErrors = _validationErrors.value.toMutableMap()
        fieldNames.forEach { currentErrors.remove(it) }
        _validationErrors.value = currentErrors
    }

    fun getFieldError(fieldName: String): String? = _validationErrors.value[fieldName]
    fun hasFieldError(fieldName: String): Boolean = _validationErrors.value.containsKey(fieldName)

    fun clearAllErrors() {
        _validationErrors.value = emptyMap()
    }
}