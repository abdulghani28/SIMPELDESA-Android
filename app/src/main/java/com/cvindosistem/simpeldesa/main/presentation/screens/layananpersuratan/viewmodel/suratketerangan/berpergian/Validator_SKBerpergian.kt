package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.berpergian

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKBerpergianValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(formData: SKBerpergianStateManager.FormData): Boolean {
        val errors = mutableMapOf<String, String>()

        if (formData.nik.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (formData.nik.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (formData.nama.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (formData.tempatLahir.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (formData.tanggalLahir.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (formData.jenisKelamin.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
        if (formData.pekerjaan.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        if (formData.alamat.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(formData: SKBerpergianStateManager.FormData): Boolean {
        val errors = mutableMapOf<String, String>()

        if (formData.tempatTujuan.isBlank()) errors["tempat_tujuan"] = "Tempat tujuan tidak boleh kosong"
        if (formData.maksudTujuan.isBlank()) errors["maksud_tujuan"] = "Maksud tujuan tidak boleh kosong"
        if (formData.tanggalKeberangkatan.isBlank()) errors["tanggal_keberangkatan"] = "Tanggal keberangkatan tidak boleh kosong"

        if (formData.lama == 0) {
            errors["lama"] = "Lama kepergian tidak boleh 0"
        } else if (formData.lama <= 0) {
            errors["lama"] = "Lama kepergian harus lebih dari 0"
        }

        if (formData.jumlahPengikut.isBlank()) {
            errors["jumlah_pengikut"] = "Jumlah pengikut tidak boleh kosong"
        } else {
            try {
                val jumlahInt = formData.jumlahPengikut.toInt()
                if (jumlahInt < 0) {
                    errors["jumlah_pengikut"] = "Jumlah pengikut tidak boleh negatif"
                }
            } catch (e: NumberFormatException) {
                errors["jumlah_pengikut"] = "Jumlah pengikut harus berupa angka"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(formData: SKBerpergianStateManager.FormData): Boolean {
        val errors = mutableMapOf<String, String>()

        if (formData.keperluan.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(formData: SKBerpergianStateManager.FormData): Boolean {
        return validateStep1(formData) && validateStep2(formData) && validateStep3(formData)
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