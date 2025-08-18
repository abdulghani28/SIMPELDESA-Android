package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKKelahiranValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SKKelahiranStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (namaValue.isBlank())
                errors["nama"] = "Nama anak tidak boleh kosong"
            if (jenisKelaminValue.isBlank())
                errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
            if (tempatLahirValue.isBlank())
                errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
            if (tanggalLahirValue.isBlank())
                errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
            if (jamLahirValue.isBlank())
                errors["jam_lahir"] = "Jam lahir tidak boleh kosong"
            if (anakKeValue <= 0)
                errors["anak_ke"] = "Anak ke- harus lebih dari 0"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(stateManager: SKKelahiranStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (namaAyahValue.isBlank())
                errors["nama_ayah"] = "Nama ayah tidak boleh kosong"

            if (nikAyahValue.isBlank()) {
                errors["nik_ayah"] = "NIK ayah tidak boleh kosong"
            } else if (nikAyahValue.length != 16) {
                errors["nik_ayah"] = "NIK ayah harus 16 digit"
            }

            if (tempatLahirAyahValue.isBlank())
                errors["tempat_lahir_ayah"] = "Tempat lahir ayah tidak boleh kosong"
            if (tanggalLahirAyahValue.isBlank())
                errors["tanggal_lahir_ayah"] = "Tanggal lahir ayah tidak boleh kosong"
            if (alamatAyahValue.isBlank())
                errors["alamat_ayah"] = "Alamat ayah tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(stateManager: SKKelahiranStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (namaIbuValue.isBlank())
                errors["nama_ibu"] = "Nama ibu tidak boleh kosong"

            if (nikIbuValue.isBlank()) {
                errors["nik_ibu"] = "NIK ibu tidak boleh kosong"
            } else if (nikIbuValue.length != 16) {
                errors["nik_ibu"] = "NIK ibu harus 16 digit"
            }

            if (tempatLahirIbuValue.isBlank())
                errors["tempat_lahir_ibu"] = "Tempat lahir ibu tidak boleh kosong"
            if (tanggalLahirIbuValue.isBlank())
                errors["tanggal_lahir_ibu"] = "Tanggal lahir ibu tidak boleh kosong"
            if (alamatIbuValue.isBlank())
                errors["alamat_ibu"] = "Alamat ibu tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep4(stateManager: SKKelahiranStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (keperluanValue.isBlank())
                errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

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