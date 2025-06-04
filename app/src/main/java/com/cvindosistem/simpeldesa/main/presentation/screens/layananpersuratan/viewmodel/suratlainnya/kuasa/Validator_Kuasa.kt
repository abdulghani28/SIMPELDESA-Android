package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.kuasa

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SuratKuasaValidator {

    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(
        nik: String,
        nama: String,
        jabatan: String,
        kuasaUntuk: String,
        kuasaSebagai: String
    ): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nik.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nik.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (nama.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (jabatan.isBlank()) errors["jabatan"] = "Jabatan tidak boleh kosong"
        if (kuasaUntuk.isBlank()) errors["kuasa_untuk"] = "Kolom tidak boleh kosong"
        if (kuasaSebagai.isBlank()) errors["kuasa_sebagai"] = "Kolom tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(
        nikPenerima: String,
        namaPenerima: String,
        jabatanPenerima: String
    ): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikPenerima.isBlank()) {
            errors["nik_penerima"] = "NIK tidak boleh kosong"
        } else if (nikPenerima.length != 16) {
            errors["nik_penerima"] = "NIK harus 16 digit"
        }

        if (namaPenerima.isBlank()) errors["nama_penerima"] = "Nama penerima tidak boleh kosong"
        if (jabatanPenerima.isBlank()) errors["jabatan_penerima"] = "Jabatan penerima tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(
        // Step 1 params
        nik: String, nama: String, jabatan: String,
        kuasaUntuk: String, kuasaSebagai: String,
        // Step 2 params
        nikPenerima: String, namaPenerima: String, jabatanPenerima: String
    ): Boolean {
        val step1Valid = validateStep1(nik, nama, jabatan, kuasaUntuk, kuasaSebagai)
        val step2Valid = validateStep2(nikPenerima, namaPenerima, jabatanPenerima)
        return step1Valid && step2Valid
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