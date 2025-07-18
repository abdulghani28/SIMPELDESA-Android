package com.cvindosistem.simpeldesa.main.domain.model

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratDetailResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.SuratListResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBedaIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBelumMemilikiPBBResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBerpergianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKBiodataWargaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliPerusahaanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKGhaibResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKIzinOrangTuaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKIzinTidakMasukKerjaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKJamkesosResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKJandaDudaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKJualBeliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKTPDalamProsesResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKelahiranResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKematianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKepemilikanKendaraanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKKepemilikanTanahResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKLahirMatiResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKNikahNonMuslimResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKPenghasilanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKPergiKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKResiKTPSementaraResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKStatusPerkawinanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKTidakMampuResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKWaliHakimResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratlainnya.SuratKuasaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratlainnya.SuratTugasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPCatatanKepolisianResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPKehilanganResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPPermohonanPenerbitanBukuPasLintasBatasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPPernikahanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar.SPPindahDomisiliResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMAktaLahirResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMBelumMemilikiAktaLahirResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMDuplikatKelahiranResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMDuplikatSuratNikahResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMKartuKeluargaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan.SPMPerubahanKKResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpernyataan.SPNPenguasaanFisikBidangTanahResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratrekomendasi.SRKeramaianResponse

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

sealed class SuratGhaibResult {
    data class Success(val data: SKGhaibResponse) : SuratGhaibResult()
    data class Error(val message: String) : SuratGhaibResult()
}

sealed class SuratKehilanganResult {
    data class Success(val data: SPKehilanganResponse) : SuratKehilanganResult()
    data class Error(val message: String) : SuratKehilanganResult()
}

sealed class SuratKelahiranResult {
    data class Success(val data: SKKelahiranResponse) : SuratKelahiranResult()
    data class Error(val message: String) : SuratKelahiranResult()
}

sealed class SuratKematianResult {
    data class Success(val data: SKKematianResponse) : SuratKematianResult()
    data class Error(val message: String) : SuratKematianResult()
}

sealed class SuratKeramaianResult {
    data class Success(val data: SRKeramaianResponse) : SuratKeramaianResult()
    data class Error(val message: String) : SuratKeramaianResult()
}

sealed class SuratKuasaResult {
    data class Success(val data: SuratKuasaResponse) : SuratKuasaResult()
    data class Error(val message: String) : SuratKuasaResult()
}

sealed class SuratPenghasilanResult {
    data class Success(val data: SKPenghasilanResponse) : SuratPenghasilanResult()
    data class Error(val message: String) : SuratPenghasilanResult()
}

sealed class SuratPernikahanResult {
    data class Success(val data: SPPernikahanResponse) : SuratPernikahanResult()
    data class Error(val message: String) : SuratPernikahanResult()
}

sealed class SuratPindahDomisiliResult {
    data class Success(val data: SPPindahDomisiliResponse) : SuratPindahDomisiliResult()
    data class Error(val message: String) : SuratPindahDomisiliResult()
}

sealed class SuratResiKTPSementaraResult {
    data class Success(val data: SKResiKTPSementaraResponse) : SuratResiKTPSementaraResult()
    data class Error(val message: String) : SuratResiKTPSementaraResult()
}

sealed class SuratSKCKResult {
    data class Success(val data: SPCatatanKepolisianResponse) : SuratSKCKResult()
    data class Error(val message: String) : SuratSKCKResult()
}

sealed class SuratStatusPerkawinanResult {
    data class Success(val data: SKStatusPerkawinanResponse) : SuratStatusPerkawinanResult()
    data class Error(val message: String) : SuratStatusPerkawinanResult()
}

sealed class SuratTidakMampuResult {
    data class Success(val data: SKTidakMampuResponse) : SuratTidakMampuResult()
    data class Error(val message: String) : SuratTidakMampuResult()
}

sealed class SuratUsahaResult {
    data class Success(val data: SKUsahaResponse) : SuratUsahaResult()
    data class Error(val message: String) : SuratUsahaResult()
}

sealed class SuratTugasResult {
    data class Success(val data: SuratTugasResponse) : SuratTugasResult()
    data class Error(val message: String) : SuratTugasResult()
}

sealed class SuratIzinTidakKerjaResult {
    data class Success(val data: SKIzinTidakMasukKerjaResponse) : SuratIzinTidakKerjaResult()
    data class Error(val message: String) : SuratIzinTidakKerjaResult()
}

sealed class SuratBelumMemilikiPBBResult {
    data class Success(val data: SKBelumMemilikiPBBResponse) : SuratBelumMemilikiPBBResult()
    data class Error(val message: String) : SuratBelumMemilikiPBBResult()
}

