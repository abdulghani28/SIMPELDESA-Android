package com.cvindosistem.simpeldesa.main.domain.usecases

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBerpergianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKGhaibRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJandaDudaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKelahiranRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKematianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPenghasilanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKResiKTPSementaraRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKStatusPerkawinanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKTidakMampuRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKUsahaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratKuasaRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratTugasRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPCatatanKepolisianRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPernikahanRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPindahDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepository
import com.cvindosistem.simpeldesa.main.domain.model.SuratBedaIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBerpergianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDetailResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliPerusahaanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratGhaibResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratIzinTidakKerjaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJandaDudaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKehilanganResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKematianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKeramaianResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKuasaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratListResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPenghasilanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPernikahanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPindahDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratResiKTPSementaraResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratSKCKResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratStatusPerkawinanResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMampuResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMasukKerjaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTugasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratUsahaResult

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

class CreateSuratGhaibUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKGhaibRequest): SuratGhaibResult {
        return suratRepository.createSuratGhaib(request)
    }
}

class CreateSuratKehilanganUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SPKehilanganRequest): SuratKehilanganResult {
        return suratRepository.createSuratKehilangan(request)
    }
}

class CreateSuratKelahiranUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKKelahiranRequest): SuratKelahiranResult {
        return suratRepository.createSuratKelahiran(request)
    }
}

class CreateSuratKematianUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKKematianRequest): SuratKematianResult {
        return suratRepository.createSuratKematian(request)
    }
}

class CreateSuratKeramaianUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SRKeramaianRequest): SuratKeramaianResult {
        return suratRepository.createSuratKeramaian(request)
    }
}

class CreateSuratKuasaUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratKuasaRequest): SuratKuasaResult {
        return suratRepository.createSuratKuasa(request)
    }
}

class CreateSuratPenghasilanUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKPenghasilanRequest): SuratPenghasilanResult {
        return suratRepository.createSuratPenghasilan(request)
    }
}

class CreateSuratPernikahanUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SPPernikahanRequest): SuratPernikahanResult {
        return suratRepository.createSuratPernikahan(request)
    }
}

class CreateSuratPindahDomisiliUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SPPindahDomisiliRequest): SuratPindahDomisiliResult {
        return suratRepository.createSuratPindahDomisili(request)
    }
}

class CreateResiKTPSementaraUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKResiKTPSementaraRequest): SuratResiKTPSementaraResult {
        return suratRepository.createResiKTPSementara(request)
    }
}

class CreateSuratSKCKUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SPCatatanKepolisianRequest): SuratSKCKResult {
        return suratRepository.createSuratSKCK(request)
    }
}

class CreateSuratStatusPerkawinanUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKStatusPerkawinanRequest): SuratStatusPerkawinanResult {
        return suratRepository.createSuratStatusPerkawinan(request)
    }
}

class CreateSuratTidakMampuUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKTidakMampuRequest): SuratTidakMampuResult {
        return suratRepository.createSuratTidakMampu(request)
    }
}

class CreateSuratUsahaUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKUsahaRequest): SuratUsahaResult {
        return suratRepository.createSuratUsaha(request)
    }
}

class CreateSuratTugasUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SuratTugasRequest): SuratTugasResult {
        return suratRepository.createSuratTugas(request)
    }
}

class CreateSuratIzinTidakKerjaUseCase(private val suratRepository: SuratRepository) {
    suspend operator fun invoke(request: SKIzinTidakMasukKerjaRequest): SuratIzinTidakKerjaResult {
        return suratRepository.createSuratIzinTidakKerja(request)
    }
}