package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.resiktpsementara

import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKResiKTPSementaraRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratResiKTPSementaraResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateResiKTPSementaraUseCase

class SKResiKTPSementaraFormSubmitter(
    private val createSuratCatatanResiKTPSementaraUseCase: CreateResiKTPSementaraUseCase
) {

    suspend fun submitForm(
        stateManager: SKResiKTPSementaraStateManager,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        stateManager.isLoading = true
        stateManager.errorMessage = null

        try {
            val request = SKResiKTPSementaraRequest(
                alamat = stateManager.alamatValue,
                disahkan_oleh = "",
                jenis_kelamin = stateManager.selectedGender,
                nama = stateManager.namaValue,
                nik = stateManager.nikValue,
                pekerjaan = stateManager.pekerjaanValue,
                tanggal_lahir = dateFormatterToApiFormat(stateManager.tanggalLahirValue),
                tempat_lahir = stateManager.tempatLahirValue,
                keperluan = stateManager.keperluanValue,
                agama_id = stateManager.agamaValue,
            )

            when (val result = createSuratCatatanResiKTPSementaraUseCase(request)) {
                is SuratResiKTPSementaraResult.Success -> {
                    onSuccess()
                    stateManager.resetForm()
                }
                is SuratResiKTPSementaraResult.Error -> {
                    stateManager.errorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Terjadi kesalahan"
            stateManager.errorMessage = errorMsg
            onError(errorMsg)
        } finally {
            stateManager.isLoading = false
        }
    }
}