sealed class SuratJamkesosResult {
    data class Success(val data: SKJamkesosResponse) : SuratJamkesosResult()
    data class Error(val message: String) : SuratJamkesosResult()
}

sealed class SuratJualBeliResult {
    data class Success(val data: SKJualBeliResponse) : SuratJualBeliResult()
    data class Error(val message: String) : SuratJualBeliResult()
}

sealed class SuratKTPDalamProsesResult {
    data class Success(val data: SKKTPDalamProsesResponse) : SuratKTPDalamProsesResult()
    data class Error(val message: String) : SuratKTPDalamProsesResult()
}

sealed class SuratLahirMatiResult {
    data class Success(val data: SKLahirMatiResponse) : SuratLahirMatiResult()
    data class Error(val message: String) : SuratLahirMatiResult()
}

sealed class SuratPergiKawinResult {
    data class Success(val data: SKPergiKawinResponse) : SuratPergiKawinResult()
    data class Error(val message: String) : SuratPergiKawinResult()
}

sealed class SuratWaliHakimResult {
    data class Success(val data: SKWaliHakimResponse) : SuratWaliHakimResult()
    data class Error(val message: String) : SuratWaliHakimResult()
}

sealed class SuratKepemilikanKendaraanResult {
    data class Success(val data: SKKepemilikanKendaraanResponse) : SuratKepemilikanKendaraanResult()
    data class Error(val message: String) : SuratKepemilikanKendaraanResult()
}

sealed class SuratAktaLahirResult {
    data class Success(val data: SPMAktaLahirResponse) : SuratAktaLahirResult()
    data class Error(val message: String) : SuratAktaLahirResult()
}

sealed class SuratBelumMemilikiAktaLahirResult {
    data class Success(val data: SPMBelumMemilikiAktaLahirResponse) : SuratBelumMemilikiAktaLahirResult()
    data class Error(val message: String) : SuratBelumMemilikiAktaLahirResult()
}

sealed class SuratDuplikatKelahiranResult {
    data class Success(val data: SPMDuplikatKelahiranResponse) : SuratDuplikatKelahiranResult()
    data class Error(val message: String) : SuratDuplikatKelahiranResult()
}

sealed class SuratDuplikatSuratNikahResult {
    data class Success(val data: SPMDuplikatSuratNikahResponse) : SuratDuplikatSuratNikahResult()
    data class Error(val message: String) : SuratDuplikatSuratNikahResult()
}

sealed class SPMCeraiResult {
    data class Success(val data: SPMCeraiResponse) : SPMCeraiResult()
    data class Error(val message: String) : SPMCeraiResult()
}

sealed class SKPengantarCeraiRujukResult {
    data class Success(val data: SKPengantarCeraiRujukResponse) : SKPengantarCeraiRujukResult()
    data class Error(val message: String) : SKPengantarCeraiRujukResult()
}

sealed class SPMKartuKeluargaResult {
    data class Success(val data: SPMKartuKeluargaResponse) : SPMKartuKeluargaResult()
    data class Error(val message: String) : SPMKartuKeluargaResult()
}

sealed class SKIzinOrangTuaResult {
    data class Success(val data: SKIzinOrangTuaResponse) : SKIzinOrangTuaResult()
    data class Error(val message: String) : SKIzinOrangTuaResult()
}

sealed class SPNPenguasaanFisikBidangTanahResult {
    data class Success(val data: SPNPenguasaanFisikBidangTanahResponse) : SPNPenguasaanFisikBidangTanahResult()
    data class Error(val message: String) : SPNPenguasaanFisikBidangTanahResult()
}

sealed class SPMPerubahanKKResult {
    data class Success(val data: SPMPerubahanKKResponse) : SPMPerubahanKKResult()
    data class Error(val message: String) : SPMPerubahanKKResult()
}

sealed class SKKepemilikanTanahResult {
    data class Success(val data: SKKepemilikanTanahResponse) : SKKepemilikanTanahResult()
    data class Error(val message: String) : SKKepemilikanTanahResult()
}

sealed class SKBiodataWargaResult {
    data class Success(val data: SKBiodataWargaResponse) : SKBiodataWargaResult()
    data class Error(val message: String) : SKBiodataWargaResult()
}

sealed class SPPermohonanPenerbitanBukuPasLintasBatasResult {
    data class Success(val data: SPPermohonanPenerbitanBukuPasLintasBatasResponse) : SPPermohonanPenerbitanBukuPasLintasBatasResult()
    data class Error(val message: String) : SPPermohonanPenerbitanBukuPasLintasBatasResult()
}

sealed class SKNikahNonMuslimResult {
    data class Success(val data: SKNikahNonMuslimResponse) : SKNikahNonMuslimResult()
    data class Error(val message: String) : SKNikahNonMuslimResult()
}
