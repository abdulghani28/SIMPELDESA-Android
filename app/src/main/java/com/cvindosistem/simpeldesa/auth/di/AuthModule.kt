package com.cvindosistem.simpeldesa.auth.di

import com.cvindosistem.simpeldesa.auth.data.remote.api.AuthApi
import com.cvindosistem.simpeldesa.auth.data.repository.AuthRepositoryImpl
import com.cvindosistem.simpeldesa.auth.data.repository.PasswordResetRepositoryImpl
import com.cvindosistem.simpeldesa.auth.domain.repository.AuthRepository
import com.cvindosistem.simpeldesa.auth.domain.repository.PasswordResetRepository
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LoginUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LogoutUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.RequestOtpUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ResetPasswordUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ValidateOtpUseCase
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.AuthViewModel
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.PasswordResetViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.MainViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { get<Retrofit>().create(AuthApi::class.java) }

    single { Gson() }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<PasswordResetRepository> { PasswordResetRepositoryImpl(get()) }

    single { LoginUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { RequestOtpUseCase(get()) }
    single { ValidateOtpUseCase(get()) }
    single { ResetPasswordUseCase(get()) }

    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { MainViewModel() }
    viewModel { PasswordResetViewModel(get(), get(), get(), get()) }
}