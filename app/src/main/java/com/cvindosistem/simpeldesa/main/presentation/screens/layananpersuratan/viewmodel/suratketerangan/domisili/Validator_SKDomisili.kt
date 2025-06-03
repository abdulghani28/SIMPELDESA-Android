package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKDomisiliValidator(private val stateManager: SKDomisiliStateManager) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateWargaDesa(): Boolean {
        val errors = validateCommonFields()
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validatePendatang(): Boolean {
        val errors = validateCommonFields()

        if (stateManager.selectedKewarganegaraan.isBlank()) {
            errors["kewarganegaraan"] = "Kewarganegaraan harus dipilih"
        }

        if (stateManager.alamatSesuaiKTP.isBlank()) {
            errors["alamat_identitas"] = "Alamat sesuai identitas tidak boleh kosong"
        }

        if (stateManager.alamatTinggalSekarang.isBlank()) {
            errors["alamat_tinggal_sekarang"] = "Alamat tempat tinggal sekarang tidak boleh kosong"
        }

        if (stateManager.jumlahPengikut.isBlank()) {
            errors["jumlah_pengikut"] = "Jumlah pengikut tidak boleh kosong"
        } else {
            try {
                stateManager.jumlahPengikut.toInt()
            } catch (_: NumberFormatException) {
                errors["jumlah_pengikut"] = "Jumlah pengikut harus berupa angka"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateCommonFields(): MutableMap<String, String> {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (stateManager.namaValue.isBlank()) {
            errors["nama"] = "Nama lengkap tidak boleh kosong"
        }

        // ... Copy validasi lainnya dari kode asli

        return errors
    }

    fun validateAllFields(): Boolean {
        return if (stateManager.currentTab == 0) validateWargaDesa() else validatePendatang()
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