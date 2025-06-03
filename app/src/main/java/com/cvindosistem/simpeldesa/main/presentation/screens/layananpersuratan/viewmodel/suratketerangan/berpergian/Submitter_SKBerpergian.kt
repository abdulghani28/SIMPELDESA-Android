package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.berpergian

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBerpergianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratBerpergianResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBerpergianUseCase

class SKBerpergianFormSubmitter(
    private val createSKBerpergianUseCase: CreateSuratBerpergianUseCase
) {
    suspend fun submitForm(formData: SKBerpergianStateManager.FormData): Result<Unit> {
        return try {
            val request = SKBerpergianRequest(
                // Step 1 - Informasi Pelapor
                nik = formData.nik,
                nama = formData.nama,
                tempat_lahir = formData.tempatLahir,
                tanggal_lahir = formData.tanggalLahir,
                jenis_kelamin = formData.jenisKelamin,
                pekerjaan = formData.pekerjaan,
                alamat = formData.alamat,

                // Step 2 - Informasi Kepergian
                tempat_tujuan = formData.tempatTujuan,
                maksud_tujuan = formData.maksudTujuan,
                tanggal_keberangkatan = formData.tanggalKeberangkatan,
                lama = formData.lama,
                satuan_lama = formData.satuanLama,
                jumlah_pengikut = formData.jumlahPengikut.toIntOrNull() ?: 0,

                // Step 3 - Informasi Pelengkap
                keperluan = formData.keperluan,

                // Other fields (set by backend or default)
                disahkan_oleh = ""
            )

            when (val result = createSKBerpergianUseCase(request)) {
                is SuratBerpergianResult.Success -> {
                    Result.success(Unit)
                }
                is SuratBerpergianResult.Error -> {
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
