package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKLahirMatiValidator(private val stateManager: SKLahirMatiStateManager) {
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
            if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nikIbuValue.isBlank()) {
                errors["nik_ibu"] = "NIK Ibu tidak boleh kosong"
            } else if (nikIbuValue.length != 16) {
                errors["nik_ibu"] = "NIK Ibu harus 16 digit"
            }

            if (namaIbuValue.isBlank()) errors["nama_ibu"] = "Nama Ibu tidak boleh kosong"
            if (tempatLahirIbuValue.isBlank()) errors["tempat_lahir_ibu"] =
                "Tempat lahir Ibu tidak boleh kosong"
            if (tanggalLahirIbuValue.isBlank()) errors["tanggal_lahir_ibu"] =
                "Tanggal lahir Ibu tidak boleh kosong"
            if (agamaIbuIdValue.isBlank()) errors["agama_ibu_id"] = "Agama Ibu tidak boleh kosong"
            if (kewarganegaraanIbuIdValue.isBlank()) errors["kewarganegaraan_ibu_id"] =
                "Kewarganegaraan Ibu tidak boleh kosong"
            if (pekerjaanIbuValue.isBlank()) errors["pekerjaan_ibu"] =
                "Pekerjaan Ibu tidak boleh kosong"
            if (alamatIbuValue.isBlank()) errors["alamat_ibu"] = "Alamat Ibu tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (lamaDikandungValue.isBlank()) errors["lama_dikandung"] =
                "Lama dikandung tidak boleh kosong"
            if (tanggalMeninggalValue.isBlank()) errors["tanggal_meninggal"] =
                "Tanggal meninggal tidak boleh kosong"
            if (tempatMeninggalValue.isBlank()) errors["tempat_meninggal"] =
                "Tempat meninggal tidak boleh kosong"
            if (hubunganIdValue.isBlank()) errors["hubungan_id"] = "Hubungan tidak boleh kosong"
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