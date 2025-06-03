package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKKematianValidator(
    private val stateManager: SKKematianStateManager
) {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.namaValue.isBlank())
            errors["nama"] = "Nama pelapor tidak boleh kosong"
        if (stateManager.alamatValue.isBlank())
            errors["alamat"] = "Alamat pelapor tidak boleh kosong"
        if (stateManager.hubunganIdValue.isBlank())
            errors["hubungan_id"] = "Hubungan dengan mendiang tidak boleh kosong"

        updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikMendiangValue.isBlank()) {
            errors["nik_mendiang"] = "NIK mendiang tidak boleh kosong"
        } else if (stateManager.nikMendiangValue.length != 16) {
            errors["nik_mendiang"] = "NIK mendiang harus 16 digit"
        }

        if (stateManager.namaMendiangValue.isBlank())
            errors["nama_mendiang"] = "Nama mendiang tidak boleh kosong"
        if (stateManager.tempatLahirMendiangValue.isBlank()) errors["tempat_lahir_mendiang"] = "Tempat lahir mendiang tidak boleh kosong"
        if (stateManager.tanggalLahirMendiangValue.isBlank()) errors["tanggal_lahir_mendiang"] = "Tanggal lahir mendiang tidak boleh kosong"
        if (stateManager.jenisKelaminMendiangValue.isBlank()) errors["jenis_kelamin_mendiang"] = "Jenis kelamin mendiang tidak boleh kosong"
        if (stateManager.alamatMendiangValue.isBlank()) errors["alamat_mendiang"] = "Alamat mendiang tidak boleh kosong"
        if (stateManager.hariMeninggalValue.isBlank()) errors["hari_meninggal"] = "Hari meninggal tidak boleh kosong"
        if (stateManager.tanggalMeninggalValue.isBlank()) errors["tanggal_meninggal"] = "Tanggal meninggal tidak boleh kosong"
        if (stateManager.tempatMeninggalValue.isBlank()) errors["tempat_meninggal"] = "Tempat meninggal tidak boleh kosong"
        if (stateManager.sebabMeninggalValue.isBlank()) errors["sebab_meninggal"] = "Sebab meninggal tidak boleh kosong"

        updateValidationErrors(errors)
        return errors.isEmpty()
    }

    fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.keperluanValue.isBlank())
            errors["keperluan"] = "Keperluan tidak boleh kosong"

        updateValidationErrors(errors)
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

    fun clearAllErrors() {
        _validationErrors.value = emptyMap()
    }

    private fun updateValidationErrors(errors: Map<String, String>) {
        _validationErrors.value = errors
    }
}