package com.cvindosistem.simpeldesa.main.di

import com.cvindosistem.simpeldesa.main.data.remote.api.ReferensiApi
import com.cvindosistem.simpeldesa.main.data.remote.api.SuratApi
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepository
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepositoryImpl
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepository
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepositoryImpl
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
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratDetailUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratListUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SRKeramaianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel
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

    viewModel { SuratSayaViewModel(get()) }
    viewModel { SRKeramaianViewModel(get(), get()) }
}