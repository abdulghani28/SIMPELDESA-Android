package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKKepemilikanTanahValidator(private val stateManager: SKKepemilikanTanahStateManager) {
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
            if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
            if (luasTanahValue.isBlank()) errors["luas_tanah"] = "Luas tanah tidak boleh kosong"
            if (jenisTanahValue.isBlank()) errors["jenis_tanah"] = "Jenis tanah tidak boleh kosong"
            if (batasUtaraValue.isBlank()) errors["batas_utara"] = "Batas utara tidak boleh kosong"
            if (batasTimurValue.isBlank()) errors["batas_timur"] = "Batas timur tidak boleh kosong"
            if (batasSelatanValue.isBlank()) errors["batas_selatan"] = "Batas selatan tidak boleh kosong"
            if (batasBaratValue.isBlank()) errors["batas_barat"] = "Batas barat tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (atasNamaValue.isBlank()) errors["atas_nama"] = "Atas nama tidak boleh kosong"
            if (asalKepemilikanTanahValue.isBlank()) errors["asal_kepemilikan_tanah"] = "Asal kepemilikan tanah tidak boleh kosong"
            if (buktiKepemilikanTanahValue.isBlank()) errors["bukti_kepemilikan_tanah"] = "Bukti kepemilikan tanah tidak boleh kosong"
            if (buktiKepemilikanTanahTanahValue.isBlank()) errors["bukti_kepemilikan_tanah_tanah"] = "Bukti kepemilikan tanah-tanah tidak boleh kosong"
            if (nomorBuktiKepemilikanValue.isBlank()) errors["nomor_bukti_kepemilikan"] = "Nomor bukti kepemilikan tidak boleh kosong"
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