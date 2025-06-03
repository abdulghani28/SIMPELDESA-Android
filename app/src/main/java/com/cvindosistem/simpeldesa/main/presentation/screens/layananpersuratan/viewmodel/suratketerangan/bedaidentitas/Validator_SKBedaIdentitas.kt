package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas

// 2. Ekstrak Form Validator
class SKBedaIdentitasValidator(private val stateManager: SKBedaIdentitasStateManager) {

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.perbedaanIdValue.isBlank()) errors["perbedaan_id"] = "Perbedaan identitas tidak boleh kosong"
        if (stateManager.nama1Value.isBlank()) errors["nama_1"] = "Nama pertama tidak boleh kosong"
        if (stateManager.nomor1Value.isBlank()) errors["nomor_1"] = "Nomor pertama tidak boleh kosong"
        if (stateManager.tempatLahir1Value.isBlank()) errors["tempat_lahir_1"] = "Tempat lahir pertama tidak boleh kosong"
        if (stateManager.tanggalLahir1Value.isBlank()) errors["tanggal_lahir_1"] = "Tanggal lahir pertama tidak boleh kosong"
        if (stateManager.alamat1Value.isBlank()) errors["alamat_1"] = "Alamat pertama tidak boleh kosong"
        if (stateManager.tercantumId1Value.isBlank()) errors["tercantum_id"] = "Tercantum identitas pertama tidak boleh kosong"

        stateManager.updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nama2Value.isBlank()) errors["nama_2"] = "Nama kedua tidak boleh kosong"
        if (stateManager.nomor2Value.isBlank()) errors["nomor_2"] = "Nomor kedua tidak boleh kosong"
        if (stateManager.tempatLahir2Value.isBlank()) errors["tempat_lahir_2"] = "Tempat lahir kedua tidak boleh kosong"
        if (stateManager.tanggalLahir2Value.isBlank()) errors["tanggal_lahir_2"] = "Tanggal lahir kedua tidak boleh kosong"
        if (stateManager.alamat2Value.isBlank()) errors["alamat_2"] = "Alamat kedua tidak boleh kosong"
        if (stateManager.tercantumId2Value.isBlank()) errors["tercantum_id_2"] = "Tercantum identitas kedua tidak boleh kosong"

        stateManager.updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (stateManager.keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"

        stateManager.updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3()
    }
}
