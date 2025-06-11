package com.cvindosistem.simpeldesa.app.di

import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferencesImpl
import com.cvindosistem.simpeldesa.core.data.remote.api.FileApi
import com.cvindosistem.simpeldesa.core.domain.repository.FileRepository
import com.cvindosistem.simpeldesa.core.domain.repository.FileRepositoryImpl
import com.cvindosistem.simpeldesa.core.domain.usecases.UploadFileUseCase
import com.cvindosistem.simpeldesa.core.helpers.ImageLoader
import com.cvindosistem.simpeldesa.core.presentation.FileUploadViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Modul Koin untuk dependency injection di level aplikasi.
 *
 * Mendaftarkan:
 * - Retrofit service [FileApi]
 * - Preferences [UserPreferences]
 * - Repository [FileRepository]
 * - Use case [UploadFileUseCase]
 * - Loader gambar [ImageLoader]
 * - ViewModel [FileUploadViewModel]
 */
val appModule = module {

    // Retrofit API service untuk file
    single { get<Retrofit>().create(FileApi::class.java) }

    // Preference penyimpanan lokal untuk user
    single<UserPreferences> { UserPreferencesImpl(androidContext()) }

    // Repository file
    single<FileRepository> { FileRepositoryImpl(get()) }

    // Use case untuk upload file
    single { UploadFileUseCase(get()) }

    // Loader gambar
    single { ImageLoader(get()) }

    // ViewModel untuk pengunggahan file
    viewModel { FileUploadViewModel(get()) }
}
