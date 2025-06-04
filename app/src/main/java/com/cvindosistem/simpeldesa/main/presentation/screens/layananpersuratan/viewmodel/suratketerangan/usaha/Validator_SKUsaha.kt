package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.usaha

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKUsahaFormValidator(private val stateManager: SKUsahaStateManager) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()
        val nik = stateManager.getCurrentNik()
        val nama = stateManager.getCurrentNama()
        val tempatLahir = stateManager.getCurrentTempatLahir()
        val tanggalLahir = stateManager.getCurrentTanggalLahir()
        val gender = stateManager.getCurrentGender()
        val pekerjaan = stateManager.getCurrentPekerjaan()
        val alamat = stateManager.getCurrentAlamat()

        if (nik.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nik.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (nama.isBlank()) errors["nama"] = "Nama lengkap tidak boleh kosong"
        if (tempatLahir.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahir.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (gender.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        if (pekerjaan.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        if (alamat.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()
        val namaUsaha = stateManager.getCurrentNamaUsaha()
        val bidangUsaha = stateManager.getCurrentBidangUsaha()
        val jenisUsaha = stateManager.getCurrentJenisUsaha()
        val alamatUsaha = stateManager.getCurrentAlamatUsaha()

        if (namaUsaha.isBlank()) errors["nama_usaha"] = "Nama usaha tidak boleh kosong"
        if (bidangUsaha.isBlank()) errors["bidang_usaha_id"] = "Bidang usaha harus dipilih"
        if (jenisUsaha.isBlank()) errors["jenis_usaha_id"] = "Jenis usaha harus dipilih"
        if (alamatUsaha.isBlank()) errors["alamat_usaha"] = "Alamat usaha tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.currentTab == 0) {
            // Warga Desa - validasi keperluan
            if (stateManager.getCurrentKeperluan().isBlank()) {
                errors["keperluan"] = "Keperluan tidak boleh kosong"
            }
        } else {
            // Pendatang - validasi NPWP
            if (stateManager.getCurrentNpwp().isBlank()) {
                errors["npwp"] = "NPWP tidak boleh kosong"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean = validateStep1() && validateStep2() && validateStep3()

    fun getFieldError(fieldName: String): String? = _validationErrors.value[fieldName]

    fun hasFieldError(fieldName: String): Boolean = _validationErrors.value.containsKey(fieldName)

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