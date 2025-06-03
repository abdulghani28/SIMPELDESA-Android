package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.berpergian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKBerpergianStateManager {
    // Form data states
    private val _formState = MutableStateFlow(FormData())
    val formState = _formState.asStateFlow()

    // UI states
    private var _currentStep by mutableIntStateOf(1)
    val currentStep: Int get() = _currentStep

    private var _useMyDataChecked by mutableStateOf(false)
    val useMyDataChecked: Boolean get() = _useMyDataChecked

    private var _isLoadingUserData by mutableStateOf(false)
    val isLoadingUserData: Boolean get() = _isLoadingUserData

    private var _showConfirmationDialog by mutableStateOf(false)
    val showConfirmationDialog: Boolean get() = _showConfirmationDialog

    private var _showPreviewDialog by mutableStateOf(false)
    val showPreviewDialog: Boolean get() = _showPreviewDialog

    // Step 1 - Personal Information
    fun updatePersonalInfo(
        nik: String = formState.value.nik,
        nama: String = formState.value.nama,
        tempatLahir: String = formState.value.tempatLahir,
        tanggalLahir: String = formState.value.tanggalLahir,
        jenisKelamin: String = formState.value.jenisKelamin,
        pekerjaan: String = formState.value.pekerjaan,
        alamat: String = formState.value.alamat
    ) {
        _formState.value = formState.value.copy(
            nik = nik,
            nama = nama,
            tempatLahir = tempatLahir,
            tanggalLahir = tanggalLahir,
            jenisKelamin = jenisKelamin,
            pekerjaan = pekerjaan,
            alamat = alamat
        )
    }

    // Step 2 - Travel Information
    fun updateTravelInfo(
        tempatTujuan: String = formState.value.tempatTujuan,
        maksudTujuan: String = formState.value.maksudTujuan,
        tanggalKeberangkatan: String = formState.value.tanggalKeberangkatan,
        lama: Int = formState.value.lama,
        satuanLama: String = formState.value.satuanLama,
        jumlahPengikut: String = formState.value.jumlahPengikut
    ) {
        _formState.value = formState.value.copy(
            tempatTujuan = tempatTujuan,
            maksudTujuan = maksudTujuan,
            tanggalKeberangkatan = tanggalKeberangkatan,
            lama = lama,
            satuanLama = satuanLama,
            jumlahPengikut = jumlahPengikut
        )
    }

    // Step 3 - Additional Information
    fun updateAdditionalInfo(keperluan: String) {
        _formState.value = formState.value.copy(keperluan = keperluan)
    }

    // Navigation
    fun setCurrentStep(step: Int) {
        _currentStep = step
    }

    fun nextStep() {
        if (_currentStep < 3) _currentStep++
    }

    fun previousStep() {
        if (_currentStep > 1) _currentStep--
    }

    // Dialog states
    fun setUseMyDataChecked(checked: Boolean) {
        _useMyDataChecked = checked
    }

    fun setLoadingUserData(loading: Boolean) {
        _isLoadingUserData = loading
    }

    fun setShowConfirmationDialog(show: Boolean) {
        _showConfirmationDialog = show
    }

    fun setShowPreviewDialog(show: Boolean) {
        _showPreviewDialog = show
    }

    // Reset form
    fun resetForm() {
        _formState.value = FormData()
        _currentStep = 1
        _useMyDataChecked = false
        _showConfirmationDialog = false
        _showPreviewDialog = false
    }

    // Check if form has data
    fun hasFormData(): Boolean {
        val form = formState.value
        return form.nik.isNotBlank() || form.nama.isNotBlank() ||
                form.tempatLahir.isNotBlank() || form.tanggalLahir.isNotBlank() ||
                form.jenisKelamin.isNotBlank() || form.pekerjaan.isNotBlank() ||
                form.alamat.isNotBlank() || form.tempatTujuan.isNotBlank() ||
                form.maksudTujuan.isNotBlank() || form.tanggalKeberangkatan.isNotBlank() ||
                form.lama != 0 || form.jumlahPengikut.isNotBlank() || form.keperluan.isNotBlank()
    }

    data class FormData(
        // Step 1 - Personal Information
        val nik: String = "",
        val nama: String = "",
        val tempatLahir: String = "",
        val tanggalLahir: String = "",
        val jenisKelamin: String = "",
        val pekerjaan: String = "",
        val alamat: String = "",

        // Step 2 - Travel Information
        val tempatTujuan: String = "",
        val maksudTujuan: String = "",
        val tanggalKeberangkatan: String = "",
        val lama: Int = 0,
        val satuanLama: String = "Hari",
        val jumlahPengikut: String = "",

        // Step 3 - Additional Information
        val keperluan: String = ""
    )
}