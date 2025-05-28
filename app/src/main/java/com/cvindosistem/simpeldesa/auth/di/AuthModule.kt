package com.cvindosistem.simpeldesa.auth.di

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
import com.cvindosistem.simpeldesa.main.presentation.screens.main.MainViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.viewmodel.ProfileViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { get<Retrofit>().create(AuthApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }

    single { Gson() }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<PasswordResetRepository> { PasswordResetRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }

    single { LoginUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { RequestOtpUseCase(get()) }
    single { ValidateOtpUseCase(get()) }
    single { ResetPasswordUseCase(get()) }
    single { GetUserInfoUseCase(get()) }

    viewModel { AuthViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { PasswordResetViewModel(get(), get(), get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
}