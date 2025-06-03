package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKDomisiliPerusahaanValidator(
    private val stateManager: SKDomisiliPerusahaanStateManager
) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()
        val nik = stateManager.getCurrentNik()
        val nama = stateManager.getCurrentNama()
        val tempatLahir = stateManager.getCurrentTempatLahir()
        val tanggalLahir = stateManager.getCurrentTanggalLahir()
        val gender = stateManager.getCurrentGender()
        val agama = stateManager.getCurrentAgama()
        val alamat = stateManager.getCurrentAlamat()
        val pekerjaan = stateManager.getCurrentPekerjaan()

        if (nik.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nik.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (nama.isBlank()) errors["nama"] = "Nama lengkap tidak boleh kosong"
        if (tempatLahir.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahir.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (gender.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        if (agama.isBlank()) errors["agama_id"] = "Agama harus dipilih"
        if (alamat.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (pekerjaan.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()
        // Copy validasi step 2 dari kode asli
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()
        // Copy validasi step 3 dari kode asli
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3()
    }

    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
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

    fun clearAllErrors() {
        _validationErrors.value = emptyMap()
    }
}
