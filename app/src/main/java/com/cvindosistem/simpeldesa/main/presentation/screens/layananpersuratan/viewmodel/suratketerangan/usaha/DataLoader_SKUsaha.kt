package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.usaha

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.BidangUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.JenisUsahaResponse
import com.cvindosistem.simpeldesa.main.domain.model.BidangUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.JenisUsahaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.BidangUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.JenisUsahaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SKUsahaDataLoader(
    private val getBidangUsahaUseCase: BidangUsahaUseCase,
    private val getJenisUsahaUseCase: JenisUsahaUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SKUsahaStateManager,
    private val validator: SKUsahaFormValidator
) {
    var bidangUsahaList by mutableStateOf<List<BidangUsahaResponse.Data>>(emptyList())
    var jenisUsahaList by mutableStateOf<List<JenisUsahaResponse.Data>>(emptyList())
    var isLoadingBidangUsaha by mutableStateOf(false)
    var isLoadingJenisUsaha by mutableStateOf(false)
    var isLoadingUserData by mutableStateOf(false)

    private val _events = MutableSharedFlow<DataLoaderEvent>()
    val events = _events.asSharedFlow()

    suspend fun loadDropdownData() {
        loadBidangUsaha()
        loadJenisUsaha()
    }

    private suspend fun loadBidangUsaha() {
        isLoadingBidangUsaha = true
        try {
            when (val result = getBidangUsahaUseCase()) {
                is BidangUsahaResult.Success -> {
                    bidangUsahaList = result.data.data
                }
                is BidangUsahaResult.Error -> {
                    _events.emit(DataLoaderEvent.BidangUsahaLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            _events.emit(DataLoaderEvent.BidangUsahaLoadError(e.message ?: "Gagal memuat bidang usaha"))
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
                    _events.emit(DataLoaderEvent.JenisUsahaLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            _events.emit(DataLoaderEvent.JenisUsahaLoadError(e.message ?: "Gagal memuat jenis usaha"))
        } finally {
            isLoadingJenisUsaha = false
        }
    }

    suspend fun loadUserData() {
        isLoadingUserData = true
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    with(stateManager) {
                        updateWargaNik(userData.nik)
                        updateWargaNama(userData.nama_warga)
                        updateWargaTempatLahir(userData.tempat_lahir)
                        updateWargaTanggalLahir(dateFormatterToApiFormat(userData.tanggal_lahir))
                        updateWargaGender(userData.jenis_kelamin)
                        updateWargaPekerjaan(userData.pekerjaan)
                        updateWargaAlamat(userData.alamat)
                    }

                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "pekerjaan", "alamat"
                    ))
                }
                is UserInfoResult.Error -> {
                    stateManager.updateUseMyData(false)
                    _events.emit(DataLoaderEvent.UserDataLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            stateManager.updateUseMyData(false)
            _events.emit(DataLoaderEvent.UserDataLoadError(e.message ?: "Gagal memuat data pengguna"))
        } finally {
            isLoadingUserData = false
        }
    }

    sealed class DataLoaderEvent {
        data class UserDataLoadError(val message: String) : DataLoaderEvent()
        data class BidangUsahaLoadError(val message: String) : DataLoaderEvent()
        data class JenisUsahaLoadError(val message: String) : DataLoaderEvent()
    }
}