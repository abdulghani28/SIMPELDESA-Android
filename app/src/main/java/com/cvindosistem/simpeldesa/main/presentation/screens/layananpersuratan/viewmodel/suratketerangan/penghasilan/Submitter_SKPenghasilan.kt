package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.penghasilan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPenghasilanRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratPenghasilanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPenghasilanUseCase

class SKPenghasilanFormSubmitter(
    private val createSKPenghasilanUseCase: CreateSuratPenghasilanUseCase
) {
    var isLoading by mutableStateOf(false)
        private set

    suspend fun submitForm(stateManager: SKPenghasilanStateManager): Result<Unit> = try {
        isLoading = true

        val request = SKPenghasilanRequest(
            disahkan_oleh = "",
            alamat = stateManager.alamatValue,
            alamat_ortu = stateManager.alamatOrtuValue,
            jenis_kelamin_anak = stateManager.jenisKelaminAnakValue,
            jenis_kelamin_ortu = stateManager.jenisKelaminOrtuValue,
            kelas_anak = stateManager.kelasAnakValue,
            keperluan = stateManager.keperluanValue,
            nama = stateManager.namaValue,
            nama_anak = stateManager.namaAnakValue,
            nama_ortu = stateManager.namaOrtuValue,
            nama_sekolah_anak = stateManager.namaSekolahAnakValue,
            nik = stateManager.nikValue,
            nik_anak = stateManager.nikAnakValue,
            nik_ortu = stateManager.nikOrtuValue,
            pekerjaan_ortu = stateManager.pekerjaanOrtuValue,
            penghasilan_ortu = stateManager.penghasilanOrtuValue,
            tanggal_lahir_anak = stateManager.tanggalLahirAnakValue,
            tanggal_lahir_ortu = stateManager.tanggalLahirOrtuValue,
            tanggungan_ortu = stateManager.tanggunganOrtuValue,
            tempat_lahir_anak = stateManager.tempatLahirAnakValue,
            tempat_lahir_ortu = stateManager.tempatLahirOrtuValue
        )

        when (val result = createSKPenghasilanUseCase(request)) {
            is SuratPenghasilanResult.Success -> {
                Result.success(Unit)
            }
            is SuratPenghasilanResult.Error -> {
                Result.failure(Exception(result.message))
            }
        }
    } catch (e: Exception) {
        Result.failure(e)
    } finally {
        isLoading = false
    }
}