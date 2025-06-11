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
import com.cvindosistem.simpeldesa.auth.domain.usecases.UpdateFcmTokenUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LoginUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LogoutUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.RequestOtpUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ResetPasswordUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ValidateOtpUseCase
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.AuthViewModel
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.PasswordResetViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.viewmodel.ProfileViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Modul Koin untuk otentikasi dan manajemen pengguna.
 *
 * Modul ini mendaftarkan dependensi yang berkaitan dengan:
 * - API untuk otentikasi dan pengguna
 * - Repositori untuk otentikasi, reset password, dan data user
 * - Use case otentikasi seperti login, OTP, dan FCM token
 * - ViewModel untuk login, reset password, dan profil
 *
 * ⚠️ **Instruksi Konfigurasi:**
 * - Pastikan Retrofit sudah diinisialisasi di `networkModule`
 * - Pastikan class `FcmManager` tersedia dan terdaftar (di `fcmModule`)
 * - Pastikan endpoint `AuthApi` dan `UserApi` sudah benar sesuai backend
 */
val authModule = module {
    // Service Retrofit untuk API otentikasi
    single { get<Retrofit>().create(AuthApi::class.java) }

    // Service Retrofit untuk API pengguna
    single { get<Retrofit>().create(UserApi::class.java) }

    // Instance Gson untuk serialisasi JSON
    single { Gson() }

    // Repositori untuk otentikasi, membutuhkan AuthApi dan FcmManager
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    // Repositori untuk reset password
    single<PasswordResetRepository> { PasswordResetRepositoryImpl(get()) }

    // Repositori untuk data pengguna
    single<UserRepository> { UserRepositoryImpl(get()) }

    // Use case untuk login pengguna
    single { LoginUseCase(get()) }

    // Use case untuk logout pengguna
    single { LogoutUseCase(get()) }

    // Use case untuk meminta OTP
    single { RequestOtpUseCase(get()) }

    // Use case untuk validasi OTP
    single { ValidateOtpUseCase(get()) }

    // Use case untuk reset password
    single { ResetPasswordUseCase(get()) }

    // Use case untuk mengambil data pengguna
    single { GetUserInfoUseCase(get()) }

    // Use case untuk update token FCM
    single { UpdateFcmTokenUseCase(get()) }

    // ViewModel untuk login & autentikasi
    // ⚠️ Pastikan `FcmManager` juga tersedia sebagai dependency
    viewModel { AuthViewModel(get(), get(), get()) }

    // ViewModel untuk reset password
    viewModel { PasswordResetViewModel(get(), get(), get(), get()) }

    // ViewModel utama untuk dashboard/home
    viewModel { HomeViewModel(get()) }

    // ViewModel untuk tampilan profil pengguna
    viewModel { ProfileViewModel(get(), get(), get()) }
}
