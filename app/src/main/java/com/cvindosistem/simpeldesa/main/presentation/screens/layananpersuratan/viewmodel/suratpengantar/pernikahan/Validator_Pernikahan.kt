package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.pernikahan

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SPPernikahanValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SPPernikahanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikSuamiValue.isBlank()) {
            errors["nik_suami"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikSuamiValue.length != 16) {
            errors["nik_suami"] = "NIK harus 16 digit"
        }

        if (stateManager.namaSuamiValue.isBlank()) {
            errors["nama_suami"] = "Nama tidak boleh kosong"
        }
        if (stateManager.tempatLahirSuamiValue.isBlank()) {
            errors["tempat_lahir_suami"] = "Tempat lahir tidak boleh kosong"
        }
        if (stateManager.tanggalLahirSuamiValue.isBlank()) {
            errors["tanggal_lahir_suami"] = "Tanggal lahir tidak boleh kosong"
        }
        if (stateManager.pekerjaanSuamiValue.isBlank()) {
            errors["pekerjaan_suami"] = "Pekerjaan tidak boleh kosong"
        }
        if (stateManager.alamatSuamiValue.isBlank()) {
            errors["alamat_suami"] = "Alamat tidak boleh kosong"
        }
        if (stateManager.agamaSuamiIdValue.isBlank()) {
            errors["agama_suami_id"] = "Agama harus dipilih"
        }
        if (stateManager.kewarganegaraanSuamiValue.isBlank()) {
            errors["kewarganegaraan_suami"] = "Kewarganegaraan tidak boleh kosong"
        }
        if (stateManager.statusKawinSuamiIdValue.isBlank()) {
            errors["status_kawin_suami_id"] = "Status kawin harus dipilih"
        }
        if (stateManager.statusKawinSuamiIdValue == "2" &&
            stateManager.namaIstriSebelumnyaValue.isBlank()) {
            errors["nama_istri_sebelumnya"] = "Nama istri sebelumnya wajib diisi"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(stateManager: SPPernikahanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        // Ayah
        if (stateManager.nikAyahSuamiValue.isBlank()) {
            errors["nik_ayah_suami"] = "NIK ayah tidak boleh kosong"
        } else if (stateManager.nikAyahSuamiValue.length != 16) {
            errors["nik_ayah_suami"] = "NIK harus 16 digit"
        }

        if (stateManager.namaAyahSuamiValue.isBlank()) errors["nama_ayah_suami"] = "Nama ayah tidak boleh kosong"
        if (stateManager.tempatLahirAyahSuamiValue.isBlank()) errors["tempat_lahir_ayah_suami"] = "Tempat lahir ayah tidak boleh kosong"
        if (stateManager.tanggalLahirAyahSuamiValue.isBlank()) errors["tanggal_lahir_ayah_suami"] = "Tanggal lahir ayah tidak boleh kosong"
        if (stateManager.pekerjaanAyahSuamiValue.isBlank()) errors["pekerjaan_ayah_suami"] = "Pekerjaan ayah tidak boleh kosong"
        if (stateManager.alamatAyahSuamiValue.isBlank()) errors["alamat_ayah_suami"] = "Alamat ayah tidak boleh kosong"
        if (stateManager.agamaAyahSuamiIdValue.isBlank()) errors["agama_ayah_suami_id"] = "Agama ayah harus dipilih"
        if (stateManager.kewarganegaraanAyahSuamiValue.isBlank()) errors["kewarganegaraan_ayah_suami"] = "Kewarganegaraan ayah tidak boleh kosong"

        // Ibu
        if (stateManager.nikIbuSuamiValue.isBlank()) {
            errors["nik_ibu_suami"] = "NIK ibu tidak boleh kosong"
        } else if (stateManager.nikIbuSuamiValue.length != 16) {
            errors["nik_ibu_suami"] = "NIK harus 16 digit"
        }

        if (stateManager.namaIbuSuamiValue.isBlank()) errors["nama_ibu_suami"] = "Nama ibu tidak boleh kosong"
        if (stateManager.tempatLahirIbuSuamiValue.isBlank()) errors["tempat_lahir_ibu_suami"] = "Tempat lahir ibu tidak boleh kosong"
        if (stateManager.tanggalLahirIbuSuamiValue.isBlank()) errors["tanggal_lahir_ibu_suami"] = "Tanggal lahir ibu tidak boleh kosong"
        if (stateManager.pekerjaanIbuSuamiValue.isBlank()) errors["pekerjaan_ibu_suami"] = "Pekerjaan ibu tidak boleh kosong"
        if (stateManager.alamatIbuSuamiValue.isBlank()) errors["alamat_ibu_suami"] = "Alamat ibu tidak boleh kosong"
        if (stateManager.agamaIbuSuamiIdValue.isBlank()) errors["agama_ibu_suami_id"] = "Agama ibu harus dipilih"
        if (stateManager.kewarganegaraanIbuSuamiValue.isBlank()) errors["kewarganegaraan_ibu_suami"] = "Kewarganegaraan ibu tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(stateManager: SPPernikahanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikIstriValue.isBlank()) {
            errors["nik_istri"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikIstriValue.length != 16) {
            errors["nik_istri"] = "NIK harus 16 digit"
        }

        if (stateManager.namaIstriValue.isBlank()) errors["nama_istri"] = "Nama tidak boleh kosong"
        if (stateManager.tempatLahirIstriValue.isBlank()) errors["tempat_lahir_istri"] = "Tempat lahir tidak boleh kosong"
        if (stateManager.tanggalLahirIstriValue.isBlank()) errors["tanggal_lahir_istri"] = "Tanggal lahir tidak boleh kosong"
        if (stateManager.pekerjaanIstriValue.isBlank()) errors["pekerjaan_istri"] = "Pekerjaan tidak boleh kosong"
        if (stateManager.alamatIstriValue.isBlank()) errors["alamat_istri"] = "Alamat tidak boleh kosong"
        if (stateManager.agamaIstriIdValue.isBlank()) errors["agama_istri_id"] = "Agama harus dipilih"
        if (stateManager.kewarganegaraanIstriValue.isBlank()) errors["kewarganegaraan_istri"] = "Kewarganegaraan tidak boleh kosong"
        if (stateManager.statusKawinIstriIdValue.isBlank()) errors["status_kawin_istri_id"] = "Status kawin harus dipilih"
        if (stateManager.statusKawinIstriIdValue == "2" && stateManager.namaSuamiSebelumnyaValue.isBlank()) {
            errors["nama_suami_sebelumnya"] = "Nama suami sebelumnya wajib diisi"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep4(stateManager: SPPernikahanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        // Ayah
        if (stateManager.nikAyahIstriValue.isBlank()) {
            errors["nik_ayah_istri"] = "NIK ayah tidak boleh kosong"
        } else if (stateManager.nikAyahIstriValue.length != 16) {
            errors["nik_ayah_istri"] = "NIK harus 16 digit"
        }

        if (stateManager.namaAyahIstriValue.isBlank()) errors["nama_ayah_istri"] = "Nama ayah tidak boleh kosong"
        if (stateManager.tempatLahirAyahIstriValue.isBlank()) errors["tempat_lahir_ayah_istri"] = "Tempat lahir ayah tidak boleh kosong"
        if (stateManager.tanggalLahirAyahIstriValue.isBlank()) errors["tanggal_lahir_ayah_istri"] = "Tanggal lahir ayah tidak boleh kosong"
        if (stateManager.pekerjaanAyahIstriValue.isBlank()) errors["pekerjaan_ayah_istri"] = "Pekerjaan ayah tidak boleh kosong"
        if (stateManager.alamatAyahIstriValue.isBlank()) errors["alamat_ayah_istri"] = "Alamat ayah tidak boleh kosong"
        if (stateManager.agamaAyahIstriIdValue.isBlank()) errors["agama_ayah_istri_id"] = "Agama ayah harus dipilih"
        if (stateManager.kewarganegaraanAyahIstriValue.isBlank()) errors["kewarganegaraan_ayah_istri"] = "Kewarganegaraan ayah tidak boleh kosong"

        // Ibu
        if (stateManager.nikIbuIstriValue.isBlank()) {
            errors["nik_ibu_istri"] = "NIK ibu tidak boleh kosong"
        } else if (stateManager.nikIbuIstriValue.length != 16) {
            errors["nik_ibu_istri"] = "NIK harus 16 digit"
        }

        if (stateManager.namaIbuIstriValue.isBlank()) errors["nama_ibu_istri"] = "Nama ibu tidak boleh kosong"
        if (stateManager.tempatLahirIbuIstriValue.isBlank()) errors["tempat_lahir_ibu_istri"] = "Tempat lahir ibu tidak boleh kosong"
        if (stateManager.tanggalLahirIbuIstriValue.isBlank()) errors["tanggal_lahir_ibu_istri"] = "Tanggal lahir ibu tidak boleh kosong"
        if (stateManager.pekerjaanIbuIstriValue.isBlank()) errors["pekerjaan_ibu_istri"] = "Pekerjaan ibu tidak boleh kosong"
        if (stateManager.alamatIbuIstriValue.isBlank()) errors["alamat_ibu_istri"] = "Alamat ibu tidak boleh kosong"
        if (stateManager.agamaIbuIstriIdValue.isBlank()) errors["agama_ibu_istri_id"] = "Agama ibu harus dipilih"
        if (stateManager.kewarganegaraanIbuIstriValue.isBlank()) errors["kewarganegaraan_ibu_istri"] = "Kewarganegaraan ibu tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep5(stateManager: SPPernikahanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.tanggalPernikahanValue.isBlank()) errors["tanggal_pernikahan"] = "Tanggal pernikahan tidak boleh kosong"
        if (stateManager.hariPernikahanValue.isBlank()) errors["hari_pernikahan"] = "Hari pernikahan tidak boleh kosong"
        if (stateManager.jamPernikahanValue.isBlank()) errors["jam_pernikahan"] = "Jam pernikahan tidak boleh kosong"
        if (stateManager.tempatPernikahanValue.isBlank()) errors["tempat_pernikahan"] = "Tempat pernikahan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SPPernikahanStateManager): Boolean {
        return validateStep1(stateManager) &&
                validateStep2(stateManager) &&
                validateStep3(stateManager) &&
                validateStep4(stateManager) &&
                validateStep5(stateManager)
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