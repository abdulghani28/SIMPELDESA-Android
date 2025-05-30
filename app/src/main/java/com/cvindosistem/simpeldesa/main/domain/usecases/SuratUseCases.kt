package com.cvindosistem.simpeldesa.main.domain.usecases

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBerpergianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJandaDudaRequest
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
    suspend operator fun invoke(request: SKBedaIdentitasRequest): SuratBedaIdentitasResult {
        return suratRepository.createSuratBedaIdentitas(request)
    }
}

class CreateSuratBerpergianUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKBerpergianRequest): SuratBerpergianResult {
        return suratRepository.createSuratBepergian(request)
    }
}

class CreateSuratDomisiliUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKDomisiliRequest): SuratDomisiliResult {
        return suratRepository.createSuratDomisili(request)
    }
}

class CreateSuratDomisiliPerusahaanUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKDomisiliPerusahaanRequest): SuratDomisiliPerusahaanResult {
        return suratRepository.createSuratDomisiliPerusahaan(request)
    }
}

class CreateSuratTidakMasukKerjaUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKIzinTidakMasukKerjaRequest): SuratTidakMasukKerjaResult {
        return suratRepository.createSuratTidakMasukKerja(request)
    }
}

class CreateSuratJandaDudaUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKJandaDudaRequest): SuratJandaDudaResult {
        return suratRepository.createSuratJandaDuda(request)
    }
}
