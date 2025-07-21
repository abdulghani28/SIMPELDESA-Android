package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganNikahNonMuslimUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPendidikanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKNikahWargaNonMuslimViewModel(
    private val createSKNikahWargaNonMuslimUseCase: CreateSuratKeteranganNikahNonMuslimUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getPendidikanUseCase: GetPendidikanUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKNikahWargaNonMuslimStateManager()
    private val validator = SKNikahWargaNonMuslimValidator(stateManager)
    private val dataLoader = SKNikahWargaNonMuslimDataLoader(
        getUserInfoUseCase, getAgamaUseCase, getPendidikanUseCase,
        getStatusKawinUseCase, stateManager, validator
    )
    private val formSubmitter = SKNikahNonMuslimFormSubmitter(createSKNikahWargaNonMuslimUseCase, stateManager)

    // Events
    private val _skNikahEvent = MutableSharedFlow<SKNikahWargaNonMuslimEvent>()
    val skNikahEvent = _skNikahEvent.asSharedFlow()

    // UI State
    val uiState = MutableStateFlow(SKNikahNonMuslimUiState()).asStateFlow()

    // Delegate properties to stateManager
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val pendidikanList: List<PendidikanResponse.Data> get() = stateManager.pendidikanList
    val isLoadingPendidikan: Boolean get() = stateManager.isLoadingPendidikan
    val pendidikanErrorMessage: String? get() = stateManager.pendidikanErrorMessage
    val statusKawinList: List<StatusKawinResponse.Data> get() = stateManager.statusKawinList
    val isLoadingStatusKawin: Boolean get() = stateManager.isLoadingStatusKawin
    val statusKawinErrorMessage: String? get() = stateManager.statusKawinErrorMessage
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // STEP 1: Data Suami & Istri properties
    val nikSuami: String get() = stateManager.nikSuamiValue
    val namaSuami: String get() = stateManager.namaSuamiValue
    val tempatLahirSuami: String get() = stateManager.tempatLahirSuamiValue
    val tanggalLahirSuami: String get() = stateManager.tanggalLahirSuamiValue
    val kewarganegaraanSuami: String get() = stateManager.kewarganegaraanSuamiValue
    val wargaNegaraSuami: String get() = stateManager.wargaNegaraSuamiValue
    val pekerjaanSuami: String get() = stateManager.pekerjaanSuamiValue
    val pendidikanIdSuami: String get() = stateManager.pendidikanIdSuamiValue
    val statusKawinSuami: String get() = stateManager.statusKawinSuamiValue
    val perkawinanKeSuami: String get() = stateManager.perkawinanKeSuamiValue
    val pasporSuami: String get() = stateManager.pasporSuamiValue
    val noKkSuami: String get() = stateManager.noKkSuamiValue
    val namaOrganisasiSuami: String get() = stateManager.namaOrganisasiSuamiValue
    val agamaSuamiId: String get() = stateManager.agamaSuamiIdValue
    val teleponSuami: String get() = stateManager.teleponSuamiValue

    val nikIstri: String get() = stateManager.nikIstriValue
    val namaIstri: String get() = stateManager.namaIstriValue
    val tempatLahirIstri: String get() = stateManager.tempatLahirIstriValue
    val tanggalLahirIstri: String get() = stateManager.tanggalLahirIstriValue
    val kewarganegaraanIstri: String get() = stateManager.kewarganegaraanIstriValue
    val wargaNegaraIstri: String get() = stateManager.wargaNegaraIstriValue
    val pekerjaanIstri: String get() = stateManager.pekerjaanIstriValue
    val pendidikanIdIstri: String get() = stateManager.pendidikanIdIstriValue
    val statusKawinIstri: String get() = stateManager.statusKawinIstriValue
    val perkawinanKeIstri: String get() = stateManager.perkawinanKeIstriValue
    val pasporIstri: String get() = stateManager.pasporIstriValue
    val noKkIstri: String get() = stateManager.noKkIstriValue
    val namaOrganisasiIstri: String get() = stateManager.namaOrganisasiIstriValue
    val agamaIstriId: String get() = stateManager.agamaIstriIdValue
    val teleponIstri: String get() = stateManager.teleponIstriValue
    val isIstriWargaDesa: Boolean get() = stateManager.isIstriWargaDesaValue

    // STEP 2: Alamat & Anak properties
    val alamatSuami: String get() = stateManager.alamatSuamiValue
    val alamatIstri: String get() = stateManager.alamatIstriValue
    val anakKeSuami: String get() = stateManager.anakKeSuamiValue
    val anakKeIstri: String get() = stateManager.anakKeIstriValue
    val jumlahAnakYangDiakui: String get() = stateManager.jumlahAnakYangDiakuiValue
    val namaAnak1: String get() = stateManager.namaAnak1Value
    val noAktaLahir1: String get() = stateManager.noAktaLahir1Value
    val tanggalLahir1: String get() = stateManager.tanggalLahir1Value
    val namaAnak2: String get() = stateManager.namaAnak2Value
    val noAktaLahir2: String get() = stateManager.noAktaLahir2Value
    val tanggalLahir2: String get() = stateManager.tanggalLahir2Value

    // STEP 3: Orang Tua Suami properties
    val nikAyahSuami: String get() = stateManager.nikAyahSuamiValue
    val namaAyahSuami: String get() = stateManager.namaAyahSuamiValue
    val tempatLahirAyahSuami: String get() = stateManager.tempatLahirAyahSuamiValue
    val tanggalLahirAyahSuami: String get() = stateManager.tanggalLahirAyahSuamiValue
    val pekerjaanAyahSuami: String get() = stateManager.pekerjaanAyahSuamiValue
    val alamatAyahSuami: String get() = stateManager.alamatAyahSuamiValue
    val teleponAyahSuami: String get() = stateManager.teleponAyahSuamiValue
    val agamaAyahSuamiId: String get() = stateManager.agamaAyahSuamiIdValue
    val kewarganegaraanAyahSuami: String get() = stateManager.kewarganegaraanAyahSuamiValue
    val namaOrganisasiAyahSuami: String get() = stateManager.namaOrganisasiAyahSuamiValue

    val nikIbuSuami: String get() = stateManager.nikIbuSuamiValue
    val namaIbuSuami: String get() = stateManager.namaIbuSuamiValue
    val tempatLahirIbuSuami: String get() = stateManager.tempatLahirIbuSuamiValue
    val tanggalLahirIbuSuami: String get() = stateManager.tanggalLahirIbuSuamiValue
    val pekerjaanIbuSuami: String get() = stateManager.pekerjaanIbuSuamiValue
    val alamatIbuSuami: String get() = stateManager.alamatIbuSuamiValue
    val teleponIbuSuami: String get() = stateManager.teleponIbuSuamiValue
    val agamaIbuSuamiId: String get() = stateManager.agamaIbuSuamiIdValue
    val kewarganegaraanIbuSuami: String get() = stateManager.kewarganegaraanIbuSuamiValue
    val namaOrganisasiIbuSuami: String get() = stateManager.namaOrganisasiIbuSuamiValue

    // STEP 4: Orang Tua Istri properties
    val nikAyahIstri: String get() = stateManager.nikAyahIstriValue
    val namaAyahIstri: String get() = stateManager.namaAyahIstriValue
    val tempatLahirAyahIstri: String get() = stateManager.tempatLahirAyahIstriValue
    val tanggalLahirAyahIstri: String get() = stateManager.tanggalLahirAyahIstriValue
    val pekerjaanAyahIstri: String get() = stateManager.pekerjaanAyahIstriValue
    val alamatAyahIstri: String get() = stateManager.alamatAyahIstriValue
    val teleponAyahIstri: String get() = stateManager.teleponAyahIstriValue
    val agamaAyahIstriId: String get() = stateManager.agamaAyahIstriIdValue
    val kewarganegaraanAyahIstri: String get() = stateManager.kewarganegaraanAyahIstriValue
    val namaOrganisasiAyahIstri: String get() = stateManager.namaOrganisasiAyahIstriValue

    val nikIbuIstri: String get() = stateManager.nikIbuIstriValue
    val namaIbuIstri: String get() = stateManager.namaIbuIstriValue
    val tempatLahirIbuIstri: String get() = stateManager.tempatLahirIbuIstriValue
    val tanggalLahirIbuIstri: String get() = stateManager.tanggalLahirIbuIstriValue
    val pekerjaanIbuIstri: String get() = stateManager.pekerjaanIbuIstriValue
    val alamatIbuIstri: String get() = stateManager.alamatIbuIstriValue
    val teleponIbuIstri: String get() = stateManager.teleponIbuIstriValue
    val agamaIbuIstriId: String get() = stateManager.agamaIbuIstriIdValue
    val kewarganegaraanIbuIstri: String get() = stateManager.kewarganegaraanIbuIstriValue
    val namaOrganisasiIbuIstri: String get() = stateManager.namaOrganisasiIbuIstriValue

    // STEP 5: Saksi Pernikahan properties
    val nikSaksi1: String get() = stateManager.nikSaksi1Value
    val namaSaksi1: String get() = stateManager.namaSaksi1Value
    val tempatLahirSaksi1: String get() = stateManager.tempatLahirSaksi1Value
    val tanggalLahirSaksi1: String get() = stateManager.tanggalLahirSaksi1Value
    val pekerjaanSaksi1: String get() = stateManager.pekerjaanSaksi1Value
    val alamatSaksi1: String get() = stateManager.alamatSaksi1Value
    val teleponSaksi1: String get() = stateManager.teleponSaksi1Value
    val agamaSaksi1Id: String get() = stateManager.agamaSaksi1IdValue
    val kewarganegaraanSaksi1: String get() = stateManager.kewarganegaraanSaksi1Value
    val namaOrganisasiSaksi1: String get() = stateManager.namaOrganisasiSaksi1Value
    val namaAyahSaksi1: String get() = stateManager.namaAyahSaksi1Value
    val isSaksi1WargaDesa: Boolean get() = stateManager.isSaksi1WargaDesaValue

    val nikSaksi2: String get() = stateManager.nikSaksi2Value
    val namaSaksi2: String get() = stateManager.namaSaksi2Value
    val tempatLahirSaksi2: String get() = stateManager.tempatLahirSaksi2Value
    val tanggalLahirSaksi2: String get() = stateManager.tanggalLahirSaksi2Value
    val pekerjaanSaksi2: String get() = stateManager.pekerjaanSaksi2Value
    val alamatSaksi2: String get() = stateManager.alamatSaksi2Value
    val teleponSaksi2: String get() = stateManager.teleponSaksi2Value
    val agamaSaksi2Id: String get() = stateManager.agamaSaksi2IdValue
    val kewarganegaraanSaksi2: String get() = stateManager.kewarganegaraanSaksi2Value
    val namaOrganisasiSaksi2: String get() = stateManager.namaOrganisasiSaksi2Value
    val namaAyahSaksi2: String get() = stateManager.namaAyahSaksi2Value
    val isSaksi2WargaDesa: Boolean get() = stateManager.isSaksi2WargaDesaValue

    // STEP 6: Pernikahan & Pemuka Agama properties
    val agamaPernikahanId: String get() = stateManager.agamaPernikahanIdValue
    val namaOrganisasiPernikahan: String get() = stateManager.namaOrganisasiPernikahanValue
    val namaPemukaAgama: String get() = stateManager.namaPemukaAgamaValue
    val tanggalPemberkatanPernikahan: String get() = stateManager.tanggalPemberkatanPernikahanValue
    val tanggalMelaporPernikahan: String get() = stateManager.tanggalMelaporPernikahanValue
    val badanPeradilanPernikahan: String get() = stateManager.badanPeradilanPernikahanValue

    // STEP 7: Legalitas & Putusan properties
    val nomorPutusanPengadilan: String get() = stateManager.nomorPutusanPengadilanValue
    val tanggalPutusanPengadilan: String get() = stateManager.tanggalPutusanPengadilanValue
    val nomorIzinPerwakilan: String get() = stateManager.nomorIzinPerwakilanValue

    // Data loader functions
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.UserDataLoadError(it.message ?: "Error"))
                }
                loadReferenceData()
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadPendidikan() {
        viewModelScope.launch {
            dataLoader.loadPendidikan().onFailure {
                _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.PendidikanLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            dataLoader.loadStatusKawin().onFailure {
                _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.StatusKawinLoadError(it.message ?: "Error"))
            }
        }
    }

    private suspend fun loadReferenceData() {
        dataLoader.loadAgama().onFailure {
            _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.AgamaLoadError(it.message ?: "Error"))
        }
        dataLoader.loadPendidikan().onFailure {
            _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.PendidikanLoadError(it.message ?: "Error"))
        }
        dataLoader.loadStatusKawin().onFailure {
            _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.StatusKawinLoadError(it.message ?: "Error"))
        }
    }

    // STEP 1 Update Functions - Data Suami
    fun updateNikSuami(value: String) {
        stateManager.updateNikSuami(value)
        validator.clearFieldError("nik_suami")
    }

    fun updateNamaSuami(value: String) {
        stateManager.updateNamaSuami(value)
        validator.clearFieldError("nama_suami")
    }

    fun updateTempatLahirSuami(value: String) {
        stateManager.updateTempatLahirSuami(value)
        validator.clearFieldError("tempat_lahir_suami")
    }

    fun updateTanggalLahirSuami(value: String) {
        stateManager.updateTanggalLahirSuami(value)
        validator.clearFieldError("tanggal_lahir_suami")
    }

    fun updateKewarganegaraanSuami(value: String) {
        stateManager.updateKewarganegaraanSuami(value)
        validator.clearFieldError("kewarganegaraan_suami")
    }

    fun updateWargaNegaraSuami(value: String) {
        stateManager.updateWargaNegaraSuami(value)
        validator.clearFieldError("warga_negara_suami")
    }

    fun updatePekerjaanSuami(value: String) {
        stateManager.updatePekerjaanSuami(value)
        validator.clearFieldError("pekerjaan_suami")
    }

    fun updatePendidikanIdSuami(value: String) {
        stateManager.updatePendidikanIdSuami(value)
        validator.clearFieldError("pendidikan_id_suami")
    }

    fun updateStatusKawinSuami(value: String) {
        stateManager.updateStatusKawinSuami(value)
        validator.clearFieldError("status_kawin_suami")
    }

    fun updatePerkawinanKeSuami(value: String) {
        stateManager.updatePerkawinanKeSuami(value)
        validator.clearFieldError("perkawinan_ke_suami")
    }

    fun updatePasporSuami(value: String) {
        stateManager.updatePasporSuami(value)
        validator.clearFieldError("paspor_suami")
    }

    fun updateNoKkSuami(value: String) {
        stateManager.updateNoKkSuami(value)
        validator.clearFieldError("no_kk_suami")
    }

    fun updateNamaOrganisasiSuami(value: String) {
        stateManager.updateNamaOrganisasiSuami(value)
        validator.clearFieldError("nama_organisasi_suami")
    }

    fun updateAgamaSuamiId(value: String) {
        stateManager.updateAgamaSuamiId(value)
        validator.clearFieldError("agama_suami_id")
    }

    fun updateTeleponSuami(value: String) {
        stateManager.updateTeleponSuami(value)
        validator.clearFieldError("telepon_suami")
    }

    // STEP 1 Update Functions - Data Istri
    fun updateNikIstri(value: String) {
        stateManager.updateNikIstri(value)
        validator.clearFieldError("nik_istri")
    }

    fun updateNamaIstri(value: String) {
        stateManager.updateNamaIstri(value)
        validator.clearFieldError("nama_istri")
    }

    fun updateTempatLahirIstri(value: String) {
        stateManager.updateTempatLahirIstri(value)
        validator.clearFieldError("tempat_lahir_istri")
    }

    fun updateTanggalLahirIstri(value: String) {
        stateManager.updateTanggalLahirIstri(value)
        validator.clearFieldError("tanggal_lahir_istri")
    }

    fun updateKewarganegaraanIstri(value: String) {
        stateManager.updateKewarganegaraanIstri(value)
        validator.clearFieldError("kewarganegaraan_istri")
    }

    fun updateWargaNegaraIstri(value: String) {
        stateManager.updateWargaNegaraIstri(value)
        validator.clearFieldError("warga_negara_istri")
    }

    fun updatePekerjaanIstri(value: String) {
        stateManager.updatePekerjaanIstri(value)
        validator.clearFieldError("pekerjaan_istri")
    }

    fun updatePendidikanIdIstri(value: String) {
        stateManager.updatePendidikanIdIstri(value)
        validator.clearFieldError("pendidikan_id_istri")
    }

    fun updateStatusKawinIstri(value: String) {
        stateManager.updateStatusKawinIstri(value)
        validator.clearFieldError("status_kawin_istri")
    }

    fun updatePerkawinanKeIstri(value: String) {
        stateManager.updatePerkawinanKeIstri(value)
        validator.clearFieldError("perkawinan_ke_istri")
    }

    fun updatePasporIstri(value: String) {
        stateManager.updatePasporIstri(value)
        validator.clearFieldError("paspor_istri")
    }

    fun updateNoKkIstri(value: String) {
        stateManager.updateNoKkIstri(value)
        validator.clearFieldError("no_kk_istri")
    }

    fun updateNamaOrganisasiIstri(value: String) {
        stateManager.updateNamaOrganisasiIstri(value)
        validator.clearFieldError("nama_organisasi_istri")
    }

    fun updateAgamaIstriId(value: String) {
        stateManager.updateAgamaIstriId(value)
        validator.clearFieldError("agama_istri_id")
    }

    fun updateTeleponIstri(value: String) {
        stateManager.updateTeleponIstri(value)
        validator.clearFieldError("telepon_istri")
    }

    fun updateIsIstriWargaDesa(value: Boolean) {
        stateManager.updateIsIstriWargaDesa(value)
    }

    // STEP 2 Update Functions - Alamat & Anak
    fun updateAlamatSuami(value: String) {
        stateManager.updateAlamatSuami(value)
        validator.clearFieldError("alamat_suami")
    }

    fun updateAlamatIstri(value: String) {
        stateManager.updateAlamatIstri(value)
        validator.clearFieldError("alamat_istri")
    }

    fun updateAnakKeSuami(value: String) {
        stateManager.updateAnakKeSuami(value)
        validator.clearFieldError("anak_ke_suami")
    }

    fun updateAnakKeIstri(value: String) {
        stateManager.updateAnakKeIstri(value)
        validator.clearFieldError("anak_ke_istri")
    }

    fun updateJumlahAnakYangDiakui(value: String) {
        stateManager.updateJumlahAnakYangDiakui(value)
        validator.clearFieldError("jumlah_anak_yang_diakui")
    }

    fun updateNamaAnak1(value: String) {
        stateManager.updateNamaAnak1(value)
        validator.clearFieldError("nama_anak1")
    }

    fun updateNoAktaLahir1(value: String) {
        stateManager.updateNoAktaLahir1(value)
        validator.clearFieldError("no_akta_lahir1")
    }

    fun updateTanggalLahir1(value: String) {
        stateManager.updateTanggalLahir1(value)
        validator.clearFieldError("tanggal_lahir1")
    }

    fun updateNamaAnak2(value: String) {
        stateManager.updateNamaAnak2(value)
        validator.clearFieldError("nama_anak2")
    }

    fun updateNoAktaLahir2(value: String) {
        stateManager.updateNoAktaLahir2(value)
        validator.clearFieldError("no_akta_lahir2")
    }

    fun updateTanggalLahir2(value: String) {
        stateManager.updateTanggalLahir2(value)
        validator.clearFieldError("tanggal_lahir2")
    }

    // STEP 3 Update Functions - Orang Tua Suami
    fun updateNikAyahSuami(value: String) {
        stateManager.updateNikAyahSuami(value)
        validator.clearFieldError("nik_ayah_suami")
    }

    fun updateNamaAyahSuami(value: String) {
        stateManager.updateNamaAyahSuami(value)
        validator.clearFieldError("nama_ayah_suami")
    }

    fun updateTempatLahirAyahSuami(value: String) {
        stateManager.updateTempatLahirAyahSuami(value)
        validator.clearFieldError("tempat_lahir_ayah_suami")
    }

    fun updateTanggalLahirAyahSuami(value: String) {
        stateManager.updateTanggalLahirAyahSuami(value)
        validator.clearFieldError("tanggal_lahir_ayah_suami")
    }

    fun updatePekerjaanAyahSuami(value: String) {
        stateManager.updatePekerjaanAyahSuami(value)
        validator.clearFieldError("pekerjaan_ayah_suami")
    }

    fun updateAlamatAyahSuami(value: String) {
        stateManager.updateAlamatAyahSuami(value)
        validator.clearFieldError("alamat_ayah_suami")
    }

    fun updateTeleponAyahSuami(value: String) {
        stateManager.updateTeleponAyahSuami(value)
        validator.clearFieldError("telepon_ayah_suami")
    }

    fun updateAgamaAyahSuamiId(value: String) {
        stateManager.updateAgamaAyahSuamiId(value)
        validator.clearFieldError("agama_ayah_suami_id")
    }

    fun updateKewarganegaraanAyahSuami(value: String) {
        stateManager.updateKewarganegaraanAyahSuami(value)
        validator.clearFieldError("kewarganegaraan_ayah_suami")
    }

    fun updateNamaOrganisasiAyahSuami(value: String) {
        stateManager.updateNamaOrganisasiAyahSuami(value)
        validator.clearFieldError("nama_organisasi_ayah_suami")
    }

    fun updateNikIbuSuami(value: String) {
        stateManager.updateNikIbuSuami(value)
        validator.clearFieldError("nik_ibu_suami")
    }

    fun updateNamaIbuSuami(value: String) {
        stateManager.updateNamaIbuSuami(value)
        validator.clearFieldError("nama_ibu_suami")
    }

    fun updateTempatLahirIbuSuami(value: String) {
        stateManager.updateTempatLahirIbuSuami(value)
        validator.clearFieldError("tempat_lahir_ibu_suami")
    }

    fun updateTanggalLahirIbuSuami(value: String) {
        stateManager.updateTanggalLahirIbuSuami(value)
        validator.clearFieldError("tanggal_lahir_ibu_suami")
    }

    fun updatePekerjaanIbuSuami(value: String) {
        stateManager.updatePekerjaanIbuSuami(value)
        validator.clearFieldError("pekerjaan_ibu_suami")
    }

    fun updateAlamatIbuSuami(value: String) {
        stateManager.updateAlamatIbuSuami(value)
        validator.clearFieldError("alamat_ibu_suami")
    }

    fun updateTeleponIbuSuami(value: String) {
        stateManager.updateTeleponIbuSuami(value)
        validator.clearFieldError("telepon_ibu_suami")
    }

    fun updateAgamaIbuSuamiId(value: String) {
        stateManager.updateAgamaIbuSuamiId(value)
        validator.clearFieldError("agama_ibu_suami_id")
    }

    fun updateKewarganegaraanIbuSuami(value: String) {
        stateManager.updateKewarganegaraanIbuSuami(value)
        validator.clearFieldError("kewarganegaraan_ibu_suami")
    }

    fun updateNamaOrganisasiIbuSuami(value: String) {
        stateManager.updateNamaOrganisasiIbuSuami(value)
        validator.clearFieldError("nama_organisasi_ibu_suami")
    }

    // STEP 4 Update Functions - Orang Tua Istri
    fun updateNikAyahIstri(value: String) {
        stateManager.updateNikAyahIstri(value)
        validator.clearFieldError("nik_ayah_istri")
    }

    fun updateNamaAyahIstri(value: String) {
        stateManager.updateNamaAyahIstri(value)
        validator.clearFieldError("nama_ayah_istri")
    }

    fun updateTempatLahirAyahIstri(value: String) {
        stateManager.updateTempatLahirAyahIstri(value)
        validator.clearFieldError("tempat_lahir_ayah_istri")
    }

    fun updateTanggalLahirAyahIstri(value: String) {
        stateManager.updateTanggalLahirAyahIstri(value)
        validator.clearFieldError("tanggal_lahir_ayah_istri")
    }

    fun updatePekerjaanAyahIstri(value: String) {
        stateManager.updatePekerjaanAyahIstri(value)
        validator.clearFieldError("pekerjaan_ayah_istri")
    }

    fun updateAlamatAyahIstri(value: String) {
        stateManager.updateAlamatAyahIstri(value)
        validator.clearFieldError("alamat_ayah_istri")
    }

    fun updateTeleponAyahIstri(value: String) {
        stateManager.updateTeleponAyahIstri(value)
        validator.clearFieldError("telepon_ayah_istri")
    }

    fun updateAgamaAyahIstriId(value: String) {
        stateManager.updateAgamaAyahIstriId(value)
        validator.clearFieldError("agama_ayah_istri_id")
    }

    fun updateKewarganegaraanAyahIstri(value: String) {
        stateManager.updateKewarganegaraanAyahIstri(value)
        validator.clearFieldError("kewarganegaraan_ayah_istri")
    }

    fun updateNamaOrganisasiAyahIstri(value: String) {
        stateManager.updateNamaOrganisasiAyahIstri(value)
        validator.clearFieldError("nama_organisasi_ayah_istri")
    }

    fun updateNikIbuIstri(value: String) {
        stateManager.updateNikIbuIstri(value)
        validator.clearFieldError("nik_ibu_istri")
    }

    fun updateNamaIbuIstri(value: String) {
        stateManager.updateNamaIbuIstri(value)
        validator.clearFieldError("nama_ibu_istri")
    }

    fun updateTempatLahirIbuIstri(value: String) {
        stateManager.updateTempatLahirIbuIstri(value)
        validator.clearFieldError("tempat_lahir_ibu_istri")
    }

    fun updateTanggalLahirIbuIstri(value: String) {
        stateManager.updateTanggalLahirIbuIstri(value)
        validator.clearFieldError("tanggal_lahir_ibu_istri")
    }

    fun updatePekerjaanIbuIstri(value: String) {
        stateManager.updatePekerjaanIbuIstri(value)
        validator.clearFieldError("pekerjaan_ibu_istri")
    }

    fun updateAlamatIbuIstri(value: String) {
        stateManager.updateAlamatIbuIstri(value)
        validator.clearFieldError("alamat_ibu_istri")
    }

    fun updateTeleponIbuIstri(value: String) {
        stateManager.updateTeleponIbuIstri(value)
        validator.clearFieldError("telepon_ibu_istri")
    }

    fun updateAgamaIbuIstriId(value: String) {
        stateManager.updateAgamaIbuIstriId(value)
        validator.clearFieldError("agama_ibu_istri_id")
    }

    fun updateKewarganegaraanIbuIstri(value: String) {
        stateManager.updateKewarganegaraanIbuIstri(value)
        validator.clearFieldError("kewarganegaraan_ibu_istri")
    }

    fun updateNamaOrganisasiIbuIstri(value: String) {
        stateManager.updateNamaOrganisasiIbuIstri(value)
        validator.clearFieldError("nama_organisasi_ibu_istri")
    }

    // STEP 5 Update Functions - Saksi Pernikahan
    fun updateNikSaksi1(value: String) {
        stateManager.updateNikSaksi1(value)
        validator.clearFieldError("nik_saksi1")
    }

    fun updateNamaSaksi1(value: String) {
        stateManager.updateNamaSaksi1(value)
        validator.clearFieldError("nama_saksi1")
    }

    fun updateTempatLahirSaksi1(value: String) {
        stateManager.updateTempatLahirSaksi1(value)
        validator.clearFieldError("tempat_lahir_saksi1")
    }

    fun updateTanggalLahirSaksi1(value: String) {
        stateManager.updateTanggalLahirSaksi1(value)
        validator.clearFieldError("tanggal_lahir_saksi1")
    }

    fun updatePekerjaanSaksi1(value: String) {
        stateManager.updatePekerjaanSaksi1(value)
        validator.clearFieldError("pekerjaan_saksi1")
    }

    fun updateAlamatSaksi1(value: String) {
        stateManager.updateAlamatSaksi1(value)
        validator.clearFieldError("alamat_saksi1")
    }

    fun updateTeleponSaksi1(value: String) {
        stateManager.updateTeleponSaksi1(value)
        validator.clearFieldError("telepon_saksi1")
    }

    fun updateAgamaSaksi1Id(value: String) {
        stateManager.updateAgamaSaksi1Id(value)
        validator.clearFieldError("agama_saksi1_id")
    }

    fun updateKewarganegaraanSaksi1(value: String) {
        stateManager.updateKewarganegaraanSaksi1(value)
        validator.clearFieldError("kewarganegaraan_saksi1")
    }

    fun updateNamaOrganisasiSaksi1(value: String) {
        stateManager.updateNamaOrganisasiSaksi1(value)
        validator.clearFieldError("nama_organisasi_saksi1")
    }

    fun updateNamaAyahSaksi1(value: String) {
        stateManager.updateNamaAyahSaksi1(value)
        validator.clearFieldError("nama_ayah_saksi1")
    }

    fun updateIsSaksi1WargaDesa(value: Boolean) {
        stateManager.updateIsSaksi1WargaDesa(value)
    }

    fun updateNikSaksi2(value: String) {
        stateManager.updateNikSaksi2(value)
        validator.clearFieldError("nik_saksi2")
    }

    fun updateNamaSaksi2(value: String) {
        stateManager.updateNamaSaksi2(value)
        validator.clearFieldError("nama_saksi2")
    }

    fun updateTempatLahirSaksi2(value: String) {
        stateManager.updateTempatLahirSaksi2(value)
        validator.clearFieldError("tempat_lahir_saksi2")
    }

    fun updateTanggalLahirSaksi2(value: String) {
        stateManager.updateTanggalLahirSaksi2(value)
        validator.clearFieldError("tanggal_lahir_saksi2")
    }

    fun updatePekerjaanSaksi2(value: String) {
        stateManager.updatePekerjaanSaksi2(value)
        validator.clearFieldError("pekerjaan_saksi2")
    }

    fun updateAlamatSaksi2(value: String) {
        stateManager.updateAlamatSaksi2(value)
        validator.clearFieldError("alamat_saksi2")
    }

    fun updateTeleponSaksi2(value: String) {
        stateManager.updateTeleponSaksi2(value)
        validator.clearFieldError("telepon_saksi2")
    }

    fun updateAgamaSaksi2Id(value: String) {
        stateManager.updateAgamaSaksi2Id(value)
        validator.clearFieldError("agama_saksi2_id")
    }

    fun updateKewarganegaraanSaksi2(value: String) {
        stateManager.updateKewarganegaraanSaksi2(value)
        validator.clearFieldError("kewarganegaraan_saksi2")
    }

    fun updateNamaOrganisasiSaksi2(value: String) {
        stateManager.updateNamaOrganisasiSaksi2(value)
        validator.clearFieldError("nama_organisasi_saksi2")
    }

    fun updateNamaAyahSaksi2(value: String) {
        stateManager.updateNamaAyahSaksi2(value)
        validator.clearFieldError("nama_ayah_saksi2")
    }

    fun updateIsSaksi2WargaDesa(value: Boolean) {
        stateManager.updateIsSaksi2WargaDesa(value)
    }

    // STEP 6 Update Functions - Pernikahan & Pemuka Agama
    fun updateAgamaPernikahanId(value: String) {
        stateManager.updateAgamaPernikahanId(value)
        validator.clearFieldError("agama_pernikahan_id")
    }

    fun updateNamaOrganisasiPernikahan(value: String) {
        stateManager.updateNamaOrganisasiPernikahan(value)
        validator.clearFieldError("nama_organisasi_pernikahan")
    }

    fun updateNamaPemukaAgama(value: String) {
        stateManager.updateNamaPemukaAgama(value)
        validator.clearFieldError("nama_pemuka_agama")
    }

    fun updateTanggalPemberkatanPernikahan(value: String) {
        stateManager.updateTanggalPemberkatanPernikahan(value)
        validator.clearFieldError("tanggal_pemberkatan_pernikahan")
    }

    fun updateTanggalMelaporPernikahan(value: String) {
        stateManager.updateTanggalMelaporPernikahan(value)
        validator.clearFieldError("tanggal_melapor_pernikahan")
    }

    fun updateBadanPeradilanPernikahan(value: String) {
        stateManager.updateBadanPeradilanPernikahan(value)
        validator.clearFieldError("badan_peradilan_pernikahan")
    }

    // STEP 7 Update Functions - Legalitas & Putusan
    fun updateNomorPutusanPengadilan(value: String) {
        stateManager.updateNomorPutusanPengadilan(value)
        validator.clearFieldError("nomor_putusan_pengadilan")
    }

    fun updateTanggalPutusanPengadilan(value: String) {
        stateManager.updateTanggalPutusanPengadilan(value)
        validator.clearFieldError("tanggal_putusan_pengadilan")
    }

    fun updateNomorIzinPerwakilan(value: String) {
        stateManager.updateNomorIzinPerwakilan(value)
        validator.clearFieldError("nomor_izin_perwakilan")
    }

    // Navigation functions
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStepWithEvent(1)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            2 -> {
                if (validateStepWithEvent(2)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            3 -> {
                if (validateStepWithEvent(3)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            4 -> {
                if (validateStepWithEvent(4)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            5 -> {
                if (validateStepWithEvent(5)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            6 -> {
                if (validateStepWithEvent(6)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            7 -> {
                if (validateStepWithEvent(7)) {
                    stateManager.showConfirmation()
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.previousStep()
            emitStepChangedEvent()
        }
    }

    // Dialog functions
    fun showPreview() {
        if (!validator.validateAllSteps()) {
            viewModelScope.launch {
                _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.hidePreview()

    fun showConfirmationDialog() {
        if (validator.validateAllSteps()) {
            stateManager.showConfirmation()
        } else {
            viewModelScope.launch {
                _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.SubmitSuccess) }
                .onFailure { _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.SubmitError(it.message ?: "Error")) }
        }
    }

    // Validation functions - delegate to validator
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Utility functions
    fun clearError() = stateManager.clearError()
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Private helper functions
    private fun validateStepWithEvent(step: Int): Boolean {
        val isValid = when (step) {
            1 -> validator.validateStep1()
            2 -> validator.validateStep2()
            3 -> validator.validateStep3()
            4 -> validator.validateStep4()
            5 -> validator.validateStep5()
            6 -> validator.validateStep6()
            7 -> validator.validateStep7()
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skNikahEvent.emit(SKNikahWargaNonMuslimEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKNikahWargaNonMuslimEvent {
        data class StepChanged(val step: Int) : SKNikahWargaNonMuslimEvent()
        data object SubmitSuccess : SKNikahWargaNonMuslimEvent()
        data class SubmitError(val message: String) : SKNikahWargaNonMuslimEvent()
        data object ValidationError : SKNikahWargaNonMuslimEvent()
        data class UserDataLoadError(val message: String) : SKNikahWargaNonMuslimEvent()
        data class AgamaLoadError(val message: String) : SKNikahWargaNonMuslimEvent()
        data class PendidikanLoadError(val message: String) : SKNikahWargaNonMuslimEvent()
        data class StatusKawinLoadError(val message: String) : SKNikahWargaNonMuslimEvent()
    }
}