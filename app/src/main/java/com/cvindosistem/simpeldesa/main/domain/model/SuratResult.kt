package com.cvindosistem.simpeldesa.main.domain.model

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratDetailResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBedaIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBerpergianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliPerusahaanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKIzinTidakMasukKerjaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKJandaDudaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratListResponse

sealed class SuratListResult {
    data class Success(val data: SuratListResponse) : SuratListResult()
    data class Error(val message: String) : SuratListResult()
}

sealed class SuratDetailResult {
    data class Success(val data: SuratDetailResponse) : SuratDetailResult()
    data class Error(val message: String) : SuratDetailResult()
}

sealed class SuratBedaIdentitasResult {
    data class Success(val data: SKBedaIdentitasResponse) : SuratBedaIdentitasResult()
    data class Error(val message: String) : SuratBedaIdentitasResult()
}

sealed class SuratBerpergianResult {
    data class Success(val data: SKBerpergianResponse) : SuratBerpergianResult()
    data class Error(val message: String) : SuratBerpergianResult()
}

sealed class SuratDomisiliResult {
    data class Success(val data: SKDomisiliResponse) : SuratDomisiliResult()
    data class Error(val message: String) : SuratDomisiliResult()
}

sealed class SuratDomisiliPerusahaanResult {
    data class Success(val data: SKDomisiliPerusahaanResponse) : SuratDomisiliPerusahaanResult()
    data class Error(val message: String) : SuratDomisiliPerusahaanResult()
}

sealed class SuratTidakMasukKerjaResult {
    data class Success(val data: SKIzinTidakMasukKerjaResponse) : SuratTidakMasukKerjaResult()
    data class Error(val message: String) : SuratTidakMasukKerjaResult()
}

sealed class SuratJandaDudaResult {
    data class Success(val data: SKJandaDudaResponse) : SuratJandaDudaResult()
    data class Error(val message: String) : SuratJandaDudaResult()
}