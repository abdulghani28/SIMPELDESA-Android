package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMasukKerjaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMasukKerjaUseCase

class SKTidakMasukKerjaFormSubmitter(
    private val createSKTidakMasukKerjaUseCase: CreateSuratTidakMasukKerjaUseCase,
    private val stateManager: SKTidakMasukKerjaStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoading(true)
            stateManager.updateErrorMessage(null)

            val request = SKIzinTidakMasukKerjaRequest(
                disahkan_oleh = "",
                agama_id = stateManager.agamaIdValue,
                alamat = stateManager.alamatValue,
                alasan_izin = stateManager.alasanIzinValue,
                jabatan = stateManager.jabatanValue,
                jenis_kelamin = stateManager.jenisKelaminValue,
                keperluan = stateManager.keperluanValue,
                lama = stateManager.lamaValue,
                nama = stateManager.namaValue,
                nama_perusahaan = stateManager.namaPerusahaanValue,
                nik = stateManager.nikValue,
                pekerjaan = stateManager.pekerjaanValue,
                tanggal_lahir = stateManager.tanggalLahirValue,
                tempat_lahir = stateManager.tempatLahirValue,
                terhitung_dari = stateManager.terhitungDariValue
            )

            when (val result = createSKTidakMasukKerjaUseCase(request)) {
                is SuratTidakMasukKerjaResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratTidakMasukKerjaResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Terjadi kesalahan"
            stateManager.updateErrorMessage(message)
            Result.failure(e)
        } finally {
            stateManager.updateLoading(false)
        }
    }
}