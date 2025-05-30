package com.cvindosistem.simpeldesa.main.domain.model

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratDetailResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganBedaIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganBerpergianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganDomisiliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganDomisiliPerusahaanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganIzinTidakMasukKerjaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratKeteranganJandaDudaResponse
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
    data class Success(val data: SuratKeteranganBedaIdentitasResponse) : SuratBedaIdentitasResult()
    data class Error(val message: String) : SuratBedaIdentitasResult()
}

sealed class SuratBerpergianResult {
    data class Success(val data: SuratKeteranganBerpergianResponse) : SuratBerpergianResult()
    data class Error(val message: String) : SuratBerpergianResult()
}

sealed class SuratDomisiliResult {
    data class Success(val data: SuratKeteranganDomisiliResponse) : SuratDomisiliResult()
    data class Error(val message: String) : SuratDomisiliResult()
}

sealed class SuratDomisiliPerusahaanResult {
    data class Success(val data: SuratKeteranganDomisiliPerusahaanResponse) : SuratDomisiliPerusahaanResult()
    data class Error(val message: String) : SuratDomisiliPerusahaanResult()
}

sealed class SuratTidakMasukKerjaResult {
    data class Success(val data: SuratKeteranganIzinTidakMasukKerjaResponse) : SuratTidakMasukKerjaResult()
    data class Error(val message: String) : SuratTidakMasukKerjaResult()
}

sealed class SuratJandaDudaResult {
    data class Success(val data: SuratKeteranganJandaDudaResponse) : SuratJandaDudaResult()
    data class Error(val message: String) : SuratJandaDudaResult()
}