package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliPerusahaanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliPerusahaanUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SKDomisiliPerusahaanFormSubmitter(
    private val createSuratDomisiliPerusahaanUseCase: CreateSuratDomisiliPerusahaanUseCase,
    private val stateManager: SKDomisiliPerusahaanStateManager
) {
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _submitEvents = MutableSharedFlow<SubmitEvent>()
    val submitEvents = _submitEvents.asSharedFlow()

    suspend fun submitForm() {
        isLoading = true
        errorMessage = null

        try {
            val request = SKDomisiliPerusahaanRequest(
                agama_id = stateManager.getCurrentAgama(),
                alamat = stateManager.getCurrentAlamat(),
                alamat_perusahaan = stateManager.getCurrentAlamatPerusahaan(),
                bidang_usaha_id = stateManager.getCurrentBidangUsaha(),
                jenis_kelamin = stateManager.getCurrentGender(),
                jenis_usaha_id = stateManager.getCurrentJenisUsaha(),
                jumlah_karyawan = stateManager.getCurrentJumlahKaryawan(),
                keperluan = if (stateManager.currentTab == 1) stateManager.getPendatangKeperluan() else stateManager.getWargaKeperluan(),
                luas_bangunan = if (stateManager.currentTab == 1) stateManager.getPendatangLuasBangunan() else 0,
                luas_tanah = if (stateManager.currentTab == 1) stateManager.getPendatangLuasTanah() else 0,
                nama = stateManager.getCurrentNama(),
                nama_perusahaan = stateManager.getCurrentNamaPerusahaan(),
                nib = if (stateManager.currentTab == 1) stateManager.getPendatangNib() else stateManager.wargaNibValue,
                nik = stateManager.getCurrentNik(),
                nomor_akta_pendirian = stateManager.getCurrentNomorAkta(),
                npwp = if (stateManager.currentTab == 1) stateManager.getPendatangNpwp() else "",
                pekerjaan = stateManager.getCurrentPekerjaan(),
                peruntukan_bangunan = if (stateManager.currentTab == 1) stateManager.getPendatangPeruntukanBangunan() else "",
                status_kepemilikan_bangunan = stateManager.getCurrentStatusKepemilikanBangunan(),
                tanggal_lahir = stateManager.getCurrentTanggalLahir(),
                tempat_lahir = stateManager.getCurrentTempatLahir()
            )

            when (val result = createSuratDomisiliPerusahaanUseCase(request)) {
                is SuratDomisiliPerusahaanResult.Success -> {
                    _submitEvents.emit(SubmitEvent.Success)
                    stateManager.resetForm()
                }
                is SuratDomisiliPerusahaanResult.Error -> {
                    errorMessage = result.message
                    _submitEvents.emit(SubmitEvent.Error(result.message))
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Terjadi kesalahan"
            _submitEvents.emit(SubmitEvent.Error(errorMessage!!))
        } finally {
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }

    sealed class SubmitEvent {
        data object Success : SubmitEvent()
        data class Error(val message: String) : SubmitEvent()
    }
}