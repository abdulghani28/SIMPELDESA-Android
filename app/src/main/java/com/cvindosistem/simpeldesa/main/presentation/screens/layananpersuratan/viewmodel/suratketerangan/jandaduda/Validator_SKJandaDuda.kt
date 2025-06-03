package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jandaduda

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKJandaDudaValidator(private val stateManager: SKJandaDudaStateManager) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nikValue.isBlank()) {
                errors["nik"] = "NIK tidak boleh kosong"
            } else if (nikValue.length != 16) {
                errors["nik"] = "NIK harus 16 digit"
            }

            if (namaValue.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
            if (tempatLahirValue.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
            if (tanggalLahirValue.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
            if (jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
            if (agamaIdValue.isBlank()) errors["agama_id"] = "Agama tidak boleh kosong"
            if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
            if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (dasarPengajuanValue.isBlank()) errors["dasar_pengajuan"] = "Dasar pengajuan tidak boleh kosong"
            if (nomorPengajuanValue.isBlank()) errors["nomor_pengajuan"] = "Nomor pengajuan tidak boleh kosong"
            if (penyebabValue.isBlank()) errors["penyebab"] = "Penyebab tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3()
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
}