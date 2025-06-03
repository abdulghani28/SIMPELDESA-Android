package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKelahiranRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKelahiranUseCase

class SKKelahiranFormSubmitter(
    private val createSKKelahiranUseCase: CreateSuratKelahiranUseCase
) {
    suspend fun submitForm(
        stateManager: SKKelahiranStateManager
    ): SuratKelahiranResult {
        stateManager.updateLoading(true)
        stateManager.updateErrorMessage(null)

        return try {
            val request = SKKelahiranRequest(
                alamat_ayah = stateManager.alamatAyahValue,
                alamat_ibu = stateManager.alamatIbuValue,
                anak_ke = stateManager.anakKeValue,
                disahkan_oleh = "",
                jam_lahir = stateManager.jamLahirValue,
                jenis_kelamin = stateManager.jenisKelaminValue,
                keperluan = stateManager.keperluanValue,
                nama = stateManager.namaValue,
                nama_ayah = stateManager.namaAyahValue,
                nama_ibu = stateManager.namaIbuValue,
                nik_ayah = stateManager.nikAyahValue,
                nik_ibu = stateManager.nikIbuValue,
                tanggal_lahir = stateManager.tanggalLahirValue,
                tanggal_lahir_ayah = stateManager.tanggalLahirAyahValue,
                tanggal_lahir_ibu = stateManager.tanggalLahirIbuValue,
                tempat_lahir = stateManager.tempatLahirValue,
                tempat_lahir_ayah = stateManager.tempatLahirAyahValue,
                tempat_lahir_ibu = stateManager.tempatLahirIbuValue
            )

            createSKKelahiranUseCase(request)
        } catch (e: Exception) {
            val errorMessage = e.message ?: "Terjadi kesalahan"
            stateManager.updateErrorMessage(errorMessage)
            SuratKelahiranResult.Error(errorMessage)
        } finally {
            stateManager.updateLoading(false)
        }
    }
}