package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.tugas

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SuratTugasValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(
        nik: String,
        nama: String,
        jabatan: String,
        additionalRecipients: List<SuratTugasStateManager.AdditionalRecipient>
    ): Boolean {
        val errors = mutableMapOf<String, String>()

        // Validate primary recipient
        if (nik.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nik.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (nama.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (jabatan.isBlank()) errors["jabatan"] = "Jabatan tidak boleh kosong"

        // Validate additional recipients
        additionalRecipients.forEachIndexed { index, recipient ->
            if (recipient.nik.isBlank()) {
                errors["nik_$index"] = "NIK tidak boleh kosong"
            } else if (recipient.nik.length != 16) {
                errors["nik_$index"] = "NIK harus 16 digit"
            }

            if (recipient.nama.isBlank()) {
                errors["nama_$index"] = "Nama tidak boleh kosong"
            }

            if (recipient.jabatan.isBlank()) {
                errors["jabatan_$index"] = "Jabatan tidak boleh kosong"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(
        ditugaskanUntuk: String,
        deskripsi: String
    ): Boolean {
        val errors = mutableMapOf<String, String>()

        if (ditugaskanUntuk.isBlank()) errors["ditugaskan_untuk"] = "Ditugaskan untuk tidak boleh kosong"
        if (deskripsi.isBlank()) errors["deskripsi"] = "Deskripsi tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
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

    fun clearAdditionalRecipientErrors(index: Int) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove("nik_$index")
        currentErrors.remove("nama_$index")
        currentErrors.remove("jabatan_$index")
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