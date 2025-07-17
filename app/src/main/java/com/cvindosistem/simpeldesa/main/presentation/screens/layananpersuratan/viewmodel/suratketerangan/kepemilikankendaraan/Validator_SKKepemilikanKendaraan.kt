package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikankendaraan

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKKepemilikanKendaraanValidator(private val stateManager: SKKepemilikanKendaraanStateManager) {
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
            if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
            if (jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
            if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (merkValue.isBlank()) errors["merk"] = "Merk kendaraan tidak boleh kosong"
            if (tahunPembuatanValue.isBlank()) errors["tahun_pembuatan"] = "Tahun pembuatan tidak boleh kosong"
            if (warnaValue.isBlank()) errors["warna"] = "Warna kendaraan tidak boleh kosong"
            if (nomorPolisiValue.isBlank()) errors["nomor_polisi"] = "Nomor polisi tidak boleh kosong"
            if (nomorMesinValue.isBlank()) errors["nomor_mesin"] = "Nomor mesin tidak boleh kosong"
            if (nomorRangkaValue.isBlank()) errors["nomor_rangka"] = "Nomor rangka tidak boleh kosong"
            if (nomorBpkbValue.isBlank()) errors["nomor_bpkb"] = "Nomor BPKB tidak boleh kosong"
            if (bahanBakarValue.isBlank()) errors["bahan_bakar"] = "Bahan bakar tidak boleh kosong"
            if (isiSilinderValue.isBlank()) errors["isi_silinder"] = "Isi silinder tidak boleh kosong"
            if (atasNamaValue.isBlank()) errors["atas_nama"] = "Atas nama tidak boleh kosong"
            if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2()
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