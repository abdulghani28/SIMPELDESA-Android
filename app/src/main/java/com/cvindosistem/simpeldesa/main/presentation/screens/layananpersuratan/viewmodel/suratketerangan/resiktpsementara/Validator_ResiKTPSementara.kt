package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.resiktpsementara

class SKResiKTPSementaraValidator {

    fun validateStep1(stateManager: SKResiKTPSementaraStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        // Copy validation logic dari kode asli
        if (stateManager.nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (stateManager.namaValue.isBlank()) {
            errors["nama"] = "Nama lengkap tidak boleh kosong"
        }

        if (stateManager.tempatLahirValue.isBlank()) {
            errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        }

        if (stateManager.tanggalLahirValue.isBlank()) {
            errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        }

        if (stateManager.selectedGender.isBlank()) {
            errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        }

        if (stateManager.pekerjaanValue.isBlank()) {
            errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        if (stateManager.alamatValue.isBlank()) {
            errors["alamat"] = "Alamat tidak boleh kosong"
        }

        if (stateManager.agamaValue.isBlank()) {
            errors["agama_id"] = "Agama tidak boleh kosong"
        }

        if (stateManager.keperluanValue.isBlank()) {
            errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        stateManager.updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SKResiKTPSementaraStateManager): Boolean {
        return validateStep1(stateManager)
    }
}