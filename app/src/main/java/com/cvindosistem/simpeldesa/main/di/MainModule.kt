package com.cvindosistem.simpeldesa.main.di

import com.cvindosistem.simpeldesa.main.data.remote.api.ReferensiApi
import com.cvindosistem.simpeldesa.main.data.remote.api.SuratApi
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepository
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepositoryImpl
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepository
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepositoryImpl
import com.cvindosistem.simpeldesa.main.domain.usecases.BidangUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateResiKTPSementaraUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratAktaLahirUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBedaIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBelumMemilikiAktaLahirUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBelumMemilikiPBBUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBerpergianUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliPerusahaanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDuplikatKelahiranUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDuplikatSuratNikahUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratGhaibUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJamkesosUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJandaDudaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJualBeliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKTPDalamProsesUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKehilanganUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKelahiranUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKematianUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKepemilikanKendaraanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeramaianUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganBiodataWargaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganIzinOrangTuaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganKepemilikanTanahUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganNikahNonMuslimUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKuasaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratLahirMatiUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPengantarCeraiRujukUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPengantarPasLintasBatasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPenghasilanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPergiKawinUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanCeraiUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanKartuKeluargaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanPerubahanKKUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernikahanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernyataanSporadikUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPindahDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratSKCKUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratStatusPerkawinanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMampuUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMasukKerjaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTugasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratWaliHakimUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetDisabilitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetHubunganUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPendidikanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPerbedaanIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratDetailUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratListUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetTercantumIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.JenisUsahaUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetailViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.catatankepolisian.SPCatatanKepolisianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.pernikahan.SPPernikahanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi.keramaian.SRKeramaianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas.SKBedaIdentitasViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.belummemilikipbb.SKBelumMemilikiPBBViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.berpergian.SKBerpergianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata.SKBiodataWargaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan.SKDomisiliPerusahaanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili.SKDomisiliViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ghaib.SKGhaibViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua.SKIzinOrangTuaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jamkesos.SKJamkesosViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jandaduda.SKJandaDudaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli.SKJualBeliViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran.SKKelahiranViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian.SKKematianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikankendaraan.SKKepemilikanKendaraanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah.SKKepemilikanTanahViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ktpdalamproses.SKKTPDalamProsesViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati.SKLahirMatiViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pengantarcerairujuk.SKPengantarCeraiRujukViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.penghasilan.SKPenghasilanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pergikawin.SKPergiKawinViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.resiktpsementara.SKResiKTPSementaraViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.statusperkawinan.SKStatusPerkawinanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmampu.SKTidakMampuViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja.SKTidakMasukKerjaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.usaha.SKUsahaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.walihakim.SKWaliHakimViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.kuasa.SuratKuasaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.tugas.SuratTugasViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.kehilangan.SPKehilanganViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku.SPPermohonanPenerbitanBukuPasLintasBatasViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir.SPMAktaLahirViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.belummemilikiaktalahir.SPMBelumMemilikiAktaLahirViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.cerai.SPMCeraiViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatkelahiran.SPMDuplikatKelahiranViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatsuratnikah.SPMDuplikatSuratNikahViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.kartukeluarga.SPMKartuKeluargaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.perubahankk.SPMPerubahanKKViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah.SPNPenguasaanFisikBidangTanahViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Modul utama (mainModule) untuk dependency injection menggunakan Koin.
 *
 * Semua komponen yang digunakan dalam package `main` harus dideklarasikan di sini agar bisa di-*inject* dengan benar,
 * termasuk:
 * - API Service (Retrofit interface)
 * - Repository (implementasi dan interface)
 * - Use Case (untuk mengatur logika domain per jenis surat)
 * - ViewModel (untuk semua form dan tampilan surat)
 *
 * Komponen ini digunakan untuk memastikan seluruh dependensi diatur secara terpusat dan mudah dikelola.
 *
 * Aturan:
 * - Jika menambahkan `UseCase` baru, daftarkan di sini.
 * - Jika menambahkan `ViewModel` baru (misalnya untuk jenis surat baru), daftarkan di sini.
 * - Semua ViewModel harus menggunakan `viewModel { ... }` agar bisa digunakan oleh Jetpack Compose / Fragment.
 * - Selalu gunakan interface untuk Repository agar implementasi mudah diuji atau diganti.
 *
 * Contoh penambahan baru:
 * ```kotlin
 * single { CreateSuratBaruUseCase(get()) }
 * viewModel { SuratBaruViewModel(get(), get()) }
 * ```
 */

