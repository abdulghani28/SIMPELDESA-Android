package com.cvindosistem.simpeldesa.main.di

import com.cvindosistem.simpeldesa.main.data.remote.api.ReferensiApi
import com.cvindosistem.simpeldesa.main.data.remote.api.SuratApi
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepository
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepositoryImpl
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepository
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepositoryImpl
import com.cvindosistem.simpeldesa.main.domain.usecases.BidangUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateResiKTPSementaraUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBedaIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBerpergianUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliPerusahaanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratGhaibUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratIzinTidakKerjaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJandaDudaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKehilanganUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKelahiranUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKematianUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeramaianUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKuasaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPenghasilanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernikahanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPindahDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratSKCKUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratStatusPerkawinanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMampuUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMasukKerjaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTugasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratDetailUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratListUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.JenisUsahaUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetailViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPCatatanKepolisianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPPernikahanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi.SRKeramaianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKBedaIdentitasViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKBerpergianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliPerusahaanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKGhaibViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKJandaDudaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKKelahiranViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKKematianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKPenghasilanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKResiKTPSementaraViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKStatusPerkawinanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKTidakMampuViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKTidakMasukKerjaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKUsahaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.SuratKuasaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.SuratTugasViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPKehilanganViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {
    single { get<Retrofit>().create(SuratApi::class.java) }
    single { get<Retrofit>().create(ReferensiApi::class.java) }

    single { Gson() }

    single<SuratRepository> { SuratRepositoryImpl(get()) }
    single<ReferensiRepository> { ReferensiRepositoryImpl(get()) }

    single { GetSuratListUseCase(get()) }
    single { GetSuratDetailUseCase(get()) }
    single { CreateSuratDomisiliUseCase(get()) }
    single { CreateSuratBerpergianUseCase(get()) }
    single { CreateSuratJandaDudaUseCase(get()) }
    single { CreateSuratBedaIdentitasUseCase(get()) }
    single { CreateSuratDomisiliPerusahaanUseCase(get()) }
    single { CreateSuratTidakMasukKerjaUseCase(get()) }
    single { CreateSuratGhaibUseCase(get()) }
    single { CreateSuratKehilanganUseCase(get()) }
    single { CreateSuratKelahiranUseCase(get()) }
    single { CreateSuratKematianUseCase(get()) }
    single { CreateSuratKeramaianUseCase(get()) }
    single { CreateSuratKuasaUseCase(get()) }
    single { CreateSuratPenghasilanUseCase(get()) }
    single { CreateSuratPernikahanUseCase(get()) }
    single { CreateSuratPindahDomisiliUseCase(get()) }
    single { CreateResiKTPSementaraUseCase(get()) }
    single { CreateSuratSKCKUseCase(get()) }
    single { CreateSuratStatusPerkawinanUseCase(get()) }
    single { CreateSuratTidakMampuUseCase(get()) }
    single { CreateSuratUsahaUseCase(get()) }
    single { CreateSuratTugasUseCase(get()) }
    single { CreateSuratIzinTidakKerjaUseCase(get()) }
    single { GetAgamaUseCase(get()) }
    single { GetStatusKawinUseCase(get()) }
    single { JenisUsahaUseCase(get()) }
    single { BidangUsahaUseCase(get()) }

    viewModel { SuratSayaViewModel(get()) }
    viewModel { SuratDetailViewModel(get()) }
    viewModel { SRKeramaianViewModel(get(), get()) }
    viewModel { SPCatatanKepolisianViewModel(get(), get()) }
    viewModel { SPPernikahanViewModel(get(), get(), get(), get()) }
    viewModel { SPKehilanganViewModel(get(), get()) }
    viewModel { SuratKuasaViewModel(get(), get()) }
    viewModel { SuratTugasViewModel(get(), get()) }
    viewModel { SKTidakMampuViewModel(get(), get(), get(), get()) }
    viewModel { SKResiKTPSementaraViewModel(get(), get(), get()) }
    viewModel { SKStatusPerkawinanViewModel(get(), get(), get(), get()) }
    viewModel { SKDomisiliViewModel(get(), get(), get()) }
    viewModel { SKUsahaViewModel(get(), get(), get(), get()) }
    viewModel { SKTidakMasukKerjaViewModel(get(), get(), get()) }
    viewModel { SKPenghasilanViewModel(get(), get()) }
    viewModel { SKKematianViewModel(get(), get()) }
    viewModel { SKKelahiranViewModel(get()) }
    viewModel { SKJandaDudaViewModel(get(), get(), get()) }
    viewModel { SKGhaibViewModel(get(), get()) }
    viewModel { SKDomisiliPerusahaanViewModel(get(), get(), get(), get(), get()) }
    viewModel { SKBerpergianViewModel(get(), get()) }
    viewModel { SKBedaIdentitasViewModel(get(), get(), get(), get()) }
}