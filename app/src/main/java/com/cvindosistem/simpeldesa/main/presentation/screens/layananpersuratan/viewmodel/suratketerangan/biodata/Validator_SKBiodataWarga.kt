package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKBiodataWargaValidator(private val stateManager: SKBiodataWargaStateManager) {
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
            if (golonganDarahValue.isBlank()) errors["golongan_darah"] = "Golongan darah tidak boleh kosong"
            if (agamaIdValue.isBlank()) errors["agama_id"] = "Agama tidak boleh kosong"
            if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
            if (pendidikanIdValue.isBlank()) errors["pendidikan_id"] = "Pendidikan tidak boleh kosong"
            // disabilitas_id is optional, so no validation needed
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
            if (statusValue.isBlank()) errors["status"] = "Status tidak boleh kosong"
            if (hubunganValue.isBlank()) errors["hubungan"] = "Hubungan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (aktaKawinValue.isBlank()) errors["akta_kawin"] = "Akta kawin tidak boleh kosong"
            if (tanggalKawinValue.isBlank()) errors["tanggal_kawin"] = "Tanggal kawin tidak boleh kosong"
            if (aktaCeraiValue.isBlank()) errors["akta_cerai"] = "Akta cerai tidak boleh kosong"
            if (tanggalCeraiValue.isBlank()) errors["tanggal_cerai"] = "Tanggal cerai tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep4(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (namaAyahValue.isBlank()) errors["nama_ayah"] = "Nama ayah tidak boleh kosong"

            if (nikAyahValue.isBlank()) {
                errors["nik_ayah"] = "NIK ayah tidak boleh kosong"
            } else if (nikAyahValue.length != 16) {
                errors["nik_ayah"] = "NIK ayah harus 16 digit"
            }

            if (namaIbuValue.isBlank()) errors["nama_ibu"] = "Nama ibu tidak boleh kosong"

            if (nikIbuValue.isBlank()) {
                errors["nik_ibu"] = "NIK ibu tidak boleh kosong"
            } else if (nikIbuValue.length != 16) {
                errors["nik_ibu"] = "NIK ibu harus 16 digit"
            }

            if (aktaLahirValue.isBlank()) errors["akta_lahir"] = "Akta lahir tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep5(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3() && validateStep4() && validateStep5()
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