package com.cvindosistem.simpeldesa.di

import com.cvindosistem.simpeldesa.presentation.screens.auth.LoginViewModel
import com.cvindosistem.simpeldesa.presentation.screens.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { MainViewModel() }
}