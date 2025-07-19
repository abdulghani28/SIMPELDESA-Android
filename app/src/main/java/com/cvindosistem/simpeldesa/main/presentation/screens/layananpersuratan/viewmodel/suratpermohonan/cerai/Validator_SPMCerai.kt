package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.cerai

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SPMCeraiValidator(private val stateManager: SPMCeraiStateManager) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nikSuamiValue.isBlank()) {
                errors["nik_suami"] = "NIK suami tidak boleh kosong"
            } else if (nikSuamiValue.length != 16) {
                errors["nik_suami"] = "NIK suami harus 16 digit"
            }

            if (namaSuamiValue.isBlank()) errors["nama_suami"] = "Nama suami tidak boleh kosong"
            if (alamatSuamiValue.isBlank()) errors["alamat_suami"] = "Alamat suami tidak boleh kosong"
            if (pekerjaanSuamiValue.isBlank()) errors["pekerjaan_suami"] = "Pekerjaan suami tidak boleh kosong"
            if (tanggalLahirSuamiValue.isBlank()) errors["tanggal_lahir_suami"] = "Tanggal lahir suami tidak boleh kosong"
            if (tempatLahirSuamiValue.isBlank()) errors["tempat_lahir_suami"] = "Tempat lahir suami tidak boleh kosong"
            if (agamaIdSuamiValue.isBlank()) errors["agama_id_suami"] = "Agama suami tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nikIstriValue.isBlank()) {
                errors["nik_istri"] = "NIK istri tidak boleh kosong"
            } else if (nikIstriValue.length != 16) {
                errors["nik_istri"] = "NIK istri harus 16 digit"
            }

            if (namaIstriValue.isBlank()) errors["nama_istri"] = "Nama istri tidak boleh kosong"
            if (alamatIstriValue.isBlank()) errors["alamat_istri"] = "Alamat istri tidak boleh kosong"
            if (pekerjaanIstriValue.isBlank()) errors["pekerjaan_istri"] = "Pekerjaan istri tidak boleh kosong"
            if (tanggalLahirIstriValue.isBlank()) errors["tanggal_lahir_istri"] = "Tanggal lahir istri tidak boleh kosong"
            if (tempatLahirIstriValue.isBlank()) errors["tempat_lahir_istri"] = "Tempat lahir istri tidak boleh kosong"
            if (agamaIdIstriValue.isBlank()) errors["agama_id_istri"] = "Agama istri tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (sebabCeraiValue.isBlank()) {
                errors["sebab_cerai"] = "Sebab perceraian tidak boleh kosong"
            }
            if (keperluanValue.isBlank()) {
                errors["keperluan"] = "Keperluan tidak boleh kosong"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()
        val step3Valid = validateStep3()
        return step1Valid && step2Valid && step3Valid
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

    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
    }
}
