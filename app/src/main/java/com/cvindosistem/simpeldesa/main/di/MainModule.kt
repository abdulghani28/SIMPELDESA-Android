package com.cvindosistem.simpeldesa.main.di

import com.cvindosistem.simpeldesa.auth.data.remote.api.AuthApi
import com.cvindosistem.simpeldesa.auth.data.remote.api.UserApi
import com.cvindosistem.simpeldesa.auth.data.repository.AuthRepository
import com.cvindosistem.simpeldesa.auth.data.repository.AuthRepositoryImpl
import com.cvindosistem.simpeldesa.auth.data.repository.PasswordResetRepository
import com.cvindosistem.simpeldesa.auth.data.repository.PasswordResetRepositoryImpl
import com.cvindosistem.simpeldesa.auth.data.repository.UserRepository
import com.cvindosistem.simpeldesa.auth.data.repository.UserRepositoryImpl
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LoginUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LogoutUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.RequestOtpUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ResetPasswordUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ValidateOtpUseCase
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.AuthViewModel
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.PasswordResetViewModel
import com.cvindosistem.simpeldesa.main.data.remote.api.ReferensiApi
import com.cvindosistem.simpeldesa.main.data.remote.api.SuratApi
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepository
import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepositoryImpl
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepository
import com.cvindosistem.simpeldesa.main.data.repository.SuratRepositoryImpl
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBedaIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBerpergianUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliPerusahaanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJandaDudaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMasukKerjaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratDetailUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratListUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.MainViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.viewmodel.ProfileViewModel
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

    viewModel { SuratSayaViewModel(get()) }
}