package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SPNPenguasaanFisikBidangTanahValidator(private val stateManager: SPNPenguasaanFisikBidangTanahStateManager) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nikPemohonValue.isBlank()) {
                errors["nik_pemohon"] = "NIK pemohon tidak boleh kosong"
            } else if (nikPemohonValue.length != 16) {
                errors["nik_pemohon"] = "NIK harus 16 digit"
            }

            if (namaPemohonValue.isBlank()) errors["nama_pemohon"] = "Nama pemohon tidak boleh kosong"
            if (tempatLahirPemohonValue.isBlank()) errors["tempat_lahir_pemohon"] = "Tempat lahir tidak boleh kosong"
            if (tanggalLahirPemohonValue.isBlank()) errors["tanggal_lahir_pemohon"] = "Tanggal lahir tidak boleh kosong"
            if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
            if (alamat1Value.isBlank()) errors["alamat_1"] = "Alamat 1 tidak boleh kosong"
            if (alamat2Value.isBlank()) errors["alamat_2"] = "Alamat 2 tidak boleh kosong"
            if (rtRwValue.isBlank()) errors["rt_rw"] = "RT/RW tidak boleh kosong"
            if (jalanValue.isBlank()) errors["jalan"] = "Jalan tidak boleh kosong"
            if (desaValue.isBlank()) errors["desa"] = "Desa tidak boleh kosong"
            if (kecamatanValue.isBlank()) errors["kecamatan"] = "Kecamatan tidak boleh kosong"
            if (kabupatenValue.isBlank()) errors["kabupaten"] = "Kabupaten tidak boleh kosong"
            if (nibValue.isBlank()) errors["nib"] = "NIB tidak boleh kosong"
            if (luasTanahValue.isBlank()) errors["luas_tanah"] = "Luas tanah tidak boleh kosong"
            if (statusTanahValue.isBlank()) errors["status_tanah"] = "Status tanah tidak boleh kosong"
            if (diperggunakanValue.isBlank()) errors["dipergunakan"] = "Dipergunakan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (diperolehDariValue.isBlank()) errors["diperoleh_dari"] = "Diperoleh dari tidak boleh kosong"
            if (diperolehDenganValue.isBlank()) errors["diperoleh_dengan"] = "Diperoleh dengan tidak boleh kosong"
            if (diperolehSejakValue.isBlank()) errors["diperoleh_sejak"] = "Diperoleh sejak tidak boleh kosong"
            if (batasUtaraValue.isBlank()) errors["batas_utara"] = "Batas utara tidak boleh kosong"
            if (batasTimurValue.isBlank()) errors["batas_timur"] = "Batas timur tidak boleh kosong"
            if (batasSelatanValue.isBlank()) errors["batas_selatan"] = "Batas selatan tidak boleh kosong"
            if (batasBaratValue.isBlank()) errors["batas_barat"] = "Batas barat tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep4(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            // Validasi Saksi 1
            if (nik1Value.isBlank()) {
                errors["nik_1"] = "NIK saksi 1 tidak boleh kosong"
            } else if (nik1Value.length != 16) {
                errors["nik_1"] = "NIK harus 16 digit"
            }
            if (nama1Value.isBlank()) errors["nama_1"] = "Nama saksi 1 tidak boleh kosong"
            if (pekerjaan1Value.isBlank()) errors["pekerjaan_1"] = "Pekerjaan saksi 1 tidak boleh kosong"

            // Validasi Saksi 2
            if (nik2Value.isBlank()) {
                errors["nik_2"] = "NIK saksi 2 tidak boleh kosong"
            } else if (nik2Value.length != 16) {
                errors["nik_2"] = "NIK harus 16 digit"
            }
            if (nama2Value.isBlank()) errors["nama_2"] = "Nama saksi 2 tidak boleh kosong"
            if (pekerjaan2Value.isBlank()) errors["pekerjaan_2"] = "Pekerjaan saksi 2 tidak boleh kosong"
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