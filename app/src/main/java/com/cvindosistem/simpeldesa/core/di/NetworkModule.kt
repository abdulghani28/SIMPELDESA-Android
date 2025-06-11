package com.cvindosistem.simpeldesa.core.di

import com.cvindosistem.simpeldesa.core.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Network module untuk menyediakan komponen jaringan.
 * - Menggunakan OkHttp + Retrofit
 * - Menyertakan interceptor untuk logging dan autentikasi
 */
val networkModule = module {

    // Menyediakan interceptor untuk menyisipkan header Authorization di setiap request
    single { AuthInterceptor(get()) }

    // Menyediakan instance OkHttpClient dengan:
    // - Logging Interceptor (level BODY)
    // - AuthInterceptor untuk menambahkan token secara otomatis
    // - Timeout 120 detik
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log seluruh isi request/response
            })
            .addInterceptor(get<AuthInterceptor>()) // Tambahkan header Authorization
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    // Menyediakan Retrofit client dengan base URL environment dev
    // - Gunakan Gson untuk konversi JSON
    // - Client sudah memakai OkHttpClient di atas
    single {
        Retrofit.Builder()
            .baseUrl("https://devsuperadmin-digitaldesa.avnet.id/") // Base URL API backend
            .addConverterFactory(GsonConverterFactory.create()) // Konversi JSON ke objek Kotlin
            .client(get()) // OkHttpClient dengan interceptor
            .build()
    }
}