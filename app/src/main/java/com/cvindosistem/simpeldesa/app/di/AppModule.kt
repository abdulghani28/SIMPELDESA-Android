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

val appModule = module {

    single { get<Retrofit>().create(FileApi::class.java) }

    single<UserPreferences> { UserPreferencesImpl(androidContext()) }
    single<FileRepository> { FileRepositoryImpl(get()) }

    single { UploadFileUseCase(get()) }
    single { ImageLoader(get()) }

    viewModel { FileUploadViewModel(get()) }

}