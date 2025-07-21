package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SPPermohonanPenerbitanBukuPasLintasBatasValidator(
    private val stateManager: SPPermohonanPenerbitanBukuPasLintasBatasStateManager
) {
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
            if (kewarganegaraanValue.isBlank()) errors["kewarganegaraan"] = "Kewarganegaraan tidak boleh kosong"
            if (agamaIdValue.isBlank()) errors["agama_id"] = "Agama tidak boleh kosong"
            if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
            if (statusKawinIdValue.isBlank()) errors["status_kawin_id"] = "Status kawin tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"

            if (noKkValue.isBlank()) {
                errors["no_kk"] = "Nomor KK tidak boleh kosong"
            } else if (noKkValue.length != 16) {
                errors["no_kk"] = "Nomor KK harus 16 digit"
            }

            if (kepalaKeluargaValue.isBlank()) errors["kepala_keluarga"] = "Nama kepala keluarga tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (anggotaKeluargaValue.isEmpty()) {
                errors["anggota_keluarga"] = "Minimal harus ada 1 anggota keluarga yang ikut"
            } else {
                // Validasi setiap anggota keluarga
                anggotaKeluargaValue.forEachIndexed { index, anggota ->
                    if (anggota.nik.isBlank()) {
                        errors["anggota_keluarga_nik_$index"] = "NIK anggota keluarga ${index + 1} tidak boleh kosong"
                    } else if (anggota.nik.length != 16) {
                        errors["anggota_keluarga_nik_$index"] = "NIK anggota keluarga ${index + 1} harus 16 digit"
                    }

                    if (anggota.keterangan.isBlank()) {
                        errors["anggota_keluarga_keterangan_$index"] = "Keterangan anggota keluarga ${index + 1} tidak boleh kosong"
                    }
                }
            }
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

    fun clearAnggotaKeluargaErrors(index: Int) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove("anggota_keluarga_nik_$index")
        currentErrors.remove("anggota_keluarga_keterangan_$index")
        _validationErrors.value = currentErrors
    }

    fun clearAllAnggotaKeluargaErrors() {
        val currentErrors = _validationErrors.value.toMutableMap()
        val keysToRemove = currentErrors.keys.filter {
            it.startsWith("anggota_keluarga_nik_") || it.startsWith("anggota_keluarga_keterangan_")
        }
        keysToRemove.forEach { currentErrors.remove(it) }
        currentErrors.remove("anggota_keluarga")
        _validationErrors.value = currentErrors
    }

    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
    }

    fun getAnggotaKeluargaError(index: Int, field: String): String? {
        return _validationErrors.value["anggota_keluarga_${field}_$index"]
    }

    fun hasAnggotaKeluargaError(index: Int, field: String): Boolean {
        return _validationErrors.value.containsKey("anggota_keluarga_${field}_$index")
    }
}