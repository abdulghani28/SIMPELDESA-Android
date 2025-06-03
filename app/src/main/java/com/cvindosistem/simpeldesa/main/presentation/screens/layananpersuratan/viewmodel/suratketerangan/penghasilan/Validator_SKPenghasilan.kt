package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.penghasilan

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKPenghasilanValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SKPenghasilanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (stateManager.namaValue.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (stateManager.alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (stateManager.keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(stateManager: SKPenghasilanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikOrtuValue.isBlank()) {
            errors["nik_ortu"] = "NIK Orang Tua tidak boleh kosong"
        } else if (stateManager.nikOrtuValue.length != 16) {
            errors["nik_ortu"] = "NIK Orang Tua harus 16 digit"
        }

        if (stateManager.namaOrtuValue.isBlank()) errors["nama_ortu"] = "Nama Orang Tua tidak boleh kosong"
        if (stateManager.tempatLahirOrtuValue.isBlank()) errors["tempat_lahir_ortu"] = "Tempat lahir Orang Tua tidak boleh kosong"
        if (stateManager.tanggalLahirOrtuValue.isBlank()) errors["tanggal_lahir_ortu"] = "Tanggal lahir Orang Tua tidak boleh kosong"
        if (stateManager.jenisKelaminOrtuValue.isBlank()) errors["jenis_kelamin_ortu"] = "Jenis kelamin Orang Tua tidak boleh kosong"
        if (stateManager.alamatOrtuValue.isBlank()) errors["alamat_ortu"] = "Alamat Orang Tua tidak boleh kosong"
        if (stateManager.pekerjaanOrtuValue.isBlank()) errors["pekerjaan_ortu"] = "Pekerjaan Orang Tua tidak boleh kosong"
        if (stateManager.penghasilanOrtuValue <= 0) errors["penghasilan_ortu"] = "Penghasilan Orang Tua harus lebih dari 0"
        if (stateManager.tanggunganOrtuValue <= 0) errors["tanggungan_ortu"] = "Jumlah tanggungan harus lebih dari 0"


        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(stateManager: SKPenghasilanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikAnakValue.isBlank()) {
            errors["nik_anak"] = "NIK Anak tidak boleh kosong"
        } else if (stateManager.nikAnakValue.length != 16) {
            errors["nik_anak"] = "NIK Anak harus 16 digit"
        }

        if (stateManager.namaAnakValue.isBlank()) errors["nama_anak"] = "Nama Anak tidak boleh kosong"
        if (stateManager.tempatLahirAnakValue.isBlank()) errors["tempat_lahir_anak"] = "Tempat lahir Anak tidak boleh kosong"
        if (stateManager.tanggalLahirAnakValue.isBlank()) errors["tanggal_lahir_anak"] = "Tanggal lahir Anak tidak boleh kosong"
        if (stateManager.jenisKelaminAnakValue.isBlank()) errors["jenis_kelamin_anak"] = "Jenis kelamin Anak tidak boleh kosong"
        if (stateManager.namaSekolahAnakValue.isBlank()) errors["nama_sekolah_anak"] = "Nama sekolah Anak tidak boleh kosong"
        if (stateManager.kelasAnakValue.isBlank()) errors["kelas_anak"] = "Kelas Anak tidak boleh kosong"


        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SKPenghasilanStateManager): Boolean {
        return validateStep1(stateManager) &&
                validateStep2(stateManager) &&
                validateStep3(stateManager)
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

    fun clearAllErrors() {
        _validationErrors.value = emptyMap()
    }
}