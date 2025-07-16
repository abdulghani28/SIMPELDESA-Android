package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKJualBeliValidator(private val stateManager: SKJualBeliStateManager) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nik1Value.isBlank()) {
                errors["nik_1"] = "NIK penjual tidak boleh kosong"
            } else if (nik1Value.length != 16) {
                errors["nik_1"] = "NIK penjual harus 16 digit"
            }

            if (nama1Value.isBlank()) errors["nama_1"] = "Nama penjual tidak boleh kosong"
            if (alamat1Value.isBlank()) errors["alamat_1"] = "Alamat penjual tidak boleh kosong"
            if (jenisKelamin1Value.isBlank()) errors["jenis_kelamin_1"] = "Jenis kelamin penjual tidak boleh kosong"
            if (pekerjaan1Value.isBlank()) errors["pekerjaan_1"] = "Pekerjaan penjual tidak boleh kosong"
            if (tanggalLahir1Value.isBlank()) errors["tanggal_lahir_1"] = "Tanggal lahir penjual tidak boleh kosong"
            if (tempatLahir1Value.isBlank()) errors["tempat_lahir_1"] = "Tempat lahir penjual tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nik2Value.isBlank()) {
                errors["nik_2"] = "NIK pembeli tidak boleh kosong"
            } else if (nik2Value.length != 16) {
                errors["nik_2"] = "NIK pembeli harus 16 digit"
            }

            if (nama2Value.isBlank()) errors["nama_2"] = "Nama pembeli tidak boleh kosong"
            if (alamat2Value.isBlank()) errors["alamat_2"] = "Alamat pembeli tidak boleh kosong"
            if (jenisKelamin2Value.isBlank()) errors["jenis_kelamin_2"] = "Jenis kelamin pembeli tidak boleh kosong"
            if (pekerjaan2Value.isBlank()) errors["pekerjaan_2"] = "Pekerjaan pembeli tidak boleh kosong"
            if (tanggalLahir2Value.isBlank()) errors["tanggal_lahir_2"] = "Tanggal lahir pembeli tidak boleh kosong"
            if (tempatLahir2Value.isBlank()) errors["tempat_lahir_2"] = "Tempat lahir pembeli tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (jenisBarangValue.isBlank()) errors["jenis_barang"] = "Jenis barang tidak boleh kosong"
            if (rincianBarangValue.isBlank()) errors["rincian_barang"] = "Rincian barang tidak boleh kosong"
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