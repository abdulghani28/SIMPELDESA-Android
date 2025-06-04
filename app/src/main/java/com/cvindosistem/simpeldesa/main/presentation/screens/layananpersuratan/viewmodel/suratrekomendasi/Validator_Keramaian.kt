package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SRKeramaianValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SRKeramaianStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nikValue.isBlank()) {
                errors["nik"] = "NIK tidak boleh kosong"
            } else if (nikValue.length != 16) {
                errors["nik"] = "NIK harus 16 digit"
            }

            if (namaValue.isBlank()) {
                errors["nama"] = "Nama lengkap tidak boleh kosong"
            }

            if (tempatLahirValue.isBlank()) {
                errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
            }

            if (tanggalLahirValue.isBlank()) {
                errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
            }

            if (selectedGender.isBlank()) {
                errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
            }

            if (pekerjaanValue.isBlank()) {
                errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
            }

            if (alamatValue.isBlank()) {
                errors["alamat"] = "Alamat tidak boleh kosong"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(stateManager: SRKeramaianStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (namaAcaraValue.isBlank()) {
                errors["nama_acara"] = "Nama acara tidak boleh kosong"
            }

            if (tempatAcaraValue.isBlank()) {
                errors["tempat_acara"] = "Tempat acara tidak boleh kosong"
            }

            if (hariValue.isBlank()) {
                errors["hari"] = "Hari tidak boleh kosong"
            }

            if (tanggalValue.isBlank()) {
                errors["tanggal"] = "Tanggal tidak boleh kosong"
            }

            if (jamMulaiValue.isBlank()) {
                errors["jam_mulai"] = "Jam mulai tidak boleh kosong"
            }

            if (jamSelesaiValue.isBlank()) {
                errors["jam_selesai"] = "Jam selesai tidak boleh kosong"
            }

            if (penanggungJawabValue.isBlank()) {
                errors["penanggung_jawab"] = "Penanggung jawab tidak boleh kosong"
            }

            if (kontakValue.isBlank()) {
                errors["kontak"] = "Kontak tidak boleh kosong"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SRKeramaianStateManager): Boolean {
        return validateStep1(stateManager) && validateStep2(stateManager)
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