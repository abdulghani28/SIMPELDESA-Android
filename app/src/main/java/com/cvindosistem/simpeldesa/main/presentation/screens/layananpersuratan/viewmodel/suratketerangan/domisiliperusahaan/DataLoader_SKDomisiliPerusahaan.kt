package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.BidangUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.JenisUsahaResponse
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.BidangUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.JenisUsahaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.BidangUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.JenisUsahaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SKDomisiliPerusahaanDataLoader(
    private val getBidangUsahaUseCase: BidangUsahaUseCase,
    private val getJenisUsahaUseCase: JenisUsahaUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SKDomisiliPerusahaanStateManager,
    private val validator: SKDomisiliPerusahaanValidator
) {
    // Loading states
    var isLoadingBidangUsaha by mutableStateOf(false)
        private set
    var isLoadingJenisUsaha by mutableStateOf(false)
        private set
    var isLoadingAgama by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set

    // Data lists
    var bidangUsahaList by mutableStateOf<List<BidangUsahaResponse.Data>>(emptyList())
        private set
    var jenisUsahaList by mutableStateOf<List<JenisUsahaResponse.Data>>(emptyList())
        private set
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set

    // Events
    private val _dataEvents = MutableSharedFlow<DataLoaderEvent>()
    val dataEvents = _dataEvents.asSharedFlow()

    suspend fun loadAllDropdownData() {
        loadBidangUsaha()
        loadJenisUsaha()
        loadAgama()
    }

    private suspend fun loadBidangUsaha() {
        isLoadingBidangUsaha = true
        try {
            when (val result = getBidangUsahaUseCase()) {
                is BidangUsahaResult.Success -> {
                    bidangUsahaList = result.data.data
                }
                is BidangUsahaResult.Error -> {
                    _dataEvents.emit(DataLoaderEvent.BidangUsahaLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            _dataEvents.emit(DataLoaderEvent.BidangUsahaLoadError(e.message ?: "Gagal memuat bidang usaha"))
        } finally {
            isLoadingBidangUsaha = false
        }
    }

    private suspend fun loadJenisUsaha() {
        isLoadingJenisUsaha = true
        try {
            when (val result = getJenisUsahaUseCase()) {
                is JenisUsahaResult.Success -> {
                    jenisUsahaList = result.data.data
                }
                is JenisUsahaResult.Error -> {
                    _dataEvents.emit(DataLoaderEvent.JenisUsahaLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            _dataEvents.emit(DataLoaderEvent.JenisUsahaLoadError(e.message ?: "Gagal memuat jenis usaha"))
        } finally {
            isLoadingJenisUsaha = false
        }
    }

    private suspend fun loadAgama() {
        isLoadingAgama = true
        try {
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    agamaList = result.data.data
                }
                is AgamaResult.Error -> {
                    _dataEvents.emit(DataLoaderEvent.AgamaLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            _dataEvents.emit(DataLoaderEvent.AgamaLoadError(e.message ?: "Gagal memuat data agama"))
        } finally {
            isLoadingAgama = false
        }
    }

    suspend fun loadUserData() {
        isLoadingUserData = true
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    // Update state manager dengan data user
                    stateManager.updateWargaNik(userData.nik ?: "")
                    stateManager.updateWargaNama(userData.nama_warga ?: "")
                    stateManager.updateWargaTempatLahir(userData.tempat_lahir ?: "")
                    stateManager.updateWargaTanggalLahir(dateFormatterToApiFormat(userData.tanggal_lahir ?: ""))
                    stateManager.updateWargaGender(userData.jenis_kelamin ?: "")
                    stateManager.updateWargaAgama(userData.agama_id ?: "")
                    stateManager.updateWargaAlamat(userData.alamat ?: "")
                    stateManager.updateWargaPekerjaan(userData.pekerjaan ?: "")

                    stateManager.updateWargaNik(userData.nik ?: "")
                    stateManager.updateWargaNama(userData.nama_warga ?: "")
                    stateManager.updateWargaTempatLahir(userData.tempat_lahir ?: "")
                    stateManager.updateWargaTanggalLahir(dateFormatterToApiFormat(userData.tanggal_lahir ?: ""))
                    stateManager.updateWargaGender(userData.jenis_kelamin ?: "")
                    stateManager.updateWargaAgama(userData.agama_id ?: "")
                    stateManager.updateWargaAlamat(userData.alamat ?: "")
                    stateManager.updateWargaPekerjaan(userData.pekerjaan ?: "")

                    // Clear validation errors
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "agama_id", "alamat", "pekerjaan"
                    ))
                }
                is UserInfoResult.Error -> {
                    stateManager.updateUseMyData(false)
                    _dataEvents.emit(DataLoaderEvent.UserDataLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            stateManager.updateUseMyData(false)
            _dataEvents.emit(DataLoaderEvent.UserDataLoadError(e.message ?: "Gagal memuat data pengguna"))
        } finally {
            isLoadingUserData = false
        }
    }

    sealed class DataLoaderEvent {
        data class BidangUsahaLoadError(val message: String) : DataLoaderEvent()
        data class JenisUsahaLoadError(val message: String) : DataLoaderEvent()
        data class AgamaLoadError(val message: String) : DataLoaderEvent()
        data class UserDataLoadError(val message: String) : DataLoaderEvent()
    }
}
