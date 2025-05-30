package com.cvindosistem.simpeldesa.main.domain.usecases

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganBerpergianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.SuratKeteranganJandaDudaRequest
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepository
import com.cvindosistem.simpeldesa.main.domain.model.SuratBedaIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBerpergianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDetailResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliPerusahaanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJandaDudaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratListResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMasukKerjaResult

class GetSuratListUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(
        page: Int? = null,
        limit: Int? = null,
        search: String? = null,
        jenisSurat: String? = null,
        status: String? = null,
        startDate: String? = null,
        endDate: String? = null
    ): SuratListResult {
        return suratRepository.getSuratList(page, limit, search, jenisSurat, status, startDate, endDate)
    }
}

class GetSuratDetailUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(id: String): SuratDetailResult {
        return suratRepository.getSuratDetail(id)
    }
}

class CreateSuratBedaIdentitasUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratKeteranganBedaIdentitasRequest): SuratBedaIdentitasResult {
        return suratRepository.createSuratBedaIdentitas(request)
    }
}

class CreateSuratBerpergianUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratKeteranganBerpergianRequest): SuratBerpergianResult {
        return suratRepository.createSuratBepergian(request)
    }
}

class CreateSuratDomisiliUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratKeteranganDomisiliRequest): SuratDomisiliResult {
        return suratRepository.createSuratDomisili(request)
    }
}

class CreateSuratDomisiliPerusahaanUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratKeteranganDomisiliPerusahaanRequest): SuratDomisiliPerusahaanResult {
        return suratRepository.createSuratDomisiliPerusahaan(request)
    }
}

class CreateSuratTidakMasukKerjaUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratIzinTidakMasukKerjaRequest): SuratTidakMasukKerjaResult {
        return suratRepository.createSuratTidakMasukKerja(request)
    }
}

class CreateSuratJandaDudaUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratKeteranganJandaDudaRequest): SuratJandaDudaResult {
        return suratRepository.createSuratJandaDuda(request)
    }
}
