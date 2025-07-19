package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKIzinOrangTuaValidator(private val stateManager: SKIzinOrangTuaStateManager) {
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
            if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
            if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
            if (tanggalLahirValue.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
            if (tempatLahirValue.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
            if (agamaIdValue.isBlank()) errors["agama_id"] = "Agama tidak boleh kosong"
            if (kewarganegaraanValue.isBlank()) errors["kewarganegaraan"] = "Kewarganegaraan tidak boleh kosong"
            if (memberiIzinValue.isBlank()) errors["memberi_izin"] = "Memberi izin tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nik2Value.isBlank()) {
                errors["nik_2"] = "NIK tidak boleh kosong"
            } else if (nik2Value.length != 16) {
                errors["nik_2"] = "NIK harus 16 digit"
            }

            if (nama2Value.isBlank()) errors["nama_2"] = "Nama tidak boleh kosong"
            if (alamat2Value.isBlank()) errors["alamat_2"] = "Alamat tidak boleh kosong"
            if (pekerjaan2Value.isBlank()) errors["pekerjaan_2"] = "Pekerjaan tidak boleh kosong"
            if (tanggalLahir2Value.isBlank()) errors["tanggal_lahir_2"] = "Tanggal lahir tidak boleh kosong"
            if (tempatLahir2Value.isBlank()) errors["tempat_lahir_2"] = "Tempat lahir tidak boleh kosong"
            if (agama2IdValue.isBlank()) errors["agama_2_id"] = "Agama tidak boleh kosong"
            if (kewarganegaraan2Value.isBlank()) errors["kewarganegaraan_2"] = "Kewarganegaraan tidak boleh kosong"
            if (diberiIzinValue.isBlank()) errors["diberi_izin"] = "Diberi izin tidak boleh kosong"
            if (statusPekerjaanValue.isBlank()) errors["status_pekerjaan"] = "Status pekerjaan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (namaPerusahaanValue.isBlank()) errors["nama_perusahaan"] = "Nama perusahaan tidak boleh kosong"
            if (negaraTujuanValue.isBlank()) errors["negara_tujuan"] = "Negara tujuan tidak boleh kosong"
            if (masaKontrakValue.isBlank()) errors["masa_kontrak"] = "Masa kontrak tidak boleh kosong"
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