/**
 * Modul utama untuk Dependency Injection menggunakan Koin.
 *
 * Modul ini mencakup:
 * - Inisialisasi Retrofit API (SuratApi, ReferensiApi)
 * - Registrasi Gson untuk parsing JSON
 * - Registrasi Repository (SuratRepository, ReferensiRepository)
 * - Registrasi semua UseCase untuk fitur surat dan referensi
 * - Registrasi semua ViewModel yang digunakan dalam aplikasi
 *
 * Semua class yang terdapat di dalam package `main` seperti:
 * - UseCase
 * - Repository
 * - ViewModel
 * harus didaftarkan di dalam `mainModule` agar dapat diinject oleh Koin.
 *
 * Pastikan setiap penambahan fitur baru dalam package `main` juga menambahkan
 * dependensinya di sini, agar tersedia secara otomatis untuk layer yang membutuhkannya.
 */
val mainModule = module {
    // API
    single { get<Retrofit>().create(SuratApi::class.java) }
    single { get<Retrofit>().create(ReferensiApi::class.java) }

    // JSON Parser
    single { Gson() }

    // Repository
    single<SuratRepository> { SuratRepositoryImpl(get()) }
    single<ReferensiRepository> { ReferensiRepositoryImpl(get()) }

    // UseCases - Surat
    single { GetSuratListUseCase(get()) }
    single { GetSuratDetailUseCase(get()) }
    single { CreateSuratDomisiliUseCase(get()) }
    single { CreateSuratDomisiliPerusahaanUseCase(get()) }
    single { CreateSuratPindahDomisiliUseCase(get()) }
    single { CreateSuratKelahiranUseCase(get()) }
    single { CreateSuratKematianUseCase(get()) }
    single { CreateSuratAktaLahirUseCase(get()) }
    single { CreateSuratBelumMemilikiAktaLahirUseCase(get()) }
    single { CreateSuratDuplikatKelahiranUseCase(get()) }
    single { CreateSuratKTPDalamProsesUseCase(get()) }
    single { CreateResiKTPSementaraUseCase(get()) }
    single { CreateSuratPermohonanKartuKeluargaUseCase(get()) }
    single { CreateSuratPermohonanPerubahanKKUseCase(get()) }
    single { CreateSuratPernikahanUseCase(get()) }
    single { CreateSuratStatusPerkawinanUseCase(get()) }
    single { CreateSuratJandaDudaUseCase(get()) }
    single { CreateSuratPergiKawinUseCase(get()) }
    single { CreateSuratWaliHakimUseCase(get()) }
    single { CreateSuratDuplikatSuratNikahUseCase(get()) }
    single { CreateSuratKeteranganNikahNonMuslimUseCase(get()) }
    single { CreateSuratPermohonanCeraiUseCase(get()) }
    single { CreateSuratPengantarCeraiRujukUseCase(get()) }
    single { CreateSuratKeteranganIzinOrangTuaUseCase(get()) }
    single { CreateSuratPenghasilanUseCase(get()) }
    single { CreateSuratUsahaUseCase(get()) }
    single { CreateSuratKuasaUseCase(get()) }
    single { CreateSuratTugasUseCase(get()) }
    single { CreateSuratTidakMasukKerjaUseCase(get()) }
    single { CreateSuratKehilanganUseCase(get()) }
    single { CreateSuratSKCKUseCase(get()) }
    single { CreateSuratTidakMampuUseCase(get()) }
    single { CreateSuratJamkesosUseCase(get()) }
    single { CreateSuratBerpergianUseCase(get()) }
    single { CreateSuratKeramaianUseCase(get()) }
    single { CreateSuratPernyataanSporadikUseCase(get()) }
    single { CreateSuratKeteranganKepemilikanTanahUseCase(get()) }
    single { CreateSuratKepemilikanKendaraanUseCase(get()) }
    single { CreateSuratGhaibUseCase(get()) }
    single { CreateSuratLahirMatiUseCase(get()) }
    single { CreateSuratBelumMemilikiPBBUseCase(get()) }
    single { CreateSuratJualBeliUseCase(get()) }
    single { CreateSuratBedaIdentitasUseCase(get()) }
    single { CreateSuratPengantarPasLintasBatasUseCase(get()) }
    single { CreateSuratKeteranganBiodataWargaUseCase(get()) }


    // UseCases - Referensi
    single { GetAgamaUseCase(get()) }
    single { GetStatusKawinUseCase(get()) }
    single { GetTercantumIdentitasUseCase(get()) }
    single { GetPerbedaanIdentitasUseCase(get()) }
    single { JenisUsahaUseCase(get()) }
    single { BidangUsahaUseCase(get()) }
    single { GetHubunganUseCase(get()) }
    single { GetPendidikanUseCase(get()) }
    single { GetDisabilitasUseCase(get()) }

    // ViewModel - Surat
    viewModel { SuratSayaViewModel(get()) }
    viewModel { SuratDetailViewModel(get()) }

    // ViewModel - SR (Surat Rekomendasi)
    viewModel { SRKeramaianViewModel(get(), get()) }

    // ViewModel - SP (Surat Pernyataan)
    viewModel { SPCatatanKepolisianViewModel(get(), get()) }
    viewModel { SPPernikahanViewModel(get(), get(), get(), get()) }
    viewModel { SPKehilanganViewModel(get(), get()) }

    // ViewModel - Surat Lainnya
    viewModel { SuratKuasaViewModel(get(), get()) }
    viewModel { SuratTugasViewModel(get(), get()) }

    // ViewModel - SK (Surat Keterangan)
    viewModel { SKTidakMampuViewModel(get(), get(), get(), get()) }
    viewModel { SKResiKTPSementaraViewModel(get(), get(), get()) }
    viewModel { SKStatusPerkawinanViewModel(get(), get(), get(), get()) }
    viewModel { SKDomisiliViewModel(get(), get(), get()) }
    viewModel { SKUsahaViewModel(get(), get(), get(), get()) }
    viewModel { SKTidakMasukKerjaViewModel(get(), get(), get()) }
    viewModel { SKPenghasilanViewModel(get(), get()) }
    viewModel { SKKematianViewModel(get(), get()) }
    viewModel { SKKelahiranViewModel(get(), get()) }
    viewModel { SKJandaDudaViewModel(get(), get(), get()) }
    viewModel { SKGhaibViewModel(get(), get()) }
    viewModel { SKDomisiliPerusahaanViewModel(get(), get(), get(), get(), get()) }
    viewModel { SKBerpergianViewModel(get(), get()) }
    viewModel { SKBedaIdentitasViewModel(get(), get(), get(), get()) }
    viewModel { SKBelumMemilikiPBBViewModel(get(), get(), get(), get()) }
    viewModel { SKBiodataWargaViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SKJamkesosViewModel(get(), get(), get(), get(), get()) }
    viewModel { SKIzinOrangTuaViewModel(get(), get(), get()) }
    viewModel { SKJualBeliViewModel(get(), get()) }
    viewModel { SKKepemilikanKendaraanViewModel(get(), get()) }
    viewModel { SKKepemilikanTanahViewModel(get(), get()) }
    viewModel { SKKTPDalamProsesViewModel(get(), get(), get(), get()) }
    viewModel { SKLahirMatiViewModel(get(), get(), get(), get()) }
    viewModel { SKNikahWargaNonMuslimViewModel(get(), get(), get(), get(), get()) }
    viewModel { SKPengantarCeraiRujukViewModel(get(), get(), get()) }
    viewModel { SKPergiKawinViewModel(get(), get(), get(), get(), get()) }
    viewModel { SKWaliHakimViewModel(get(), get(), get(), get(), get()) }

    // ViewModel - SPM (Surat Permohonan)
    viewModel { SPMAktaLahirViewModel(get(), get()) }
    viewModel { SPMBelumMemilikiAktaLahirViewModel(get(), get()) }
    viewModel { SPMCeraiViewModel(get(), get(), get()) }
    viewModel { SPMDuplikatKelahiranViewModel(get(), get(), get()) }
    viewModel { SPMDuplikatSuratNikahViewModel(get(), get()) }
    viewModel { SPMKartuKeluargaViewModel(get(), get(), get()) }
    viewModel { SPMPerubahanKKViewModel(get(), get(), get()) }
    viewModel { SPPermohonanPenerbitanBukuPasLintasBatasViewModel(get(), get(), get(), get()) }

    // ViewModel - SPN (Surat Pernyataan)
    viewModel { SPNPenguasaanFisikBidangTanahViewModel(get(), get()) }
}