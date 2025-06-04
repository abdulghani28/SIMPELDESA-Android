package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja

class SKTidakMasukKerjaValidator(
    private val stateManager: SKTidakMasukKerjaStateManager
) {
    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        // Copy semua validasi dari validateStep1() di kode asli
        if (stateManager.nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (stateManager.namaValue.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (stateManager.tempatLahirValue.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (stateManager.tanggalLahirValue.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (stateManager.jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
        if (stateManager.agamaIdValue.isBlank()) errors["agama_id"] = "Agama tidak boleh kosong"
        if (stateManager.alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (stateManager.pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"

        stateManager.updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        // Copy semua validasi dari validateStep2() di kode asli
        if (stateManager.jabatanValue.isBlank()) errors["jabatan"] = "Jabatan tidak boleh kosong"
        if (stateManager.namaPerusahaanValue.isBlank()) errors["nama_perusahaan"] = "Nama perusahaan tidak boleh kosong"
        if (stateManager.alasanIzinValue.isBlank()) errors["alasan_izin"] = "Alasan izin tidak boleh kosong"
        if (stateManager.keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        if (stateManager.terhitungDariValue.isBlank()) errors["terhitung_dari"] = "Terhitung dari tidak boleh kosong"

        stateManager.updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateAllSteps(): Boolean = validateStep1() && validateStep2()
}