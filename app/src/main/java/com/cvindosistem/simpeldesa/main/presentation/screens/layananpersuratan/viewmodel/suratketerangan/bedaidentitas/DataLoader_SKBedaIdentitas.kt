package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.domain.model.PerbedaanIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.TercantumIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPerbedaanIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetTercantumIdentitasUseCase

class SKBedaIdentitasDataLoader(
    private val getTercantumIdentitasUseCase: GetTercantumIdentitasUseCase,
    private val getPerbedaanIdentitasUseCase: GetPerbedaanIdentitasUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SKBedaIdentitasStateManager,
    private val eventEmitter: (SKBedaIdentitasViewModel.SKBedaIdentitasEvent) -> Unit
) {
    suspend fun loadTercantumIdentitas() {
        stateManager.isLoadingTercantumIdentitas = true
        stateManager.tercantumIdentitasErrorMessage = null
        try {
            when (val result = getTercantumIdentitasUseCase()) {
                is TercantumIdentitasResult.Success -> {
                    stateManager.tercantumIdentitasList = result.data.data
                }
                is TercantumIdentitasResult.Error -> {
                    stateManager.tercantumIdentitasErrorMessage = result.message
                    eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.TercantumIdentitasLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            stateManager.tercantumIdentitasErrorMessage = e.message ?: "Gagal memuat data tercantum identitas"
            eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.TercantumIdentitasLoadError(stateManager.tercantumIdentitasErrorMessage!!))
        } finally {
            stateManager.isLoadingTercantumIdentitas = false
        }
    }

    suspend fun loadPerbedaanIdentitas() {
        stateManager.isLoadingPerbedaanIdentitas = true
        stateManager.perbedaanIdentitasErrorMessage = null
        try {
            when (val result = getPerbedaanIdentitasUseCase()) {
                is PerbedaanIdentitasResult.Success -> {
                    stateManager.perbedaanIdentitasList = result.data.data
                }
                is PerbedaanIdentitasResult.Error -> {
                    stateManager.perbedaanIdentitasErrorMessage = result.message
                    eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.PerbedaanIdentitasLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            stateManager.perbedaanIdentitasErrorMessage = e.message ?: "Gagal memuat data perbedaan identitas"
            eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.PerbedaanIdentitasLoadError(stateManager.perbedaanIdentitasErrorMessage!!))
        } finally {
            stateManager.isLoadingPerbedaanIdentitas = false
        }
    }

    suspend fun loadUserData() {
        stateManager.isLoadingUserData = true
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    stateManager.nama1Value = userData.nama_warga
                    stateManager.nomor1Value = userData.nik
                    stateManager.tempatLahir1Value = userData.tempat_lahir
                    stateManager.tanggalLahir1Value = dateFormatterToApiFormat(userData.tanggal_lahir)
                    stateManager.alamat1Value = userData.alamat

                    stateManager.clearMultipleFieldErrors(listOf(
                        "nama_1", "nomor_1", "tempat_lahir_1", "tanggal_lahir_1", "alamat_1"
                    ))
                }
                is UserInfoResult.Error -> {
                    stateManager.errorMessage = result.message
                    stateManager.useMyDataChecked = false
                    eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.UserDataLoadError(result.message))
                }
            }
        } catch (e: Exception) {
            stateManager.errorMessage = e.message ?: "Gagal memuat data pengguna"
            stateManager.useMyDataChecked = false
            eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.UserDataLoadError(stateManager.errorMessage!!))
        } finally {
            stateManager.isLoadingUserData = false
        }
    }
}