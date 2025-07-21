package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKNikahWargaNonMuslimValidator(private val stateManager: SKNikahWargaNonMuslimStateManager) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            // Validasi Data Suami
            if (nikSuamiValue.isBlank()) {
                errors["nik_suami"] = "NIK Suami tidak boleh kosong"
            } else if (nikSuamiValue.length != 16) {
                errors["nik_suami"] = "NIK Suami harus 16 digit"
            }

            if (namaSuamiValue.isBlank()) errors["nama_suami"] = "Nama Suami tidak boleh kosong"
            if (tempatLahirSuamiValue.isBlank()) errors["tempat_lahir_suami"] = "Tempat lahir Suami tidak boleh kosong"
            if (tanggalLahirSuamiValue.isBlank()) errors["tanggal_lahir_suami"] = "Tanggal lahir Suami tidak boleh kosong"
            if (kewarganegaraanSuamiValue.isBlank()) errors["kewarganegaraan_suami"] = "Kewarganegaraan Suami tidak boleh kosong"
            if (wargaNegaraSuamiValue.isBlank()) errors["warga_negara_suami"] = "Status warga negara Suami tidak boleh kosong"
            if (pekerjaanSuamiValue.isBlank()) errors["pekerjaan_suami"] = "Pekerjaan Suami tidak boleh kosong"
            if (pendidikanIdSuamiValue.isBlank()) errors["pendidikan_id_suami"] = "Pendidikan Suami tidak boleh kosong"
            if (statusKawinSuamiValue.isBlank()) errors["status_kawin_suami"] = "Status kawin Suami tidak boleh kosong"
            if (perkawinanKeSuamiValue.isBlank()) errors["perkawinan_ke_suami"] = "Perkawinan ke Suami tidak boleh kosong"
            if (noKkSuamiValue.isBlank()) errors["no_kk_suami"] = "No KK Suami tidak boleh kosong"
            if (namaOrganisasiSuamiValue.isBlank()) errors["nama_organisasi_suami"] = "Nama organisasi Suami tidak boleh kosong"
            if (agamaSuamiIdValue.isBlank()) errors["agama_suami_id"] = "Agama Suami tidak boleh kosong"
            if (teleponSuamiValue.isBlank()) errors["telepon_suami"] = "Telepon Suami tidak boleh kosong"

            // Validasi Data Istri
            if (nikIstriValue.isBlank()) {
                errors["nik_istri"] = "NIK Istri tidak boleh kosong"
            } else if (nikIstriValue.length != 16) {
                errors["nik_istri"] = "NIK Istri harus 16 digit"
            }

            if (namaIstriValue.isBlank()) errors["nama_istri"] = "Nama Istri tidak boleh kosong"
            if (tempatLahirIstriValue.isBlank()) errors["tempat_lahir_istri"] = "Tempat lahir Istri tidak boleh kosong"
            if (tanggalLahirIstriValue.isBlank()) errors["tanggal_lahir_istri"] = "Tanggal lahir Istri tidak boleh kosong"
            if (kewarganegaraanIstriValue.isBlank()) errors["kewarganegaraan_istri"] = "Kewarganegaraan Istri tidak boleh kosong"
            if (wargaNegaraIstriValue.isBlank()) errors["warga_negara_istri"] = "Status warga negara Istri tidak boleh kosong"
            if (pekerjaanIstriValue.isBlank()) errors["pekerjaan_istri"] = "Pekerjaan Istri tidak boleh kosong"
            if (pendidikanIdIstriValue.isBlank()) errors["pendidikan_id_istri"] = "Pendidikan Istri tidak boleh kosong"
            if (statusKawinIstriValue.isBlank()) errors["status_kawin_istri"] = "Status kawin Istri tidak boleh kosong"
            if (perkawinanKeIstriValue.isBlank()) errors["perkawinan_ke_istri"] = "Perkawinan ke Istri tidak boleh kosong"
            if (noKkIstriValue.isBlank()) errors["no_kk_istri"] = "No KK Istri tidak boleh kosong"
            if (namaOrganisasiIstriValue.isBlank()) errors["nama_organisasi_istri"] = "Nama organisasi Istri tidak boleh kosong"
            if (agamaIstriIdValue.isBlank()) errors["agama_istri_id"] = "Agama Istri tidak boleh kosong"
            if (teleponIstriValue.isBlank()) errors["telepon_istri"] = "Telepon Istri tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            // Validasi Alamat
            if (alamatSuamiValue.isBlank()) errors["alamat_suami"] = "Alamat Suami tidak boleh kosong"
            if (alamatIstriValue.isBlank()) errors["alamat_istri"] = "Alamat Istri tidak boleh kosong"

            // Validasi Data Anak
            if (anakKeSuamiValue.isBlank()) errors["anak_ke_suami"] = "Anak ke (Suami) tidak boleh kosong"
            if (anakKeIstriValue.isBlank()) errors["anak_ke_istri"] = "Anak ke (Istri) tidak boleh kosong"
            if (jumlahAnakYangDiakuiValue.isBlank()) errors["jumlah_anak_yang_diakui"] = "Jumlah anak yang diakui tidak boleh kosong"

            // Validasi anak 1 jika ada
            val jumlahAnak = jumlahAnakYangDiakuiValue.toIntOrNull() ?: 0
            if (jumlahAnak >= 1) {
                if (namaAnak1Value.isBlank()) errors["nama_anak1"] = "Nama anak 1 tidak boleh kosong"
                if (noAktaLahir1Value.isBlank()) errors["no_akta_lahir1"] = "No akta lahir anak 1 tidak boleh kosong"
                if (tanggalLahir1Value.isBlank()) errors["tanggal_lahir1"] = "Tanggal lahir anak 1 tidak boleh kosong"
            }

            // Validasi anak 2 jika ada
            if (jumlahAnak >= 2) {
                if (namaAnak2Value.isBlank()) errors["nama_anak2"] = "Nama anak 2 tidak boleh kosong"
                if (noAktaLahir2Value.isBlank()) errors["no_akta_lahir2"] = "No akta lahir anak 2 tidak boleh kosong"
                if (tanggalLahir2Value.isBlank()) errors["tanggal_lahir2"] = "Tanggal lahir anak 2 tidak boleh kosong"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            // Validasi Ayah Suami
            if (nikAyahSuamiValue.isBlank()) {
                errors["nik_ayah_suami"] = "NIK Ayah Suami tidak boleh kosong"
            } else if (nikAyahSuamiValue.length != 16) {
                errors["nik_ayah_suami"] = "NIK Ayah Suami harus 16 digit"
            }

            if (namaAyahSuamiValue.isBlank()) errors["nama_ayah_suami"] = "Nama Ayah Suami tidak boleh kosong"
            if (tempatLahirAyahSuamiValue.isBlank()) errors["tempat_lahir_ayah_suami"] = "Tempat lahir Ayah Suami tidak boleh kosong"
            if (tanggalLahirAyahSuamiValue.isBlank()) errors["tanggal_lahir_ayah_suami"] = "Tanggal lahir Ayah Suami tidak boleh kosong"
            if (pekerjaanAyahSuamiValue.isBlank()) errors["pekerjaan_ayah_suami"] = "Pekerjaan Ayah Suami tidak boleh kosong"
            if (alamatAyahSuamiValue.isBlank()) errors["alamat_ayah_suami"] = "Alamat Ayah Suami tidak boleh kosong"
            if (teleponAyahSuamiValue.isBlank()) errors["telepon_ayah_suami"] = "Telepon Ayah Suami tidak boleh kosong"
            if (agamaAyahSuamiIdValue.isBlank()) errors["agama_ayah_suami_id"] = "Agama Ayah Suami tidak boleh kosong"
            if (kewarganegaraanAyahSuamiValue.isBlank()) errors["kewarganegaraan_ayah_suami"] = "Kewarganegaraan Ayah Suami tidak boleh kosong"
            if (namaOrganisasiAyahSuamiValue.isBlank()) errors["nama_organisasi_ayah_suami"] = "Nama organisasi Ayah Suami tidak boleh kosong"

            // Validasi Ibu Suami
            if (nikIbuSuamiValue.isBlank()) {
                errors["nik_ibu_suami"] = "NIK Ibu Suami tidak boleh kosong"
            } else if (nikIbuSuamiValue.length != 16) {
                errors["nik_ibu_suami"] = "NIK Ibu Suami harus 16 digit"
            }

            if (namaIbuSuamiValue.isBlank()) errors["nama_ibu_suami"] = "Nama Ibu Suami tidak boleh kosong"
            if (tempatLahirIbuSuamiValue.isBlank()) errors["tempat_lahir_ibu_suami"] = "Tempat lahir Ibu Suami tidak boleh kosong"
            if (tanggalLahirIbuSuamiValue.isBlank()) errors["tanggal_lahir_ibu_suami"] = "Tanggal lahir Ibu Suami tidak boleh kosong"
            if (pekerjaanIbuSuamiValue.isBlank()) errors["pekerjaan_ibu_suami"] = "Pekerjaan Ibu Suami tidak boleh kosong"
            if (alamatIbuSuamiValue.isBlank()) errors["alamat_ibu_suami"] = "Alamat Ibu Suami tidak boleh kosong"
            if (teleponIbuSuamiValue.isBlank()) errors["telepon_ibu_suami"] = "Telepon Ibu Suami tidak boleh kosong"
            if (agamaIbuSuamiIdValue.isBlank()) errors["agama_ibu_suami_id"] = "Agama Ibu Suami tidak boleh kosong"
            if (kewarganegaraanIbuSuamiValue.isBlank()) errors["kewarganegaraan_ibu_suami"] = "Kewarganegaraan Ibu Suami tidak boleh kosong"
            if (namaOrganisasiIbuSuamiValue.isBlank()) errors["nama_organisasi_ibu_suami"] = "Nama organisasi Ibu Suami tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep4(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            // Validasi Ayah Istri
            if (nikAyahIstriValue.isBlank()) {
                errors["nik_ayah_istri"] = "NIK Ayah Istri tidak boleh kosong"
            } else if (nikAyahIstriValue.length != 16) {
                errors["nik_ayah_istri"] = "NIK Ayah Istri harus 16 digit"
            }

            if (namaAyahIstriValue.isBlank()) errors["nama_ayah_istri"] = "Nama Ayah Istri tidak boleh kosong"
            if (tempatLahirAyahIstriValue.isBlank()) errors["tempat_lahir_ayah_istri"] = "Tempat lahir Ayah Istri tidak boleh kosong"
            if (tanggalLahirAyahIstriValue.isBlank()) errors["tanggal_lahir_ayah_istri"] = "Tanggal lahir Ayah Istri tidak boleh kosong"
            if (pekerjaanAyahIstriValue.isBlank()) errors["pekerjaan_ayah_istri"] = "Pekerjaan Ayah Istri tidak boleh kosong"
            if (alamatAyahIstriValue.isBlank()) errors["alamat_ayah_istri"] = "Alamat Ayah Istri tidak boleh kosong"
            if (teleponAyahIstriValue.isBlank()) errors["telepon_ayah_istri"] = "Telepon Ayah Istri tidak boleh kosong"
            if (agamaAyahIstriIdValue.isBlank()) errors["agama_ayah_istri_id"] = "Agama Ayah Istri tidak boleh kosong"
            if (kewarganegaraanAyahIstriValue.isBlank()) errors["kewarganegaraan_ayah_istri"] = "Kewarganegaraan Ayah Istri tidak boleh kosong"
            if (namaOrganisasiAyahIstriValue.isBlank()) errors["nama_organisasi_ayah_istri"] = "Nama organisasi Ayah Istri tidak boleh kosong"

            // Validasi Ibu Istri
            if (nikIbuIstriValue.isBlank()) {
                errors["nik_ibu_istri"] = "NIK Ibu Istri tidak boleh kosong"
            } else if (nikIbuIstriValue.length != 16) {
                errors["nik_ibu_istri"] = "NIK Ibu Istri harus 16 digit"
            }

            if (namaIbuIstriValue.isBlank()) errors["nama_ibu_istri"] = "Nama Ibu Istri tidak boleh kosong"
            if (tempatLahirIbuIstriValue.isBlank()) errors["tempat_lahir_ibu_istri"] = "Tempat lahir Ibu Istri tidak boleh kosong"
            if (tanggalLahirIbuIstriValue.isBlank()) errors["tanggal_lahir_ibu_istri"] = "Tanggal lahir Ibu Istri tidak boleh kosong"
            if (pekerjaanIbuIstriValue.isBlank()) errors["pekerjaan_ibu_istri"] = "Pekerjaan Ibu Istri tidak boleh kosong"
            if (alamatIbuIstriValue.isBlank()) errors["alamat_ibu_istri"] = "Alamat Ibu Istri tidak boleh kosong"
            if (teleponIbuIstriValue.isBlank()) errors["telepon_ibu_istri"] = "Telepon Ibu Istri tidak boleh kosong"
            if (agamaIbuIstriIdValue.isBlank()) errors["agama_ibu_istri_id"] = "Agama Ibu Istri tidak boleh kosong"
            if (kewarganegaraanIbuIstriValue.isBlank()) errors["kewarganegaraan_ibu_istri"] = "Kewarganegaraan Ibu Istri tidak boleh kosong"
            if (namaOrganisasiIbuIstriValue.isBlank()) errors["nama_organisasi_ibu_istri"] = "Nama organisasi Ibu Istri tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep5(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            // Validasi Saksi 1
            if (nikSaksi1Value.isBlank()) {
                errors["nik_saksi1"] = "NIK Saksi 1 tidak boleh kosong"
            } else if (nikSaksi1Value.length != 16) {
                errors["nik_saksi1"] = "NIK Saksi 1 harus 16 digit"
            }

            if (namaSaksi1Value.isBlank()) errors["nama_saksi1"] = "Nama Saksi 1 tidak boleh kosong"
            if (tempatLahirSaksi1Value.isBlank()) errors["tempat_lahir_saksi1"] = "Tempat lahir Saksi 1 tidak boleh kosong"
            if (tanggalLahirSaksi1Value.isBlank()) errors["tanggal_lahir_saksi1"] = "Tanggal lahir Saksi 1 tidak boleh kosong"
            if (pekerjaanSaksi1Value.isBlank()) errors["pekerjaan_saksi1"] = "Pekerjaan Saksi 1 tidak boleh kosong"
            if (alamatSaksi1Value.isBlank()) errors["alamat_saksi1"] = "Alamat Saksi 1 tidak boleh kosong"
            if (teleponSaksi1Value.isBlank()) errors["telepon_saksi1"] = "Telepon Saksi 1 tidak boleh kosong"
            if (agamaSaksi1IdValue.isBlank()) errors["agama_saksi1_id"] = "Agama Saksi 1 tidak boleh kosong"
            if (kewarganegaraanSaksi1Value.isBlank()) errors["kewarganegaraan_saksi1"] = "Kewarganegaraan Saksi 1 tidak boleh kosong"
            if (namaOrganisasiSaksi1Value.isBlank()) errors["nama_organisasi_saksi1"] = "Nama organisasi Saksi 1 tidak boleh kosong"
            if (namaAyahSaksi1Value.isBlank()) errors["nama_ayah_saksi1"] = "Nama Ayah Saksi 1 tidak boleh kosong"

            // Validasi Saksi 2
            if (nikSaksi2Value.isBlank()) {
                errors["nik_saksi2"] = "NIK Saksi 2 tidak boleh kosong"
            } else if (nikSaksi2Value.length != 16) {
                errors["nik_saksi2"] = "NIK Saksi 2 harus 16 digit"
            }

            if (namaSaksi2Value.isBlank()) errors["nama_saksi2"] = "Nama Saksi 2 tidak boleh kosong"
            if (tempatLahirSaksi2Value.isBlank()) errors["tempat_lahir_saksi2"] = "Tempat lahir Saksi 2 tidak boleh kosong"
            if (tanggalLahirSaksi2Value.isBlank()) errors["tanggal_lahir_saksi2"] = "Tanggal lahir Saksi 2 tidak boleh kosong"
            if (pekerjaanSaksi2Value.isBlank()) errors["pekerjaan_saksi2"] = "Pekerjaan Saksi 2 tidak boleh kosong"
            if (alamatSaksi2Value.isBlank()) errors["alamat_saksi2"] = "Alamat Saksi 2 tidak boleh kosong"
            if (teleponSaksi2Value.isBlank()) errors["telepon_saksi2"] = "Telepon Saksi 2 tidak boleh kosong"
            if (agamaSaksi2IdValue.isBlank()) errors["agama_saksi2_id"] = "Agama Saksi 2 tidak boleh kosong"
            if (kewarganegaraanSaksi2Value.isBlank()) errors["kewarganegaraan_saksi2"] = "Kewarganegaraan Saksi 2 tidak boleh kosong"
            if (namaOrganisasiSaksi2Value.isBlank()) errors["nama_organisasi_saksi2"] = "Nama organisasi Saksi 2 tidak boleh kosong"
            if (namaAyahSaksi2Value.isBlank()) errors["nama_ayah_saksi2"] = "Nama Ayah Saksi 2 tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep6(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (agamaPernikahanIdValue.isBlank()) errors["agama_pernikahan_id"] = "Agama pernikahan tidak boleh kosong"
            if (namaOrganisasiPernikahanValue.isBlank()) errors["nama_organisasi_pernikahan"] = "Nama organisasi pernikahan tidak boleh kosong"
            if (namaPemukaAgamaValue.isBlank()) errors["nama_pemuka_agama"] = "Nama pemuka agama tidak boleh kosong"
            if (tanggalPemberkatanPernikahanValue.isBlank()) errors["tanggal_pemberkatan_pernikahan"] = "Tanggal pemberkatan pernikahan tidak boleh kosong"
            if (tanggalMelaporPernikahanValue.isBlank()) errors["tanggal_melapor_pernikahan"] = "Tanggal melapor pernikahan tidak boleh kosong"
            if (badanPeradilanPernikahanValue.isBlank()) errors["badan_peradilan_pernikahan"] = "Badan peradilan pernikahan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateStep7(): Boolean {
        val errors = mutableMapOf<String, String>()

        with(stateManager) {
            if (nomorPutusanPengadilanValue.isBlank()) errors["nomor_putusan_pengadilan"] = "Nomor putusan pengadilan tidak boleh kosong"
            if (tanggalPutusanPengadilanValue.isBlank()) errors["tanggal_putusan_pengadilan"] = "Tanggal putusan pengadilan tidak boleh kosong"
            if (nomorIzinPerwakilanValue.isBlank()) errors["nomor_izin_perwakilan"] = "Nomor izin perwakilan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3() &&
                validateStep4() && validateStep5() && validateStep6() && validateStep7()
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

    fun clearSuamiFieldErrors() {
        val suamiFields = listOf(
            "nik_suami", "nama_suami", "tempat_lahir_suami", "tanggal_lahir_suami",
            "kewarganegaraan_suami", "warga_negara_suami", "pekerjaan_suami",
            "pendidikan_id_suami", "status_kawin_suami", "perkawinan_ke_suami",
            "no_kk_suami", "nama_organisasi_suami", "agama_suami_id", "telepon_suami"
        )
        clearMultipleFieldErrors(suamiFields)
    }

    fun clearIstriFieldErrors() {
        val istriFields = listOf(
            "nik_istri", "nama_istri", "tempat_lahir_istri", "tanggal_lahir_istri",
            "kewarganegaraan_istri", "warga_negara_istri", "pekerjaan_istri",
            "pendidikan_id_istri", "status_kawin_istri", "perkawinan_ke_istri",
            "no_kk_istri", "nama_organisasi_istri", "agama_istri_id", "telepon_istri"
        )
        clearMultipleFieldErrors(istriFields)
    }

    fun clearAllFieldErrors() {
        _validationErrors.value = emptyMap()
    }

    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
    }

    fun getErrorsForStep(step: Int): Map<String, String> {
        val allErrors = _validationErrors.value
        return when (step) {
            1 -> allErrors.filterKeys { it.contains("suami") || it.contains("istri") }
            2 -> allErrors.filterKeys { it.contains("alamat") || it.contains("anak") }
            3 -> allErrors.filterKeys { it.contains("ayah_suami") || it.contains("ibu_suami") }
            4 -> allErrors.filterKeys { it.contains("ayah_istri") || it.contains("ibu_istri") }
            5 -> allErrors.filterKeys { it.contains("saksi") }
            6 -> allErrors.filterKeys { it.contains("pernikahan") || it.contains("pemuka") || it.contains("peradilan") }
            7 -> allErrors.filterKeys { it.contains("putusan") || it.contains("izin") }
            else -> emptyMap()
        }
    }

    fun hasErrorsInStep(step: Int): Boolean {
        return getErrorsForStep(step).isNotEmpty()
    }
}