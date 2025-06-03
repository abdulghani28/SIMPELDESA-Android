package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKKelahiranValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SKKelahiranStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.namaValue.isBlank())
            errors["nama"] = "Nama anak tidak boleh kosong"
        if (stateManager.jenisKelaminValue.isBlank())
            errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
        if (stateManager.tempatLahirValue.isBlank())
            errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (stateManager.tanggalLahirValue.isBlank())
            errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (stateManager.jamLahirValue.isBlank())
            errors["jam_lahir"] = "Jam lahir tidak boleh kosong"
        if (stateManager.anakKeValue <= 0)
            errors["anak_ke"] = "Anak ke- harus lebih dari 0"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(stateManager: SKKelahiranStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.namaAyahValue.isBlank())
            errors["nama_ayah"] = "Nama ayah tidak boleh kosong"

        if (stateManager.nikAyahValue.isBlank()) {
            errors["nik_ayah"] = "NIK ayah tidak boleh kosong"
        } else if (stateManager.nikAyahValue.length != 16) {
            errors["nik_ayah"] = "NIK ayah harus 16 digit"
        }

        // ... (copy validasi lainnya dari kode asli)

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(stateManager: SKKelahiranStateManager): Boolean {
        // Copy implementasi dari validateStep3() kode asli
        val errors = mutableMapOf<String, String>()
        // ... (copy semua validasi step 3)
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep4(stateManager: SKKelahiranStateManager): Boolean {
        val errors = mutableMapOf<String, String>()
        if (stateManager.keperluanValue.isBlank())
            errors["keperluan"] = "Keperluan tidak boleh kosong"
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SKKelahiranStateManager): Boolean {
        return validateStep1(stateManager) &&
                validateStep2(stateManager) &&
                validateStep3(stateManager) &&
                validateStep4(stateManager)
    }

    fun clearFieldError(fieldName: String) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove(fieldName)
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