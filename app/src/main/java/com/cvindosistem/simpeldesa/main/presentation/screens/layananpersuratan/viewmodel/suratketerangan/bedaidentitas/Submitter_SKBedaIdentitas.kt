package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratBedaIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBedaIdentitasUseCase

class SKBedaIdentitasFormSubmitter(
    private val createSKBedaIdentitasUseCase: CreateSuratBedaIdentitasUseCase,
    private val stateManager: SKBedaIdentitasStateManager,
    private val eventEmitter: (SKBedaIdentitasViewModel.SKBedaIdentitasEvent) -> Unit
) {

    suspend fun submitForm() {
        stateManager.isLoading = true
        stateManager.errorMessage = null

        try {
            val request = SKBedaIdentitasRequest(
                alamat_1 = stateManager.alamat1Value,
                alamat_2 = stateManager.alamat2Value,
                disahkan_oleh = "",
                keperluan = stateManager.keperluanValue,
                nama_1 = stateManager.nama1Value,
                nama_2 = stateManager.nama2Value,
                nomor_1 = stateManager.nomor1Value,
                nomor_2 = stateManager.nomor2Value,
                perbedaan_id = stateManager.perbedaanIdValue,
                tanggal_lahir_1 = stateManager.tanggalLahir1Value,
                tanggal_lahir_2 = stateManager.tanggalLahir2Value,
                tempat_lahir_1 = stateManager.tempatLahir1Value,
                tempat_lahir_2 = stateManager.tempatLahir2Value,
                tercantum_id = stateManager.tercantumId1Value,
                tercantum_id_2 = stateManager.tercantumId2Value
            )

            when (val result = createSKBedaIdentitasUseCase(request)) {
                is SuratBedaIdentitasResult.Success -> {
                    eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.SubmitSuccess)
                    stateManager.resetForm()
                }
                is SuratBedaIdentitasResult.Error -> {
                    stateManager.errorMessage = result.message
                    eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.SubmitError(result.message))
                }
            }
        } catch (e: Exception) {
            stateManager.errorMessage = e.message ?: "Terjadi kesalahan"
            eventEmitter(SKBedaIdentitasViewModel.SKBedaIdentitasEvent.SubmitError(stateManager.errorMessage!!))
        } finally {
            stateManager.isLoading = false
        }
    }
}