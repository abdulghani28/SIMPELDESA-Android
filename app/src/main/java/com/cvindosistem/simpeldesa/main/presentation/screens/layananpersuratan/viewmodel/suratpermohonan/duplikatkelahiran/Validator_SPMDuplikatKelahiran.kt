package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatkelahiran

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SPMDuplikatKelahiranValidator(private val stateManager: SPMDuplikatKelahiranStateManager) {
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
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nikAnakValue.isBlank()) {
                errors["nik_anak"] = "NIK anak tidak boleh kosong"
            } else if (nikAnakValue.length != 16) {
                errors["nik_anak"] = "NIK anak harus 16 digit"
            }

            if (namaAnakValue.isBlank()) errors["nama_anak"] = "Nama anak tidak boleh kosong"
            if (tanggalLahirAnakValue.isBlank()) errors["tanggal_lahir_anak"] = "Tanggal lahir anak tidak boleh kosong"
            if (tempatLahirAnakValue.isBlank()) errors["tempat_lahir_anak"] = "Tempat lahir anak tidak boleh kosong"
            if (jenisKelaminAnakValue.isBlank()) errors["jenis_kelamin_anak"] = "Jenis kelamin anak tidak boleh kosong"
            if (agamaIdAnakValue.isBlank()) errors["agama_id_anak"] = "Agama anak tidak boleh kosong"
            if (alamatAnakValue.isBlank()) errors["alamat_anak"] = "Alamat anak tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            // Validasi Ayah
            if (namaAyahValue.isBlank()) errors["nama_ayah"] = "Nama ayah tidak boleh kosong"

            if (nikAyahValue.isBlank()) {
                errors["nik_ayah"] = "NIK ayah tidak boleh kosong"
            } else if (nikAyahValue.length != 16) {
                errors["nik_ayah"] = "NIK ayah harus 16 digit"
            }

            if (alamatAyahValue.isBlank()) errors["alamat_ayah"] = "Alamat ayah tidak boleh kosong"
            if (pekerjaanAyahValue.isBlank()) errors["pekerjaan_ayah"] = "Pekerjaan ayah tidak boleh kosong"

            // Validasi Ibu
            if (namaIbuValue.isBlank()) errors["nama_ibu"] = "Nama ibu tidak boleh kosong"

            if (nikIbuValue.isBlank()) {
                errors["nik_ibu"] = "NIK ibu tidak boleh kosong"
            } else if (nikIbuValue.length != 16) {
                errors["nik_ibu"] = "NIK ibu harus 16 digit"
            }

            if (alamatIbuValue.isBlank()) errors["alamat_ibu"] = "Alamat ibu tidak boleh kosong"
            if (pekerjaanIbuValue.isBlank()) errors["pekerjaan_ibu"] = "Pekerjaan ibu tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep4(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3() && validateStep4()
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